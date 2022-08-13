# lambda-beach-time

<div>Description:</div>
<div>This project is aimed to fetch weather data for a specific geo location publish to sqs queue if weather conditions pass a set of rules indicating conditins are optimal for scubadiving.</div>
<br>
<div>Runs on AWS lambda, triggered by cloudwatch scheduled event.</div>
<br>
<ul>
<li>clone/fork</li>
<li>set up java lmabda : TBD - cloudfromation template</li>
<li>set up cloudwatch event : TBD - cloudformation template</li>
<li>set up sqs queue : TBD - cloudformation template</li>
<li>set up a user and get access key from stormglass.io</li>
</ul>

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
 
 
