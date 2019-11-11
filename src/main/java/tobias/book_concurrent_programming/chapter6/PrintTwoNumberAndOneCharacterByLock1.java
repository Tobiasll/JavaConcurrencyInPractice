package tobias.book_concurrent_programming.chapter6;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintTwoNumberAndOneCharacterByLock1 {

  private static ReentrantLock lock = new ReentrantLock();
  ;
  private static Condition conditionA = lock.newCondition();
  private static Condition conditionB = lock.newCondition();
  private static int[] num = {1, 2, 3, 4, 5, 6, 7, 8};
  private static char[] chars = {'A', 'B', 'C', 'D'};
  private static volatile boolean flag;
  private static volatile AtomicInteger count = new AtomicInteger();
  private final static Integer maxForTimes = 5;


  public static void main(String[] args) throws InterruptedException {

    Thread numThread = new Thread(() -> {
      try {
        lock.lock();
        while (true) {
          flag = true;
          for (int i = 0; i < num.length; i++) {

            if (count.get() < maxForTimes) {
              System.out.println(num[i]);

            }
            if (i % 2 != 0) {
              conditionB.signalAll();
              conditionA.await();
            }

          }
          conditionB.signalAll();
          count.incrementAndGet();
          System.out.println("begin to shutdown ThreadNumber and notify charThread");
          if (count.get() == maxForTimes) {
            System.out.println("count " + count.get());
            Thread.currentThread().interrupt();
          }
        }
      } catch (InterruptedException ignored) {
      } finally {
        lock.unlock();
      }
    });

    Thread charThread = new Thread(() -> {
      try {

        lock.lock();
        while (true) {
          if (count.get() == maxForTimes) {
            System.out.println("count " + count.get());
            Thread.currentThread().interrupt();
          }
          for (char aChar : chars) {
            if (count.get() < maxForTimes) {
              System.out.println(aChar);
            }
            conditionA.signalAll();
            conditionB.await();
          }
        }
      } catch (InterruptedException ignored) {
      } finally {
        lock.unlock();
      }

    });

    numThread.start();
    if (flag) {
      LockSupport.parkNanos(20);
    }
    charThread.start();

    numThread.join();
    charThread.join();

  }

}
