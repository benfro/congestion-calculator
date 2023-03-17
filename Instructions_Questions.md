# Getting Started

- Java 17 expected on the machine
- Extract ZIP and open in IntelliJ
- Run './mvnw clean install' to build
- Run unit tests './mvnw clean test' - there should be more than hundred of them
- Run './mvnw spring-boot:run'
- POST towards http://localhost:8088/tax JSON body { "dates": ["2013-01-14 21:00:00","2013-01-15 21:00:00","2013-02-07 06:23:27","2013-02-07 15:27:00","2013-02-08 06:27:00","2013-02-08 06:20:27","2013-02-08 14:35:00","2013-02-08 15:29:00","2013-02-08 15:47:00","2013-02-08 16:01:00","2013-02-08 16:48:00","2013-02-08 17:49:00","2013-02-08 18:29:00","2013-02-08 18:35:00","2013-03-26 14:25:00","2013-03-28 14:07:27"]} using PostMAN or similar software
- or use 'curl -X POST -H "Content-Type: application/json" -d '{"dates": ["2013-01-14 21:00:00","2013-01-15 21:00:00","2013-02-07 06:23:27","2013-02-07 15:27:00","2013-02-08 06:27:00","2013-02-08 06:20:27","2013-02-08 14:35:00","2013-02-08 15:29:00","2013-02-08 15:47:00","2013-02-08 16:01:00","2013-02-08 16:48:00","2013-02-08 17:49:00","2013-02-08 18:29:00","2013-02-08 18:35:00","2013-03-26 14:25:00","2013-03-28 14:07:27"]}'  http://localhost:8088/tax'
- Get back { "taxResult": "89"}

# Done
- Put existing code in a basic Spring Boot project to get http functionality. Use curl or custom program to post for tax calculation result.
- Refactoring to use java.time classes instead of old Date
- Possibility to implement rules for other cities through the use of interfaces i package net.benfro.concalc.ruleapi
- Currently configuration of tax rates during time periods are read from file resources/tax-interval-fees-gbg.csv which makes this also extendable for other cities.
- Current rule implementations reside in net.benfro.concalc.config.gothenburg. If one wanted to include implementations for other cities and use those one would probably use the Spring @Qualifier to distinguish implementations used. This is beyond the scope of this assignment.
- Implementation works for other years than 2013 through the use of a HolidayCalculator. Of course one could think of some kind of generic file format (JSON?) to describe all the rules for toll-free dates, but this soulution uses holiday calculation and a cache mechanism (in package /ruleapi/util)
- Finally the toll-free vehicle types and max-per-day-fee resides in  the application.properties file

# Not done - needs to be fixed
- Vehicle type is currently not included in http call. I deemed this not necessary as toll-free vehicles always amount to zero. Of course the next step could be to implement a dummy lookup service that maps vehicle registration number to a vehicle type.
- Integration tests of http call should be implemented.
