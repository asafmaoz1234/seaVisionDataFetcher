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
<div><p>github actions - for CI-CD and running tests on pull requests.</div>
<div>Using Spring boot, Java 17, maven build</div>
<div>Dockerized for local dev</div>

<div>
  <p>The process system design:</p>
  <p align="center">
            <img src="https://asafmaoz.com/images/datafetchersysdesign.png">
        </p>
</div>
<div>To do:</div>
<pre>
 <p>
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
    - <s>store results in db</s>
    - <s>set up database connection</s>
    - <s>get locations from db</s>
    - change to reactive database connection

handler:
    - <s>add handler</s> 
    - test to lambda
    - log errors
    - add metrics
</p>
</pre>

 
