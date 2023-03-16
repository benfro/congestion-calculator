# Getting Started

- Java 17 expected!
- Made with IntelliJ.
- Run './mvnw clean install' to build
- Run unit tests './mvnw clean test' - there should be 27 of them
- Run './mvnw spring-boot:run'
- POST towards http://localhost:8088/tax JSON body { "dates": ["2013-01-14 21:00:00","2013-01-15 21:00:00","2013-02-07 06:23:27","2013-02-07 15:27:00","2013-02-08 06:27:00","2013-02-08 06:20:27","2013-02-08 14:35:00","2013-02-08 15:29:00","2013-02-08 15:47:00","2013-02-08 16:01:00","2013-02-08 16:48:00","2013-02-08 17:49:00","2013-02-08 18:29:00","2013-02-08 18:35:00","2013-03-26 14:25:00","2013-03-28 14:07:27"]}
- Get back { "taxResult": "29"}

# Not done - needs to be fixed
- CongestionTaxCalculator.getTax(..) does not return correctly for lists comprising more than one 24-hour period.
- Vehicle type is currently not included in http call. 
- Integration tests of http call lacking.
- External config of period tax charges currently hard coded. File 'charges.csv' should be read into a dynamic list consisting of TaxTimeInterval instances and calculation logic be adapted to this.

# Done
- Put existing code in a basic Spring Boot project to get http functionality
- Setting up basic unit tests for existing classes.
- Refactoring to use java.time classes instead of old Date
- Refactoring and code cleaning: Vehicle class adapted to dynamic init of possible values via configuration entry. Redundant classes removed.

# Question
- Rule of tax free days says "public holidays, days before a public holiday" - currently this doesn't apply to 30/11 - All Saints Day. Should it? I'm not sure the "day before" rule is currently correctly implemented. Needs further checks amd tests.