# cpn
# Integration-test-1
# Pre-requisite
* 1. AWS CDK Toolkit: can you install with command npm i aws-cdk
* 2. nodejs
# How To Install
* 1. npm install
* 2. cdk synth
* 3. cdk bootstrap
* 4. cdk deploy

# Test for AWS CLI:
* Before you can begin using install AWS-CLI must be installed.
* 1. manual test function lambda with command: 
* aws lambda invoke --function-name IntegrationTest1 out --log-type Tail --query 'LogResult' --output text |  base64 -d
* You can check your messages on aws cloud watch. Messages are sent every 1 minute.

# 2.Integration-test-2
# Pre-requisite
* 1. AWS CDK Toolkit: can you install with command npm i aws-cdk
* 2. nodejs
# How To Install
* 1. npm install
* 2. cdk synth
* 3. cdk bootstrap
* 4. cdk deploy
# Test for AWS CLI:
* Before you can begin using install AWS-CLI must be installed.
* 1. add profile date to S3 with command: 
* aws s3 cp lib/src/RANDOM.txt s3 s3://{{bucketName}}  
* 2. test sqs to message with command:
* aws sqs send-message --queue-url "{{queueUrl}}" --message-body '{"username": "RANDOM","login_at": "2022-07-19 12:00:00"}'  

# 3. customer-profile-service-api
# Pre-requisite
* 1. java 8
* 2. Docker
* 3. maven
# How To Install
* you can run through the command line: 
* 1. mvn clean install
* 2. cp target/customer-profile-service-api-0.0.1-SNAPSHOT.jar .
* 3. docker-compose up
# Test for api:
* you can test the code through to the postman, which is attached in the test-customer-profile-service-api folder.
# Metrics observing using spring Boot Actuator and Prometheus
* localhost:8080/actuator/prometheus
