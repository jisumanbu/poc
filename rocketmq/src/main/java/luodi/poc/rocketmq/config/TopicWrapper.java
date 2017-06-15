package luodi.poc.rocketmq.config;

import java.util.List;

/**
 * Created by liujinjing on 2017/6/15.
 */

public class TopicWrapper {
    private String topicName;
    private List<String> tags;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}