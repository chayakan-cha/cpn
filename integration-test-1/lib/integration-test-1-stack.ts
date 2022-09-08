import { Construct } from 'constructs';
import { aws_events, aws_events_targets, aws_sns, aws_sqs, aws_iam, aws_lambda, aws_lambda_event_sources, Duration, Stack, StackProps, CfnOutput } from 'aws-cdk-lib';

export class IntegrationTest1Stack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const topic = new aws_sns.Topic(this, 'SnsTopic', {
      displayName: 'ThisTopic',
    });

    const firstQueue = new aws_sqs.Queue(this, 'FirstQueue', {
      visibilityTimeout: Duration.seconds(30),
      queueName: "TheFirstQueue"
    });
    const secondQueue = new aws_sqs.Queue(this, 'SecondQueue', {
      visibilityTimeout: Duration.seconds(30),
      queueName: "TheSecondQueue"
    });

    const lambdaFunction = new aws_lambda.Function(this, 'integrationTest1', {
      code: aws_lambda.Code.fromAsset('lib/src'),
      handler: 'index.handler',
      functionName: 'IntegrationTest1',
      runtime: aws_lambda.Runtime.NODEJS_12_X,
      environment: {
        SNS_TOPIC_ARN: topic.topicArn,
        FIRST_QUEUE_URL: firstQueue.queueUrl,
        SECOND_QUEUE_URL: secondQueue.queueUrl,
      }
    });

    lambdaFunction.addToRolePolicy(new aws_iam.PolicyStatement({
      effect: aws_iam.Effect.ALLOW,
      resources: [topic.topicArn, firstQueue.queueArn, secondQueue.queueArn],
      actions: ["*"],
    }))

    lambdaFunction.role?.addManagedPolicy(
      aws_iam.ManagedPolicy.fromAwsManagedPolicyName(
        'AmazonSNSFullAccess',
      ),
    );
    
    const eventRule = new aws_events.Rule(this, 'scheduleRule', {
      schedule: aws_events.Schedule.rate(Duration.minutes(1)),
    });
    eventRule.addTarget(new aws_events_targets.LambdaFunction(lambdaFunction));

    new CfnOutput(this, 'snsTopicArn', {
      value: topic.topicArn,
      description: 'snsTopicArn',
    });
    new CfnOutput(this, 'queue1Url', {
      value: firstQueue.queueUrl,
      description: 'queue1Url',
    });
    new CfnOutput(this, 'queue2Url', {
      value: secondQueue.queueUrl,
      description: 'queue2Url',
    });
  }
}
