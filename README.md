# MID_Project
Paloma, Samu, Diego and Alberto 's Middleware project

Project includes:
* Components for connecting multiple machines
* Web based user interface for
	* changing, adding, downloading and deleting files
	* searching music (.mp3) files and playing them in the browser.

Web-interface can be run on single machine by cloning this repository and running

```
mvn install
mvn clean compile assembly:singler
java -jar target/MID_Project-1.0-SNAPSHOT-jar-with-dependencies.jar
```

then the server can be reached from localhost:4567.