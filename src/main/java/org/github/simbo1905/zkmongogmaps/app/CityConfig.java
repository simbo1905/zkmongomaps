package org.github.simbo1905.zkmongogmaps.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@ComponentScan(basePackages = "org.github.simbo1905.zkmongogmaps.app")
@PropertySource({ "classpath:zkmongogmaps.properties" })
@EnableMongoRepositories
@Configuration
public class CityConfig extends AbstractMongoConfiguration {

	@Autowired
	Environment env;

	public @Bean
	MongoOperations mongoTemplate(Mongo mongo) {
		final String db = env.getProperty("zkmongomaps.mongo.db");
		MongoTemplate mongoTemplate = new MongoTemplate(mongo, db);
		return mongoTemplate;
	}

	@Override
	public String getDatabaseName() {
		final String db = env.getProperty("zkmongomaps.mongo.db");
		return db;
	}

	@Override
	public Mongo mongo() throws Exception {
		final String host = env.getProperty("zkmongomaps.mongo.host");
		final Integer port = Integer.valueOf(env.getProperty("zkmongomaps.mongo.port"));
		return new Mongo(host, port);
	}

}
