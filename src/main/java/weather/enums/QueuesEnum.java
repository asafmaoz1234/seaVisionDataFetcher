package weather.enums;

import weather.client.SQSClient;

import java.util.HashMap;
import java.util.Map;

import static weather.config.EnvParams.SQS_EMAIL_QUEUE_URL;

public enum QueuesEnum {
    EMAIL_QUEUE(SQS_EMAIL_QUEUE_URL, "email_queue", "email_queue_group_id"){
        @Override
        public void publishToQ(String messageBody, String destEmail, String emailSubject) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("fromProcess", emailSubject);
            attributes.put("to", destEmail);
            SQSClient.getInstance()
                    .postMessageToQueue(attributes,
                            messageBody,
                            QueuesEnum.EMAIL_QUEUE);
        }
    };

    private String url;
    private String queueName;
    private String queueGroupId;
    QueuesEnum(String url, String queueName, String queueGroupId) {
        this.url = url;
        this.queueName = queueName;
        this.queueGroupId = queueGroupId;
    }

    public abstract void publishToQ(String messageBody, String destEmail, String emailSubject);

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueGroupId() {
        return queueGroupId;
    }

    public void setQueueGroupId(String queueGroupId) {
        this.queueGroupId = queueGroupId;
    }
}
