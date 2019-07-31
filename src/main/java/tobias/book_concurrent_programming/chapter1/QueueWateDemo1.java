package tobias.book_concurrent_programming.chapter1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class QueueWateDemo1 {
  private static final int MAX_SIZE = 1;
  private static final Queue<String> QUEUE = new LinkedList<>(Arrays.asList("1", " 2", "3", "4", "5"));


  public static void main(String[] args) throws InterruptedException {


    for (int i = 0; i < 100; i++) {

        final int count = i;
        new Thread(() -> {
          synchronized (QUEUE) {
            while (QUEUE.size() == MAX_SIZE) {
              try {
                QUEUE.wait();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
            QUEUE.add(count + "");
            System.out.println("QUEUE add " + count);
            QUEUE.notifyAll();
          }
        }).start();

        new Thread(() -> {
          synchronized (QUEUE) {
            while (QUEUE.size() == 0) {
              try {
                QUEUE.wait();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
            System.out.println("Queue remove " + QUEUE.remove());
            QUEUE.notifyAll();
          }
        }).start();
      }

  }
}
