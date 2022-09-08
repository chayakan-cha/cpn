# Integration-test-1
# Pre-requisite
* 1. AWS CDK Toolkit: can you install with command npm i aws-cdk
* 2. nodejs
# How To Install
* 1. npm install
* 2. cdk synth
* 3. cdk bootstrap
* 4. cdk deploy
* you can delete to stack aws wth command: cdk destroy
# Test for AWS CLI:
* Before you can begin using install AWS-CLI must be installed.
* 1. manual test function lambda with command: 
* aws lambda invoke --function-name IntegrationTest1 out --log-type Tail --query 'LogResult' --output text |  base64 -d
* You can check your messages on aws cloud watch. Messages are sent every 1 minute.
