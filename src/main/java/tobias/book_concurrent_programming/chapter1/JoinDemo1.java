package tobias.book_concurrent_programming.chapter1;
public class JoinDemo1 {

  public static void main(String[] args) throws InterruptedException {
    Thread threadA = new Thread(() -> {
      System.out.println("threadA run");
      while (true) {}

    });

    final Thread threadMain = Thread.currentThread();

    Thread threadB = new Thread(() -> {
      System.out.println("threadB run");
      try {
        Thread.sleep(1000);
        System.out.println("threadB end");
        threadMain.interrupt();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    threadA.start();
    threadB.start();
//    threadA.join();

  }
}
