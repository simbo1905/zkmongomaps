package org.github.simbo1905.zkmongogmaps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.github.simbo1905.zkmongogmaps.app.Zipcode;
import org.github.simbo1905.zkmongogmaps.app.ZipcodeAppConfig;
import org.github.simbo1905.zkmongogmaps.app.ZipcodeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.MongoCollectionUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;

/**
 * Tests for Spring Data MongoDB - Geospatial queries.
 * 
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias
 *         Trelle</a>
 * @see https://github.com/ttrelle/spring-data-examples
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=ZipcodeAppConfig.class )
public class CityRepositoryTests {

	private static final Point DUS = new Point( 6.810036, 51.224088 );

	private static final Zipcode BERLIN = new Zipcode("Berlin", "Berlin", 3515473, 13.405838, 52.531261  );

	private static final Zipcode COLOGNE =  new Zipcode("Cologne", "North Rhine-Westphalia", 1017155, 6.921272, 50.960157 );

	private static final Zipcode DUSSELDORF = new Zipcode("Düsseldorf", "North Rhine-Westphalia", 592393, 6.810036, 51.224088 );
	
	@Inject
	protected ZipcodeRepository cityRepository = null;
	
	@Inject
	MongoOperations mongoOperations;
	
	@Before
	public void setUp() {
		mongoOperations.indexOps(Zipcode.class).ensureIndex( new GeospatialIndex("loc") );
		String collectionName = MongoCollectionUtils.getPreferredCollectionName(Zipcode.class);
		DBCollection collection = mongoOperations.getCollection(collectionName);
		mongoOperations.indexOps(Zipcode.class).ensureIndex( new GeospatialIndex("loc") );
		collection.remove(BasicDBObjectBuilder.start().get()); // wild card remove
	}
	
	@Test
	public void testRoundTripSimple() throws Exception {
		// given a city
		Zipcode city = new Zipcode(null,"ACMAR","AL",6055, new double[]{-86.51557d,33.584132d});
		
		// when roundtrip to db
		this.cityRepository.save(city);
		Collection<Zipcode> fromDbCollection = this.cityRepository.findByState("AL");
		Zipcode cityDb = fromDbCollection.iterator().next();
		
		// then assert equal by value
		Assert.assertEquals(city, cityDb);
	}
	
	@Test
	public void testGeospacialFindsNear() throws Exception {
		// given
		mongoOperations.save( BERLIN );
		mongoOperations.save( COLOGNE );
		mongoOperations.save( DUSSELDORF );
		
		// when
		List<Zipcode> locations = this.cityRepository.findByLocNear(DUS , new Distance(70, Metrics.KILOMETERS) );
		
		// then
		Assert.assertThat(locations.size(), is(2));
		Assert.assertThat(locations, hasItem(COLOGNE));
		Assert.assertThat(locations, hasItem(DUSSELDORF));
	}
	
	@Test public void shouldFindAroundOrigin() {
		// given
		mongoOperations.save( BERLIN );
		mongoOperations.save( COLOGNE );
		mongoOperations.save( DUSSELDORF );
		
		// when
		List<Zipcode> locations = cityRepository.findByLocWithin(new Circle(DUS.getX(), DUS.getY(),
				1.0));

		// then
		Assert.assertThat(locations.size(), is(2));
		Assert.assertThat(locations, hasItem(COLOGNE));
		Assert.assertThat(locations, hasItem(DUSSELDORF));		
	}

	@Test public void shouldFindWithinBox() {
		// given
		mongoOperations.save( BERLIN );
		mongoOperations.save( COLOGNE );
		mongoOperations.save( DUSSELDORF );
		
		// when
		List<Zipcode> locations = cityRepository.findByLocWithin(new Box(new Point(
				DUS.getX()-0.5, DUS.getY()-0.5), new Point(DUS.getX()+0.5, DUS.getY()+0.5)));

		// then
		Assert.assertThat(locations.size(), is(2));
		Assert.assertThat(locations, hasItem(COLOGNE));
		Assert.assertThat(locations, hasItem(DUSSELDORF));				
	}
	
	@Test public void shouldPage() {
		// given
		
		mongoOperations.save( BERLIN );
		mongoOperations.save( COLOGNE );
		mongoOperations.save( DUSSELDORF );
		
		// when
		
		Pageable firstPageableOfTwo = new PageRequest(0,2);
		Page<Zipcode> firstPageOfTwo = cityRepository.findAll(firstPageableOfTwo );
		Pageable secondPageableOfTwo = new PageRequest(1,2);
		Page<Zipcode> secondPageOfTwo = cityRepository.findAll(secondPageableOfTwo);
		
		// then
		
		List<Zipcode> firstList = Lists.newArrayList(firstPageOfTwo.iterator());
		Assert.assertThat(firstList.size(),is(2));
		
		List<Zipcode> secondList = Lists.newArrayList(secondPageOfTwo.iterator());
		Assert.assertThat(secondList.size(),is(1));
		
		List<Zipcode> combined = Lists.newArrayList(Iterables.concat(firstList,secondList));
		
		Assert.assertThat(combined, hasItem(BERLIN));
		Assert.assertThat(combined, hasItem(COLOGNE));
		Assert.assertThat(combined, hasItem(DUSSELDORF));
	}

	@Test public void testFindByState() {
		// given
		
		mongoOperations.save( BERLIN );
		mongoOperations.save( COLOGNE );
		mongoOperations.save( DUSSELDORF );
		
		// when
		List<Zipcode> nrw = cityRepository.findByState("North Rhine-Westphalia");
		
		// then
		
		Assert.assertThat(nrw.size(), is(2));	
		Assert.assertThat(nrw, hasItem(COLOGNE));
		Assert.assertThat(nrw, hasItem(DUSSELDORF));
	}

}
