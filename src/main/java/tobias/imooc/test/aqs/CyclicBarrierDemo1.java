package tobias.imooc.test.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo1 {

  private static final int THREAD_COUNT = 200;
  private static int count = 0;
  private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
    System.out.println("running ---------------------------------------------------------------");
  });

  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = Executors
        .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    for (int i = 0; i < THREAD_COUNT; i++) {
      Thread.sleep(500);
      executorService.execute(() -> {
        test();
      });
    }
    executorService.shutdown();
  }

  private static void test() {
    try {
      Thread.sleep(2000);
      System.out.println("before wait " + count++);
      cyclicBarrier.await();
      System.out.println("after wait " + count);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
