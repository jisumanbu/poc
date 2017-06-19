package rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 * Created by liujinjing on 2017/6/1.
 * <p>
 * producerGroup: one producerGroup per application
 * producerGroup has nothing to do with consumerGroup
 * producerGroup用来表示一个发送消息应用，一个 Producer Group 下包含多个 Producer 实例，可以是多台机器，也可以
 * 是一台机器的多个进程，或者一个进程的多个 Producer 对象。一个 Producer Group 可以发送多个 Topic 消息，Producer Group 作用如下:
 * 1. 标识一类 Producer
 * 2. 可以通过运维工具查询这个发送消息应用下有多个 Producer 实例
 * 3. 发送分布式事务消息时，如果 Producer 中途意外宕机，Broker 会主动回调 Producer Group 内的任意一台机器来确认事务状态。
 * <p>
 * <p>
 * 2. multi producers
 */
public class MqProducerUtil {
    public static DefaultMQProducer producer = null;

    {
        producer = new DefaultMQProducer("groupProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName(UUID.randomUUID().toString());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static SendResult sendMsg(String msgContent, String topic, String tags) {
        SendResult sendResult = null;

        Message msg = null;
        try {
            msg = new Message(topic,
                    tags,
                    msgContent.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                }

                @Override
                public void onException(Throwable e) {

                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return sendResult;
    }

}
