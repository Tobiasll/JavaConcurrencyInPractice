package tobias.imooc.test.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo1 {

  private static int count;
  public static final int THREAD_COUNT = 20;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Semaphore semaphore = new Semaphore(5);

    for (int i = 0; i < THREAD_COUNT; i++) {
      executorService.execute(() -> count2(semaphore));
    }

    executorService.shutdown();
  }

  private static void count1(Semaphore semaphore) {
    try {
      semaphore.acquire(2);
      Thread.sleep(1000);
      System.out.println(count++);
      semaphore.release(2);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void count2(Semaphore semaphore) {
    try {
      if (semaphore.tryAcquire(2, 500, TimeUnit.MILLISECONDS)) {

        Thread.sleep(500);
        System.out.println(count++);
        semaphore.release(2);
      }
    } catch (Exception e) {
      e.printStackTrace();

    }
  }
}
