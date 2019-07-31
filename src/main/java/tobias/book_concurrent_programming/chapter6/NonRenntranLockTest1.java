package tobias.book_concurrent_programming.chapter6;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

public class NonRenntranLockTest1 {

  private static NonReentranLock lock = new NonReentranLock();
  private static Condition nonEmpty = lock.newCondition();
  private static Condition nonFull = lock.newCondition();
  private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
  private static int queueSize = 20;
  private static int index ;

  public static void main(String[] args) {

    Thread product = new Thread(() -> {
      while (true) {
        try {
          lock.lock();
          while (queue.size() == queueSize) {
            nonEmpty.await();
          }
          queue.add("test" + index++);
          System.out.println("add index" + index);
          nonFull.signalAll();
        } catch (Exception ignored) {
        } finally {
          lock.unlock();
        }
      }
    });

    Thread consumer = new Thread(() -> {
      while (true) {
        try {
          lock.lock();
          while (queue.size() == 0) {
            nonFull.await();
          }
          Thread.sleep(1000);
          System.out.println(queue.poll());
          nonEmpty.signalAll();
        } catch (Exception ignored) {
        } finally {
          lock.unlock();
        }
      }
    });

    product.start();
    consumer.start();


    //0000 0000 0000 0000 1111 1111 1111 1111
    //1111 1111 1111 1111 0000 1111 1110 1010

    //1111 1111 1111 1111 1111 0000 0001 0101
    //0000 0000 0000 0000 0000 0000 0000 1111
  }

}
