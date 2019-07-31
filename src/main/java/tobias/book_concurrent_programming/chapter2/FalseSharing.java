package tobias.book_concurrent_programming.chapter2;

public class FalseSharing {

  static class TestThread extends Thread {

    private SharingLong[] shadingLongs;
    private int index;

    public TestThread(SharingLong[] shadingLongs, int index) {
      this.shadingLongs = shadingLongs;
      this.index = index;
    }

    @Override
    public void run() {
      for (int i = 0; i < 100000000; i++) {
        shadingLongs[index].increment();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    int size = Runtime.getRuntime().availableProcessors();
    System.out.println("size :: " + size);
    SharingLong[] shadingLongs = new SharingLong[size];
    for (int i = 0; i < size; i++) {
      shadingLongs[i] = new SharingLong();
    }

    Thread[] threads = new TestThread[size];
    for (int i = 0; i < size; i++) {
      threads[i] = new TestThread(shadingLongs, i);
    }

    for (Thread thread : threads) {
      thread.start();
    }
    long start = System.currentTimeMillis();

    for (Thread thread : threads) {
      thread.join();
    }

    long end = System.currentTimeMillis();
    System.out.println("" + (end - start));
    // @contended on value 10803 // @contended on class 10114 // nothing 10228 // have six long value to resolve false sharing 4206

  }

}
