package tobias.book_concurrent_programming.chapter1;

public class DeadLockDemo1 {

  private static volatile Object objectA = new Object();
  private static volatile Object objectB = new Object();

  public static void main(String[] args) throws Exception {
    Thread threadA = new Thread(() -> {
      synchronized (objectA) {
        System.out.println("获得对象A锁");
        synchronized (objectB) {
          System.out.println("获得对象B锁");
          try {
            System.out.println("调用ObjectA::wait方法");
            objectA.wait();
            System.out.println("sdasdasdad");

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });


    Thread threadB = new Thread(() -> {
      synchronized (objectA) {
        System.out.println("获得对象A锁");
        System.out.println("尝试获取对象B锁");
        synchronized (objectB) {
          System.out.println("获取对象B锁");
        }
      }
    });
    threadA.start();
    Thread.sleep(1000);
    threadB.start();

    threadA.join();
    threadB.join();
  }

}
