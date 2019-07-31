package tobias.imooc.test.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownDemo1 {

  private static final int THREA_COUNT = 200;
  private static int count = 0;

  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = Executors.newCachedThreadPool();
    CountDownLatch countDownDemo1 = new CountDownLatch(THREA_COUNT);

    for (int i = 0; i < THREA_COUNT; i++) {

      executorService.execute(() -> {

        try {
          synchronized (executorService) {
            Thread.sleep(100);
            System.out.println(count++);
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          countDownDemo1.countDown();
        }
      });
    }
    countDownDemo1.await(10, TimeUnit.MILLISECONDS);
    executorService.shutdown();
    System.out.println("finish");

  }

}
