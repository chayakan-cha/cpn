const aws = require('aws-sdk');
const s3 = new aws.S3();
// aws s3 cp RANDOM.txt s3://test2retrivedata
exports.handler = async (event) => {
    const snsTopicArn = process.env.SNS_TOPIC_ARN
    const queueUrl = process.env.QUEUE_URL
    const bucketName = process.env.BUCKET_NAME
    const s3FileName = process.env.S3_FILE_NAME
    const currentDate = new Date().toISOString().slice(0, 10);
    let snsContext;
    
    for (const { messageId, body } of event.Records) {
        console.log('SQS message %s: %j', messageId, body);
        snsContext = body;
    }
    const objSns = JSON.parse(snsContext);
    
    async function getObject (bucket, objectKey) {
      try {
        const params = {
          Bucket: bucket,
          Key: objectKey 
        }
        const data = await s3.getObject(params).promise();
        return data.Body.toString('utf-8');
      } catch (e) {
        throw new Error(`Could not retrieve file from S3: ${e.message}`)
      }
    }
    const resultPorfile = await getObject(bucketName, s3FileName);
    const objS3 = JSON.parse(resultPorfile);
    // console.log(objS3.address);

    const composeProfile = `{username: ${objSns.username}, login_at: ${objSns.login_at}, name: ${objS3.name}, address: ${objS3.address}}`;
    var paramsPublish = {Message: composeProfile,
                Subject: "Topic",
                TopicArn: snsTopicArn
    };
    // // console.log(profile);
    var sns = new aws.SNS({ apiVersion: currentDate});   
    const resPublish = await sns.publish(paramsPublish).promise();
    console.log(resPublish);
}
  
