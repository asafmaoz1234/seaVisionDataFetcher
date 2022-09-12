# Sea visibility service

<div>Description:</div> 
<div>This project is aimed to fetch weather data for a specific geo location point, check weather and weaves conditions and publish to sqs queue if conditions pass a set of rules indicating optimal sea vissibility for scubadiving/snorkeling.</div>
<br>
<div><p>AWS Lambda - Query a weather service api for measurements in specific geolocation coordinates and according to a set of rules determine if it should notify me.</div></p>
<div><p>SQS - get the notifications events and trigger a different lambda that handles emails and notifications.</div></p>
<div><p>CloudWatch - scheduled event that triggers the lambda once a day.</div></p>
<div><p>github actions - for CI-CD and testing on pull requests.</div></p>

<div>General project notes:</div>
<ul>
  <li>Trying to keep code base small, so not Spring or other heave dependencies.</li>
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
 
 
