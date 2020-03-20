package utils;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AwsQueueUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AwsQueueUtils.class);

    public void sendMessage(String msg, String queueUrl) {

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        try {
            LOG.info("Sending message to queue: " + queueUrl);
            LOG.info("Message : " + msg);
            sqs.sendMessage(new SendMessageRequest(queueUrl, msg));
        } catch (Exception e) {
            LOG.error("Error on publish message" + e);
        }
    }
}