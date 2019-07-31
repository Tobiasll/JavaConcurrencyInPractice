package tobias.book_concurrent_programming.chapter2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AfreshSoft {

  private volatile static int num = 0;
  private volatile static boolean ready = false;


  static class ReadThread extends Thread {

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        System.out.println("readThread ready " + ready + num);
        if (ready) {
          System.out.println(num + num);
        }
      }
    }
  }

  static class WriterThread extends Thread {

    @Override
    public void run() {
      num = 2;
      ready = true;
      System.out.println("WriterThread is over");
    }
  }


  public static void main(String[] args) throws InterruptedException {
    ReadThread rt = new ReadThread();
    rt.start();

    WriterThread wt = new WriterThread();
    wt.start();

    Thread.sleep(10);
    Random random = new Random();
    int i = ThreadLocalRandom.current().nextInt();
    rt.interrupt();
  }
}
