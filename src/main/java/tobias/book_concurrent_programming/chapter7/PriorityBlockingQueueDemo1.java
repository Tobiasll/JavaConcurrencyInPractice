package tobias.book_concurrent_programming.chapter7;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PriorityBlockingQueueDemo1 {

  static class Task implements Comparable<Task> {

    private int priority;
    private String taskName;

    public Task(int priority, String taskName) {
      this.priority = priority;
      this.taskName = taskName;
    }

    public int getPriority() {
      return priority;
    }

    @Override
    public int compareTo(@NonNull Task o) {
      return this.priority > o.getPriority() ? 1 : -1;
    }

    public void doSomething(Semaphore semaphore) throws InterruptedException {
      semaphore.acquire();
      Thread.sleep(1000);
      System.out.println(taskName + " :: " + priority);
      semaphore.release();
    }
  }

  public static void main(String[] args) throws InterruptedException {

    PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
    CountDownLatch countDownLatch = new CountDownLatch(10);
    Semaphore semaphore = new Semaphore(1100);
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      queue.offer(new Task(random.nextInt(100), "taskName" + i));
    }

    long startTime = System.currentTimeMillis();
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    for (int i = 0; i < 1000; i++) {

      executorService.execute(() -> {
        try {
          queue.poll().doSomething(semaphore);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }/*finally {
//          countDownLatch.countDown();
        }*/
      });
      Thread.sleep(10);
    }
    executorService.shutdown();

    try {
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
      System.out.println(System.currentTimeMillis() - startTime);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
