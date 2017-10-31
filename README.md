## Publisher to rabbit mq using akka streams
Since akka streams have back pressure built into them we can publish to rabbit with lots of data without need for creating a in memory queue to publish the messages

## Prerequisites
A running instance of rabbitmq and [oracle db express edition](http://www.oracle.com/technetwork/database/database-technologies/express-edition/overview/index.html)
```
    docker-compose up -d
```

## Running the application
```
    ./gradlew clean bootRun
```

## SSH'ing into oracle db
```
    ssh root@localhost -p 49132
    password: admin
```

## Connecting to commandline sql
```
    sqlplus system/oracle
```

## JDBC configuration
```
host: localhost
port: 49133
sid: xe
userid: system
password: oracle

```






