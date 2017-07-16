package rocketmq.producer.callback;

import org.apache.rocketmq.client.producer.SendResult;
import sun.plugin2.message.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by liujinjing on 2017/6/20.
 *
 * this should be a singleton instance shared by all SendCallBack.
 */
public class SendResultWrapper extends SendResult implements Callable{
    ConcurrentLinkedQueue<SendResult> successResults = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<Throwable> failedSendingMsgs = new ConcurrentLinkedQueue<>();

    public void addSuucess(SendResult successResult) {
        successResults.add(successResult);
    }

    public void addFailedMessage(Throwable e) {
        failedSendingMsgs.add(e);
    }


    //TODO, record success result, deal failed exception.
    @Override
    public Object call() throws Exception {
        return null;
    }
}
