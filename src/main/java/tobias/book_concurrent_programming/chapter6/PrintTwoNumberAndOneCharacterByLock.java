package tobias.book_concurrent_programming.chapter6;

import java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintTwoNumberAndOneCharacterByLock {

  private static ReentrantLock lock = new ReentrantLock();
  ;
  private static Condition condition = lock.newCondition();
  private static int[] num = {1, 2, 3, 4, 5, 6, 7, 8};
  private static char[] chars = {'A', 'B', 'C', 'D'};


  public static void main(String[] args) throws InterruptedException {

    for (int x = 0; x < 1; x++) {
      Thread numThread = new Thread(() -> {
        try {
          lock.lock();
          for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
            if (i % 2 != 0) {
              condition.signalAll();
              condition.await();
            }
          }
          LockSupport.unpark(Thread.currentThread());
          System.out.println("begin to shutdown ThreadNumber and notify charThread");
          condition.signalAll();
        } catch (InterruptedException ignored) {
        } finally {
          lock.unlock();
        }
      });

      Thread charThread = new Thread(() -> {
        try {
          lock.lock();
          for (char aChar : chars) {
            System.out.println(aChar);
            condition.signalAll();
            condition.await();
          }
        } catch (InterruptedException ignored) {
        } finally {
          lock.unlock();
        }
      });
      numThread.start();
//      Thread.sleep(1);
      charThread.start();

      numThread.join();
      charThread.join();
    }
  }

}
