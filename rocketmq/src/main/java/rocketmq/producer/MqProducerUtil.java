package rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocketmq.producer.callback.DefaultSendCallback;
import rocketmq.producer.callback.SendResultWrapper;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 * Created by liujinjing on 2017/6/1.
 * <p>
 * producerGroup: one producerGroup per application as default
 * <p>
 * producerGroup has nothing to do with consumerGroup
 * <p>
 * producerGroup用来表示一个发送消息应用，一个 Producer Group 下包含多个 Producer 实例，可以是多台机器，也可以
 * 是一台机器的多个进程，或者一个进程的多个 Producer 对象。
 * <p>
 * 一个 Producer Group 可以发送多个 Topic 消息.
 * <p>
 * Producer Group 作用如下:
 * 1. 标识一类 Producer
 * 2. 可以通过运维工具查询这个发送消息应用下有多个 Producer 实例
 * 3. 发送分布式事务消息时，如果 Producer 中途意外宕机，Broker 会主动回调 Producer Group 内的任意一台机器来确认事务状态。
 * <p>
 * 2. multi producers
 */
public class MqProducerUtil {
    private final static Logger logger = LoggerFactory.getLogger(MqProducerUtil.class);

    public static DefaultMQProducer producer = null;

    static {
        producer = new DefaultMQProducer("groupProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName(UUID.randomUUID().toString());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static DefaultMQProducer getProducer() {
        return producer;
    }

    /**
     * Send message synchronized.
     * @param msgContent
     * @param messageKey
     * @param topic
     * @param tags
     * @return
     */
    public static SendResult sendMsg(String msgContent, String messageKey, String topic, String tags) {
        SendResult sendResult = null;

        try {
            Message msg = new Message(topic,
                    tags,
                    messageKey,
                    msgContent.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            sendResult = producer.send(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

        return sendResult;
    }

    /**
     *
     * @param msgContent
     * @param messageKey
     * @param topic
     * @param tags
     * @param sendCallback
     * @return
     */
    //have not finished designing yet, so do not use it now.
    @Deprecated
    public static SendResultWrapper sendMsgAsyn(String msgContent, String messageKey, String topic, String tags,
                                                SendCallback sendCallback) {
        if (sendCallback == null) {
            sendCallback = new DefaultSendCallback();
        }

        SendResultWrapper sendResultWrapper = new SendResultWrapper();

        try {
            Message msg = new Message(topic,
                    tags,
                    messageKey,
                    msgContent.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.send(msg, sendCallback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        return null;
    }


}
