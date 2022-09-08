const aws = require("aws-sdk");
aws.config.update({region: 'us-east-1'});
 
exports.handler = async (event) => {
    const snsTopicArn = process.env.SNS_TOPIC_ARN
    const queueFirstUrl = process.env.FIRST_QUEUE_URL
    const queueSecondUrl = process.env.SECOND_QUEUE_URL
    const currentDate = new Date().toISOString().slice(0, 10);
    const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    function generateString(length) {
        let result = ' ';
        const charactersLength = characters.length;
        for ( let i = 0; i < length; i++ ) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
    
        return result;
    }
    var msg = JSON.stringify({username: generateString(5), login_at: currentDate});
    console.log("Lambda call:", msg);
    var params0 = {Message: msg,
                Subject: "snsTopic",
                TopicArn: snsTopicArn
    };
    var sns = new aws.SNS({ apiVersion: currentDate});   
    const resPublish = await sns.publish(params0).promise();
    console.log("response sns publish:",resPublish);

    var sqs = new aws.SQS({ apiVersion: currentDate});
    var params1 = {
        DelaySeconds: 2,
        MessageBody: msg,
        QueueUrl: queueFirstUrl
    };
    var params2 = {
        DelaySeconds: 2,
        MessageBody: msg,
        QueueUrl: queueSecondUrl
    };
    
    const resSendMsgQueueFirst =  await sqs.sendMessage(params1).promise();
    const resSendMsgQueueSecond =  await sqs.sendMessage(params2).promise();
    
    if(resSendMsgQueueFirst.MessageId != null){
        console.log("response sendMsgQueueFirst:", resSendMsgQueueFirst);
    }
     if(resSendMsgQueueSecond.MessageId != null){
        console.log("response sendMsgQueueSecond", resSendMsgQueueSecond);
    }
};


