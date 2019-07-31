package tobias.book_concurrent_programming.chapter6;
import	java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.ReentrantLock;

public class AQSConditionDemo1 {

  private static ReentrantLock lock = new ReentrantLock();
  private static Condition condition = lock.newCondition();

  public static void main(String[] args) throws InterruptedException {

    Thread threadA = new Thread(() -> {
      lock.lock();
      try {
        System.out.println("begin to await");
        condition.await();
        System.out.println("end to await");
      }catch (Exception ignored) {

      } finally {
        System.out.println("unlock");
        lock.unlock();
      }
    });

    Thread threadB = new Thread(() -> {
      lock.lock();
      try {
        System.out.println("begin to signal");
        condition.signal();
        System.out.println("end to signal");
      } finally {
        System.out.println("signal unlock");
        lock.unlock();
      }
    });

    threadA.start();
    Thread.sleep(1000);
    threadB.start();
  }

}
