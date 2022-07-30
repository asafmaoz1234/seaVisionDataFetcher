# lambda-beach-time

<div>Description:</div>
<div>This project is aimed to fetch weather data for a specific geo location and notify me if the weather conditions pass a set of rules and the sea is in the best condition for me to go scubadiving.</div>
<br>
<div>This code runs on AWS lambda, so project size is important, that is why im not using Spring for dependency injection or webClient.</div>
<br>
<div>General on the code:</div>
<ul>
  <li>Using Java 8, maven build</li>
  <li>Simple HttpURLConnection to fetch data from external api</li>
  <li>unit and mockito for testing</li>
  <li>AWS S3 sdk to publish message incase conditions are great</li>
</ul>

<div>
  <p>The process system design:</p>
  <p align="center">
    <img src="https://github.com/asafmaoz1234/website/blob/main/images/lambda-beach-time.png">
  </p>
</div>
<div>
  <dl>
      <dt>Cloudwatch</dt>
      <dd>- triger lambda by issuing an event every 24 hours</dd>
      <dt>AWS Lambda</dt>
      <dd>- Java lambda for this repo</dd>
      <dt>https://stormglass.io/</dt>
      <dd>- Free service to fetch waves data</dd>
</dl>
</div>
<div>
  <p>enhancements:
  <ul>
  <li>use cloud formation to set up aws resources</li>
  <li>add more ruls</li>
  </ul>
</div>
 
 
