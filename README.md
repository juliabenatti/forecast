# Forecast Application Battle

Welcome to _Forecast Application_!

### Running application
* The same can happen using IDE (i.e IntelliJ)
* Using command`mvn spring-boot:run`

### Current functionalities
* Endpoints:
  * `/metrics (POST):` Create a new metric. Expects a `.json` including `sensorId`, `metricName`, `metricValue` and `updatedAt`
  * `/metrics/statistic/{statisticType} (GET):` Get statistic for specified `Sensor ID`, `Metric Name` and `Date range`. 
  This endpoint accepts no data range (will return today's data) and a month data range (if data range is above this, it will be automatically reset to a month from endDate selected)
    * Expects:
      * **[Required]** `statisticType` (Path parameter) - Valid values: `min`, `max`, `sum` or `avg`
      * **[Required]** `sensorIds` (Request parameter) - Valid values: List including `all` (if you want to select all sensor ids) **OR** `sensor ids`
      * **[Required]** `metricNames` (Request parameter) - Valid values: List including `metric names`
      * **[Optional, but only if endDate is also null]** - Valid values: `dd-mm-yyyy` or `null`. If null, method will automatically selecy **today's date** for the query.
      * **[Optional, but only if startDate is also null]** - Valid values: `dd-mm-yyyy` or `null`. If null, method will automatically selecy **today's date** for the query.
* More details and examples for the endpoints can be found in Swagger: http://localhost:8080/swagger-ui/index.html. Make sure the application is up and running to have access to the URL.

### Future improvement points
* Database (currently using H2)
  * Credentials shouldn't be in the application.properties.
* Unit tests
  * Test getMetricsByStatisticWhenStatisticNameIsInvalidFails() is failing. Application works, but filtering is having some issue.
  

## Tech Stack
* Java 11
* Maven
* Spring Boot
* Spring Data (JPA)
* H2 (database)

## Contact details
**Julia Benatti Latanzio <br>
juliabenattilatanzio@gmail.com**
