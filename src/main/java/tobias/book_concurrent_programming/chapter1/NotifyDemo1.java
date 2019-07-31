package tobias.book_concurrent_programming.chapter1;

public class NotifyDemo1 {

  private static volatile Object object = new Object();

  public static void main(String[] args) throws InterruptedException {
    Thread threadA = new Thread(() -> {
      System.out.println("get lock " + Thread.currentThread().getName());
      synchronized (object) {
        System.out.println("start wait " + Thread.currentThread().getName());
        try {
          object.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("end wait" + Thread.currentThread().getName());
      }
    }, "ThreadA");

    Thread threadB = new Thread(() -> {
      System.out.println("get lock " + Thread.currentThread().getName());
      synchronized (object) {
        System.out.println("start wait " + Thread.currentThread().getName());
        try {
          object.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("end wait" + Thread.currentThread().getName());
      }
    }, "ThreadB");

    Thread threadC = new Thread(() -> {
      System.out.println("get lock " + Thread.currentThread().getName());
      synchronized (object) {
        System.out.println("start notify " + Thread.currentThread().getName());
          object.notifyAll();
        System.out.println("end notify" + Thread.currentThread().getName());
      }
    }, "ThreadC");

    threadA.start();
    threadB.start();

    Thread.sleep(1000);
    threadC.start();

  }

}
