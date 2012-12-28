package org.github.simbo1905.zkmongogmaps.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@ComponentScan(basePackages = "org.github.simbo1905.zkmongogmaps.app")
@PropertySource({ "classpath:zkmongogmaps.properties" })
@EnableMongoRepositories
@Configuration
public class CityConfig {

	@Autowired
	Environment env;

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		final String user = env.getProperty("zkmongomaps.mongo.user");
		final String db = env.getProperty("zkmongomaps.mongo.db");
		final String password = env.getProperty("zkmongomaps.mongo.password");
		final String host = env.getProperty("zkmongomaps.mongo.host");
		final Integer port = Integer.valueOf(env
				.getProperty("zkmongomaps.mongo.port"));

		UserCredentials userCredentials = new UserCredentials(user, password);
		return new SimpleMongoDbFactory(new Mongo(host, port), db,
				userCredentials);
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

}
