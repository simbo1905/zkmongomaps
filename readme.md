
#### ZK MVVM Spring Data Mongodb & Google Maps demo

Build and run the code with 

	mvn -Djetty.port=8080 -Dmaven.test.skip=true package jetty:run

It will run inside of its own jetty server. 

To change the mongo host and port edit the file zkmongogmaps.properties

	zkmongogmaps.host=localhost
	zkmongogmaps.port=27017
	zkmongogmaps.db=world

Upon startup the application will attempt to load its own test data from the 
file zips.json.gz which was sourced from http://media.mongodb.org/zips.json
