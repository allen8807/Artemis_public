#!/bin/sh

git pull origin master

mvn clean

mvn clean install -Dmaven.test.skip=true

cp target/artemis-0.0.1-SNAPSHOT.war ../apache-tomcat-8.5.47/webapps/ROOT.war
