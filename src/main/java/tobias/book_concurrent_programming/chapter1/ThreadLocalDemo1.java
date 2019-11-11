package tobias.book_concurrent_programming.chapter1;

public class ThreadLocalDemo1 {

  private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
  private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

  public static void main(String[] args) {

    test1();

  }

  private static void test1() {
    threadLocal.set("mainTest");
    inheritableThreadLocal.set("inheritableThreadLocal");
    Thread threadA = new Thread(() -> {
      threadLocal.set("Thread A get :: ");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(threadLocal.get());
      System.out.println(threadLocal.get() + inheritableThreadLocal.get());
      threadLocal.remove();
    });
    System.out.println("main get :: " + threadLocal.get());
    threadA.start();
  }

}
