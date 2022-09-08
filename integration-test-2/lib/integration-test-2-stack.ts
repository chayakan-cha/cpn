import { Construct } from 'constructs';
import {aws_iam,aws_s3, aws_sns, aws_sqs, RemovalPolicy, aws_lambda, aws_lambda_event_sources, Duration, Stack, StackProps, CfnOutput } from 'aws-cdk-lib';


export class IntegrationTest2Stack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const queue = new aws_sqs.Queue(this, 'Queue', {
      visibilityTimeout: Duration.seconds(30),
      queueName: "Queue"
    });
    
    const topic = new aws_sns.Topic(this, 'SnsTopic', {
      displayName: 'ThisTopic',
    });

    const bucket = new aws_s3.Bucket(this, 'ProfileDataBucket', {
      autoDeleteObjects: true,
      removalPolicy: RemovalPolicy.DESTROY
    })

    const lambdaFunction = new aws_lambda.Function(this, 'integrationTest2', {
      code: aws_lambda.Code.fromAsset('lib/src'),
      handler: 'index.handler',
      functionName: 'IntegrationTest2',
      runtime: aws_lambda.Runtime.NODEJS_12_X,
      // role: role,
      environment: {
        QUEUE_URL: queue.queueUrl,
        SNS_TOPIC_ARN: topic.topicArn,
        BUCKET_NAME: bucket.bucketName,
        S3_FILE_NAME: 'RANDOM.txt',
      }
    });

    lambdaFunction.role?.addManagedPolicy(
      aws_iam.ManagedPolicy.fromAwsManagedPolicyName(
        'AmazonSNSFullAccess',
      ),
    );

    lambdaFunction.role?.addManagedPolicy(
      aws_iam.ManagedPolicy.fromAwsManagedPolicyName(
        'AmazonS3FullAccess',
      ),
    );

    const lambdaEventSourceQueue = new aws_lambda_event_sources.SqsEventSource(queue, {
      batchSize: 10
    });
    lambdaFunction.addEventSource(lambdaEventSourceQueue);

    new CfnOutput(this, 'snsTopicArn', {
      value: topic.topicArn,
      description: 'snsTopicArn',
    });
    new CfnOutput(this, 'queueUrl', {
      value: queue.queueUrl,
      description: 'queueUrl',
    });
    new CfnOutput(this, 's3Bucket', {
      value: bucket.bucketName,
      description: 's3Bucket',
    });
   
  }
}
