# SeaVision Pro Data Collector
<div>
The Data collector module within SeaVision Pro is a sophisticated component designed <br>
to provide `SeaVision Pro` with the latest weather information for selected coastal locations.<br> 
Its primary goal is to ensure that we have access to the most accurate and current weather data,<br> 
allowing us to assess sea visibility conditions effectively.<br>
</div>
<br>
<div>The module runs as an AWS lambda, triggered by CloudWatch event.</div>
<div>Data is stored on our secured DB's.</div>
<div><p>github actions - for CI-CD and testing on pull requests.</div>
<div>Using Java 17, maven build</div>

<div>
  <p>The process system design:</p>
  <p align="center">
    <img src="https://github.com/asafmaoz1234/website/blob/main/images/lambda-beach-time.png">
  </p>
</div>

<pre>
 <p>
TO DO:
- Add spring to project
properties:
    - <s>use application.yml for properties in test and prod</s>
    - <s>modify env params to use inject</s>
webclient:
    - <s>use reactive client to fetch data</s>
    - <s>modify client to support different geo locations</s>
    - <s>mock weather api</s>
    - add timeouts
    - change client non blocking
    
database:
    - set up reactive database connection
    - get locations from db
    - store results in db
</p>
</pre>

 
