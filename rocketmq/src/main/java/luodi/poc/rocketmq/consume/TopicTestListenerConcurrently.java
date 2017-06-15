package luodi.poc.rocketmq.consume;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liujinjing on 2017/6/13.
 */

@Component
public class TopicTestListenerConcurrently extends AbstractConsumer {
    private final static Logger log = LoggerFactory.getLogger(TopicTestListenerConcurrently.class);

    @Scheduled(fixedDelay = 1000 * 5)
    private void monitorConsumedCount() {
        log.info(" consumed message count = " + consumedMsgCount.get());
    }

    public String getConsumerGroup() {
        return "Test_20170613";
    }

    public String getTopic() {
        return "TopicTest";
    }

    public void registerListener() {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                log.info("Start to consume message[{}] Concurrently>>> \n[{}], totally consumed {} messages", new String(msg
                                .getBody()),
                        msgs.get(0), msgs.size());
                consumedMsgCount.incrementAndGet();
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }


}
