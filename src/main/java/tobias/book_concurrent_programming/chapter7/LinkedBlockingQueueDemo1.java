package tobias.book_concurrent_programming.chapter7;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueDemo1 {

  private static ReentrantLock lock = new ReentrantLock();
  private static Condition condition = lock.newCondition();

  public static void main(String[] args) throws InterruptedException {
    LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);
    queue.put(1);
    System.out.println(queue);
    queue.put(1);
    System.out.println(queue);
    queue.put(1);
    System.out.println(queue);

    Thread thread = new Thread(() -> {
      try {
        lock.lock();
        System.out.println("lock");
        condition.await();
      } catch (Exception ignored) {
      } finally {
        System.out.println("lock unlock");
        lock.unlock();
      }
    });
    
    thread.start();
  }

}
