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
public interface ZipcodeRepository extends MongoRepository<Zipcode, String> {

	@Query("{ state: ?0 }")
	List<Zipcode> findByState(String state);

	List<Zipcode> findByLocWithin(Circle c);

	List<Zipcode> findByLocWithin(Box b);

	List<Zipcode> findByLocNear(Point p, Distance d);
}
