load testing basically multiple connections types. System: Xeon E3 1235, 8GB DDR3 1866, Ubuntu 16.04, JDK 1.8.
256 parallel users, warmup of 10k requests, sending 100k requests for simple connecting, 10k requests for database (mysql) simple select.
Database test is not relevant as the bottlenet is the database itself. Different drivers, blocking and non-blocking strategies have similar results.

tomcat-spring-boot (blocking) : 23k responses per second
undertow-spring-boot (blocking): 32k responses per second
vertx-qbit (non-blocking): 39k responses per second
undertow-handlers (non-blocking): 55k responses per second
vertx-verticle, vertx-sync (non-blocking): 57k responses per second.