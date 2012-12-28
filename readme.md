
#### ZK MVVM Spring Data Mongodb & Google Maps demo

Build and run the code with 

	mvn package jetty:run

It will run inside of its own jetty server. 

Upon startup the application will attempt to load its own test data from the 
file zips.json.gz which was sourced from http://media.mongodb.org/zips.json

You can set the following properties which defaults given values: 

	-Djetty.port=8080 
	-Dmaven.test.skip=true 
	-Dzkmongomaps.mongo.host=localhost
	-Dzkmongomaps.mongo.port=27017
	-Dzkmongomaps.mongo.db=world
	-Dzkmongomaps.mongo.user=zkmongomaps
	-Dzkmongomaps.mongo.password=xxx

The defaults are defined within zkmongogmaps.properties

See it running on redhat openshift cloud at http://zkmongomaps-simbo1905.rhcloud.com/

End.
