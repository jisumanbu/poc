package luodi.poc.rocketmq.consume;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liujinjing on 2017/6/1.
 *
 *  Concrete consumer listener must provide group/topic, tags
 *
 */
public abstract class AbstractConsumer {
    private final static Logger log = LoggerFactory.getLogger(AbstractConsumer.class);

    public DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
    public AtomicInteger consumedMsgCount = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        consumer.setConsumerGroup(getConsumerGroup());
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(getMessageModel());
        try {
            consumer.subscribe(getTopic(), "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        registerListener();

        try {
            consumer.start();
        } catch (MQClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    public MessageModel getMessageModel() {
        return MessageModel.CLUSTERING;
    }

    public abstract String getConsumerGroup();

    public abstract String getTopic();

    public abstract void registerListener();

}
