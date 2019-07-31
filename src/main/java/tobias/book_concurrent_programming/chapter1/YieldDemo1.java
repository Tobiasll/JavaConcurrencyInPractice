package tobias.book_concurrent_programming.chapter1;

public class YieldDemo1 implements Runnable {

  YieldDemo1() {
    new Thread(this).start();
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      if ((i % 5) == 0) {
        System.out.println(Thread.currentThread().getName() + "yield cpu !");
        Thread.yield();
      }
    }

    System.out.println(Thread.currentThread().getName() + "is over");
  }

  public static void main(String[] args) throws InterruptedException {
    new YieldDemo1();

    new YieldDemo1();

    new YieldDemo1();
  }
}
