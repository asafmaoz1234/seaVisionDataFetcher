package weather;

import static weather.config.EnvParams.SQS_EMAIL_QUEUE_URL;

public enum QueuesEnum {
    EMAIL_QUEUE(SQS_EMAIL_QUEUE_URL, "email_queue", "email_queue_group_id");

    private String url;
    private String queueName;
    private String queueGroupId;
    QueuesEnum(String url, String queueName, String queueGroupId) {
        this.url = url;
        this.queueName = queueName;
        this.queueGroupId = queueGroupId;
    }

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
