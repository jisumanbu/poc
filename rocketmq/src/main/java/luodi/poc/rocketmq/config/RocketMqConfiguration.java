package luodi.poc.rocketmq.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by liujinjing on 2017/6/15.
 */
@Component
@ConfigurationProperties("rocketMQConf")
public class RocketMqConfiguration {
    private String produceGroup;
    private List<TopicWrapper> topics = new ArrayList();

    public String getProduceGroup() {
        return produceGroup;
    }

    public void setProduceGroup(String produceGroup) {
        this.produceGroup = produceGroup;
    }

    public List<TopicWrapper> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicWrapper> topics) {
        this.topics = topics;
    }
}


