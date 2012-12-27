package org.github.simbo1905.zkmongogmaps.app;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

	@Query("{ state: ?0 }")
	List<City> findByState(String state);

	List<City> findByLocWithin(Circle c);

	List<City> findByLocWithin(Box b);

	List<City> findByLocNear(Point p, Distance d);
}
