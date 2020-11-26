package tobias.book_concurrent_programming.chapter2;


import sun.misc.Contended;

@Contended
public class SharingLong {

  private volatile long value;

//  private volatile long p1, p2, p3, p4, p5, p6;

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public void increment() {
    value++;
  }
}
