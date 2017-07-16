package rocketmq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocketmq.producer.MqProducerUtil;

import java.util.List;

/**
 * Created by liujinjing on 2017/6/20.
 */
public class AuthenticationIfPaidSuccess extends AbstractConsumer {
    private final static Logger logger = LoggerFactory.getLogger(MqProducerUtil.class);

    @Override
    public String getConsumerGroup() {
        return this.getClass().getName();
    }

    @Override
    public String getTopic() {
        return "OrderStatus";
    }

    @Override
    public String getTags() {
        return "PaidSuccess";
    }

    @Override
    public MessageListener getListener() {
        MessageListenerConcurrently listener = new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                logger.info("recieved message >>>\n{}", msgs.get(0));

                //assume that it takes 2 seconds to consume 1 message.
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        };
        return listener;
    }
}
