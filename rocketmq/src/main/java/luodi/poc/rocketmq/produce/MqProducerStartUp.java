package luodi.poc.rocketmq.produce;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liujinjing on 2017/6/12.
 */

@Component
public class MqProducerStartUp extends MqProducerUtil {
    private final static Logger log = LoggerFactory.getLogger(MqProducerStartUp.class);
    public static AtomicInteger producedMsgCount = new AtomicInteger(0);

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage() {
        log.info("access into produceMessage");
        doProduceMessage();
    }

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage2() {
        log.info("access into produceMessage2");
        doProduceMessage();
    }

    @Scheduled(/*cron = "0 0/1 * * * ?"*/ fixedDelay = 1000 * 10)
    public void produceMessage3() {
        log.info("access into produceMessage3");
        doProduceMessage();
    }

    private void doProduceMessage() {
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
    }


}
