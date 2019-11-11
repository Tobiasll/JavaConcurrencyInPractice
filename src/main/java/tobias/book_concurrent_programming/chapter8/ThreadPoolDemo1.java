package tobias.book_concurrent_programming.chapter8;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ThreadPoolDemo1 {

  private static volatile int count;

  static class Task implements Delayed {

    private String name;
    private long expect;
    private long delayed;

    public Task(String name, long delayed) {
      this.name = name;
      this.delayed = delayed;
      this.expect = System.currentTimeMillis() + delayed;
    }


    @Override
    public long getDelay(@NonNull TimeUnit unit) {
      return unit.convert(expect - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
      return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
      return "Task{" +
          "name='" + name + '\'' +
          ", expect=" + expect +
          ", delayed=" + delayed +
          '}';
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    DelayQueue<Task> delayQueue = new DelayQueue<>();
    Random random = new Random();

    for (int i = 0; i < 100; i++) {
      delayQueue.add(new Task("task" + i, random.nextInt(9999) + 1000));
    }
    Future<String>[] tasks = new Future[100];
    for (int i = 0; i < 100; i++) {
      ExecutorService executorService = Executors.newWorkStealingPool();
      tasks[i] = executorService
          .submit(() -> delayQueue.take() + " thread: = " + Thread.currentThread().getName());
      System.out.println("submit");
    }
    System.out.println("finish to submit");
    for (Future<String> task : tasks) {
      System.out.println(task.get());
    }

  }
}
