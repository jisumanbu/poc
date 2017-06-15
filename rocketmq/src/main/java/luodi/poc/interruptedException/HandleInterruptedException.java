package luodi.poc.interruptedException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liujinjing on 2017/6/12.
 *
 * How does interrupted state change
 *
 *
 */
public class HandleInterruptedException {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    while(true) {

                        /*for (int i = 1; i < 10000000; i ++) {
                            //do something here
                        }*/

                    try {
                        System.out.print(Thread.currentThread().getName() + " is running.\n");
                        Thread.sleep(1000 * 2);
                        } catch (InterruptedException e) {
                            //swallow interruptedException or not
                            System.out.print(Thread.currentThread().getName() + " Met " + e.getMessage() + "\n");
//                            Thread.currentThread().interrupt();
                        }
                    }
//                    return null;

                }
            });
        }

        //sleep 1 second to make sure other threads are all in sleeping
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();

        while (!executorService.isTerminated()) {
            System.out.print("executorService has not terminated yet.\n");
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.print("executorService is terminated.\n");

        while (true) {
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
