package tobias.book_concurrent_programming.chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintTwoNumberAndOneCharacterBySynchronize {

  private static int[] num = {1, 2, 3, 4, 5, 6, 7, 8};
  private static char[] chars = {'A', 'B', 'C', 'D'};
  private static Lock lock = new ReentrantLock();

  public static void main(String[] args) throws InterruptedException {

    for (int x = 0; x < 1000; x++) {
      Thread numThread = new Thread(() -> {
        synchronized (PrintTwoNumberAndOneCharacterBySynchronize.class) {
          for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
            if (i % 2 != 0) {
              try {
                PrintTwoNumberAndOneCharacterBySynchronize.class.notifyAll();
                PrintTwoNumberAndOneCharacterBySynchronize.class.wait();
              } catch (InterruptedException ignored) {
              }
            }
          }
          LockSupport.unpark(Thread.currentThread());
          System.out.println("begin to shutdown ThreadNumber and notify charThread");
          PrintTwoNumberAndOneCharacterBySynchronize.class.notifyAll();
        }
      });

      Thread charThread = new Thread(() -> {
        synchronized (PrintTwoNumberAndOneCharacterBySynchronize.class) {
          for (char aChar : chars) {
            System.out.println(aChar);
            try {
              PrintTwoNumberAndOneCharacterBySynchronize.class.notifyAll();
              PrintTwoNumberAndOneCharacterBySynchronize.class.wait();
            } catch (InterruptedException ignored) {
            }
          }
        }
      });
      numThread.start();
      Thread.sleep(5);
      charThread.start();

      numThread.join();
      charThread.join();
    }
  }

}
