package tobias.book_concurrent_programming.chapter4;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongCountDemo1 {

  private static AtomicLong atomicLong = new AtomicLong();
  private static ThreadLocalRandom threadLocalRandom;
  private static int[] one = new int[10];
  private static int[] two = new int[10];
  private static int[] three = new int[10];


  public static void main(String[] args) throws InterruptedException {

    Thread threadA = new Thread(() -> {
      threadLocalRandom = ThreadLocalRandom.current();
      for (int i = 0; i < one.length; i++) {
        one[i] = threadLocalRandom.nextInt(5);
        if (one[i] == 0) {
          atomicLong.incrementAndGet();
        }
      }
    });

    Thread threadB = new Thread(() -> {
      threadLocalRandom = ThreadLocalRandom.current();
      for (int i = 0; i < two.length; i++) {
        two[i] = threadLocalRandom.nextInt(7);
        if (two[i] == 0) {
          atomicLong.incrementAndGet();
        }
      }
    });

    Thread threadC = new Thread(() -> {
      threadLocalRandom = ThreadLocalRandom.current();
      for (int i = 0; i < three.length; i++) {
        three[i] = threadLocalRandom.nextInt(5);
        if (three[i] == 0) {
          atomicLong.incrementAndGet();
        }
      }
    });

    threadA.start();
    threadB.start();
    threadC.start();

    threadA.join();
    threadB.join();
    threadC.join();

    System.out.println(Arrays.toString(one));
    System.out.println(Arrays.toString(two));
    System.out.println(Arrays.toString(three));
    System.out.println(atomicLong.get());
  }

}
