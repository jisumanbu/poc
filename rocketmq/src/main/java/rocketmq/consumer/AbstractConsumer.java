package rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liujinjing on 2017/6/1.
 * <p>
 * Concrete consumer listener must provide group/topic, tags
 */
public abstract class AbstractConsumer {
    private final static Logger log = LoggerFactory.getLogger(AbstractConsumer.class);

    public DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
    public AtomicInteger consumedMsgCount = new AtomicInteger(0);

    {
        consumer.setConsumerGroup(getConsumerGroup());
        //TODO get namesrvAddr from ENV
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(getMessageModel());
        try {
            consumer.subscribe(getTopic(), "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.setMessageListener(getListener());

        try {
            consumer.start();
        } catch (MQClientException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public MessageModel getMessageModel() {
        return MessageModel.CLUSTERING;
    }

    public abstract String getConsumerGroup();

    public abstract String getTopic();

    public abstract MessageListener getListener();

}
