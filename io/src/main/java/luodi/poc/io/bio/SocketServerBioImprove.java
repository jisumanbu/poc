package luodi.poc.io.bio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * resolve Blocking in program level
 *  1. serverSocket.setSoTimeout(100);
 *  2. socket.setSoTimeout(10);
 */
public class SocketServerBioImprove {

    static {
        BasicConfigurator.configure();
    }

    private static Object xWait = new Object();

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(SocketServerBioImprove.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(83);
            serverSocket.setSoTimeout(100);
            while(true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch(SocketTimeoutException e1) {
                    //===========================================================
                    //      执行到这里，说明本次accept没有接收到任何数据报文
                    //      主线程在这里就可以做一些事情，记为X
                    //===========================================================
                    synchronized (SocketServerBioImprove.xWait) {
                        SocketServerBioImprove.LOGGER.info("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件X的处理时间");
                        SocketServerBioImprove.xWait.wait(10);
                    }
                    continue;
                }

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 2048;
                byte[] contextBytes = new byte[maxLen];
                int realLen;
                StringBuffer message = new StringBuffer();
                //下面我们收取信息（这里还是阻塞式的,一直等待，直到有数据可以接受）
                //resolve it by setSoTimeout
                socket.setSoTimeout(10);
                BIORead:while(true) {

                    try {

                        while((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
                            message.append(new String(contextBytes , 0 , realLen));
                    /*
                     * 我们假设读取到“over”关键字，
                     * 表示客户端的所有信息在经过若干次传送后，完成
                     * */
                            if(message.indexOf("over") != -1) {
                                break BIORead;
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        //一次轮询读取结束，开始做其他的事
                        SocketServerBioImprove.LOGGER.info("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件Y的处理时间");
                        continue;
                    }
                }
                //下面打印信息
                SocketServerBioImprove.LOGGER.info("服务器收到来自于端口：" + sourcePort + "的信息：" + message);

                //下面开始发送信息
                out.write("回发响应信息！".getBytes());

                //关闭
                out.close();
                in.close();
                socket.close();
            }
        } catch(Exception e) {
            SocketServerBioImprove.LOGGER.error(e.getMessage(), e);
        } finally {
            if(serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
