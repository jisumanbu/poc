package luodi.poc.rocketmq.produce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liujinjing on 2017/6/12.
 */

@Component
public class MqProducerStartUp extends MqProducer {
    public static AtomicInteger msgId = new AtomicInteger(0);
    public static String TOPIC_NAME = "TopicTest";
    @Autowired
    MqProducer mqProducer;


    private final static Logger log = LoggerFactory.getLogger(MqProducerStartUp.class);
    public static AtomicInteger producedMsgCount = new AtomicInteger(0);

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage() {
        log.info("access into produceMessage");

    }

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage2() {
        try {
            mqProducer.sendMsg("msgId=" + msgId.incrementAndGet() + ", produced by producer2.", TOPIC_NAME, "producer2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage3() {
        try {
            mqProducer.sendMsg("msgId=" + msgId.incrementAndGet() + ", produced by producer2.", TOPIC_NAME, "producer3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void doSendMessage() {
        for (int i = 1; i <= 50; i++) {
            Message msg = null;
            int paymentId = producedMsgCount.incrementAndGet();
            try {
                msg = new Message("TopicTest",
                        "failure",
                        ("paymentId :  " + paymentId).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            SendResult sendResult = null;
            try {
                sendResult = producer.send(msg);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("produced a new message, paymentId : {} >>> {}", paymentId, sendResult);
//            log.info(sendResult.toString());
        }
    }*/


}
