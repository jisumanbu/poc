package rocketmq.producer.callback;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * Created by liujinjing on 2017/6/20.
 */
public class DefaultSendCallback implements SendCallback{
    SendResultWrapper sendResultWrapper = new SendResultWrapper();

    @Override
    public void onSuccess(SendResult sendResult) {
        sendResultWrapper.addSuucess(sendResult);
    }

    @Override
    public void onException(Throwable e) {
        sendResultWrapper.addFailedMessage(e);
    }
}
