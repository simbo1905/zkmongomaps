
#### ZK MVVM Spring Data Mongodb & Google Maps demo

## Build

Build and run the code with 

	mvn -Dmaven.test.skip=true package jetty:run

It will run inside of its own jetty server. 

You can set the following properties which defaults to the values shown: 

	-Djetty.port=8080 
	-Dzkmongomaps.mongo.host=localhost
	-Dzkmongomaps.mongo.port=27017
	-Dzkmongomaps.mongo.db=world
	-Dzkmongomaps.mongo.user=zkmongomaps
	-Dzkmongomaps.mongo.password=xxx

The defaults are defined within zkmongogmaps.properties

Upon first startup the application will attempt to load its own test data from 
the file zips.json.gz which was sourced from http://media.mongodb.org/zips.json
This may take more than a minute. 

## Running On Redhat Openshift PaaS

See it running on redhat openshift cloud at http://zkmongomaps-simbo1905.rhcloud.com/

Approximate openshift instructions

	# create a diy app
	rhc app create zkmongomaps diy-0.1
	
	# add mongo cartridge
	rhc cartridge add mongodb-2.2 --app zkmongomaps
	
	#add the demo code repo to the folder
	cd zkmongomaps
	git remote add upstream https://github.com/simbo1905/zkmongomaps.git
	
	# in the next command just hit return if asked for a password for downloading the code
	git pull -s recursive -X theirs upstream master
	
	# you can check that 'upstream' points to my demo code and 'origin' points to your server with 
	git remote show upstream
	git remote show origin
	
	# insure the code build and deploys see commmandline.build.and.run.txt for full details
	mvn -Dmaven.test.skip=true package
	
	#once you have it working push it up to 'origin' which should be your server which will build it and start the app
	git push
	
	#tail the logs in a second window
	rhc-tail-files -a zkmongomaps 

End.
