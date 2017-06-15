package luodi.poc.rocketmq.consume;

import org.apache.rocketmq.client.consumer.listener.*;
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
public class TopicTestListenerOrderly extends AbstractConsumer {
    private final static Logger log = LoggerFactory.getLogger(TopicTestListenerOrderly.class);

    @Scheduled(fixedDelay = 1000 * 5)
    private void monitorConsumedCount() {
        log.info(" consumed message count = " + consumedMsgCount.get());
    }


    public String getConsumerGroup() {
        return "groupConsumer";
    }

    public String getTopic() {
        return "TopicTest";
    }

    public void registerListener() {
        consumer.registerMessageListener(new MessageListenerOrderly() {
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                MessageExt msg = msgs.get(0);
                log.info("Start to consume message[{}] Orderly>>> \n[{}], totally consumed {} messages", new String(msg
                                .getBody()),
                        msgs.get(0), msgs.size());
                consumedMsgCount.incrementAndGet();
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
    }


}
