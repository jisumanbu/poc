package rocketmq.consumer;

import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocketmq.producer.MqProducerUtil;

import java.util.Map;

/**
 * Created by liujinjing on 2017/6/20.
 */
public class ConsumerTest {
    private final static Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {
            SendResult sendResult = MqProducerUtil.sendMsg("msg_1", i+"", "OrderStatus", "PaidSuccess");
            logger.info(sendResult.toString());
        }

        AuthenticationIfPaidSuccess consumer1 = new AuthenticationIfPaidSuccess();
        AuthenticationIfPaidSuccess consumer2 = new AuthenticationIfPaidSuccess();

        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
