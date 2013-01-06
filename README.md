
Esper Quick Start
=================

Esper Quick Start Application

See http://esper.codehaus.org/tutorials/tutorial/quickstart.html


Prerequisites for Building
-------------------
Java JDK 1.6
Maven 2.2 or higher (http://maven.apache.org/)


Build
-------------------
$ mvn clean package


Test
-------------------
$ mvn compile test  



Run
-------------------

$ mvn compile exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.lei.cep.App

$ mvn compile exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.lei.cep.main.StockAvg -Dlog4j.configuration=./log4j.xml

$ mvn compile exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.lei.cep.main.StockAvg
