
#### ZK MVVM Spring Data Mongodb & Google Maps Demo

## Build

Build and run the code with: 

	mvn -Dmaven.test.skip=true package jetty:run

It will run inside of its own jetty server. 

You can set the following properties with defaults as shown: 

	-Djetty.port=8080 
	-Dzkmongomaps.mongo.host=localhost
	-Dzkmongomaps.mongo.port=27017
	-Dzkmongomaps.mongo.db=world
	-Dzkmongomaps.mongo.user=zkmongomaps
	-Dzkmongomaps.mongo.password=xxx

The defaults are configured within zkmongogmaps.properties

Upon first startup the application will attempt to load its own test data from 
the file zips.json.gz which was sourced from http://media.mongodb.org/zips.json
The one time dataload may take more than a minute. 

Note that the code assumes you are running mongod with --auth with a username 
and password set on the db that the app is using.  This matches how mongo runs 
on a cloud provider such as Redhat Openshift. See the following mongo docs: 

[Mongo DB - Adding Users](http://docs.mongodb.org/manual/tutorial/control-access-to-mongodb-with-authentication/#adding-users)

Alternatively you can edit the ZipcodeAppConfig.java to take out the credentials 
setting line to run your local mongo with the default settings. 

## Running On Redhat Openshift PaaS Cloud

Openshift Instructions:

	# create a diy app
	rhc app create zkmongomaps diy-0.1
	
	# add mongo cartridge
	rhc cartridge add mongodb-2.2 --app zkmongomaps
	
	# add the demo repo to the local folder created by the 'rhc app' command
	cd zkmongomaps
	git remote add upstream https://github.com/simbo1905/zkmongomaps.git

	# you can check that 'upstream' now points to my demo code and 'origin' points to your cloud server with 
	git remote show upstream
	git remote show origin
	
	# pull down the demo code (just hit return if asked for a password)
	git pull -s recursive -X theirs upstream master
	
	# insure the demo code builds in your servers local folder
	mvn -Dmaven.test.skip=true package
	
	# push it up to 'origin' which should be your server and it will build and start the app
	git push
	
	# tail the logs in a second window
	rhc-tail-files -a zkmongomaps
	
## Inspiration 

Tobias Trelle with [Spring Data Part 4: Geospatial Queries with MongoDB](http://blog.codecentric.de/en/2012/02/spring-data-mongodb-geospatial-queries/)

Hamid Fadishei with [ZK MVVM Design Pattern and Server-Side Paging](http://fadishei.wordpress.com/2012/03/22/zk-mvvm-design-pattern-and-server-side-paging/)

See Z60-Gmaps-Bind.zul in the sourcecode for ZK GMaps MVVM API showcasing the test module of [zkgmapsz](https://code.google.com/p/zkgmapsz/)

End.
