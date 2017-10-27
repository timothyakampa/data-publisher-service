## Publisher to rabbit mq using akka streams
Since akka streams have back pressure built into them we can publish to rabbit with lots of data without need for creating a in memory queue to publish the messages

## Prerequisites
A running instance of rabbit mq on localhost

## Running the application
./gradlew clean bootRun

