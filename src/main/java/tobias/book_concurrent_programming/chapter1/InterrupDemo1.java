package tobias.book_concurrent_programming.chapter1;

public class InterrupDemo1 {

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        System.out.println("interrupted");
      }
    });

    thread.setDaemon(true);
    thread.start();
    Thread.sleep(1000);
//    thread.interrupt();

//    thread.join();
    System.out.println("after join");
  }
}
