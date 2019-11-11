package tobias.book_concurrent_programming.chapter6;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo1 {

  public static void main(String[] args) throws InterruptedException {
//    test4();
    test5();
    Thread.sleep(10000);
  }

  private static void test1() {
    System.out.println("begin to park");
    LockSupport.park();
    System.out.println("end to park");
  }

  private static void test2() {
    System.out.println("begin to park");
    LockSupport.unpark(Thread.currentThread());
    LockSupport.park();
    System.out.println("end to park");
  }

  private static void test3() throws InterruptedException {

    Thread thread = new Thread(() -> {
      System.out.println("subThread begin to park");
      LockSupport.park();
      System.out.println("subThread end to park");
    });

    System.out.println("mainThread begin to block");
    thread.start();
    Thread.sleep(1000);
    System.out.println("mainThread begin to unpark subThread");
    LockSupport.unpark(thread);
  }

  private static void test4() throws InterruptedException {

    Thread thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        System.out.println("subThread begin to park");
        System.out.println("enter while ");
        LockSupport.park();
      }
      System.out.println("subThread end to park");
    });

    thread.start();
    System.out.println("mainThread begin to block");
    Thread.sleep(1000);
    System.out.println("mainThread begin to interrupted subThread");
    thread.interrupt();
  }

  private static void test5() throws InterruptedException {
    System.out.println("test5 park start!" + System.currentTimeMillis());
    LockSupport.parkUntil(new LockSupportDemo1(), System.currentTimeMillis() + 20000);
    System.out.println("test5 park end!");
  }


}
