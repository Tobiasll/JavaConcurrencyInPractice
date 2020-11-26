//package tobias.book_concurrent_programming.chapter6;
//
//import com.google.common.collect.Maps;
//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;
//import java.util.HashMap;
//import java.util.concurrent.locks.StampedLock;
//import org.apache.commons.beanutils.BeanMap;
//
//class Point {
//
//  private double x = 1, y = 2;
//  private final StampedLock sl = new StampedLock();
//
//
//  // an exclusively locked method
//  void move(double deltaX, double deltaY) {
//    long stamp = sl.writeLock();
//    try {
//      x += deltaX;
//      y += deltaY;
//    } finally {
//      sl.unlockWrite(stamp);
//    }
//  }
//
//  double distanceFromOrigin() {
//    long stamp = sl.tryOptimisticRead();
//    double currentX = x, currentY = y;
//    System.out.println(x + "  " + y);
//    if (!sl.validate(stamp)) {
//      stamp = sl.readLock();
//      try {
//        currentX = x;
//        currentY = y;
//      } finally {
//        sl.unlockRead(stamp);
//      }
//    }
//    return currentX + currentY;
//  }
//
//  // a read-only method
//  // upgrade from optimistic read to read lock
//  double distanceFromOrigin2() {
//    long stamp = sl.tryOptimisticRead();
//    try {
//      retryHoldingLock:
//      for (; ; stamp = sl.readLock()) {
//        if (stamp == 0L) {
//          continue retryHoldingLock;
//        }
//        // possibly racy reads
//        double currentX = x;
//        double currentY = y;
//        if (!sl.validate(stamp)) {
//          continue retryHoldingLock;
//        }
//        return Math.hypot(currentX, currentY);
//      }
//    } finally {
//      if (StampedLock.isReadLockStamp(stamp)) {
//        sl.unlockRead(stamp);
//      }
//    }
//  }
//
//  // upgrade from optimistic read to write lock
//  void moveIfAtOrigin(double newX, double newY) {
//    long stamp = sl.tryOptimisticRead();
//    try {
//      retryHoldingLock:
//      for (; ; stamp = sl.writeLock()) {
//        if (stamp == 0L) {
//          continue retryHoldingLock;
//        }
//        // possibly racy reads
//        double currentX = x;
//        double currentY = y;
//        if (!sl.validate(stamp)) {
//          continue retryHoldingLock;
//        }
//        if (currentX != 0.0 || currentY != 0.0) {
//          break;
//        }
//        stamp = sl.tryConvertToWriteLock(stamp);
//        if (stamp == 0L) {
//          continue retryHoldingLock;
//        }
//        // exclusive access
//        x = newX;
//        y = newY;
//        return;
//      }
//    } finally {
//      if (StampedLock.isWriteLockStamp(stamp)) {
//        sl.unlockWrite(stamp);
//      }
//    }
//  }
//
//  // Upgrade read lock to write lock
//  void moveIfAtOrigin2(double newX, double newY) {
//    long stamp = sl.readLock();
//    try {
//      while (x == 0.0 && y == 0.0) {
//        long ws = sl.tryConvertToWriteLock(stamp);
//        if (ws != 0L) {
//          stamp = ws;
//          x = newX;
//          y = newY;
//          break;
//        } else {
//          sl.unlockRead(stamp);
//          stamp = sl.writeLock();
//        }
//      }
//    } finally {
//      sl.unlock(stamp);
//    }
//  }
//
//  public double getX() {
//    return x;
//  }
//
//  public double getY() {
//    return y;
//  }
//
//  @Override
//  public String toString() {
//    return "Point{" +
//        "x=" + x +
//        ", y=" + y +
//        '}';
//  }
//}
//
//public class StampedLockDemo1 {
//
//  public static void main(String[] args) throws Exception {
//    Point p = new Point();
//    System.out.println(p);
//    Thread threadA = new Thread(() -> System.out.println(p.distanceFromOrigin()));
//    Thread threadB = new Thread(() -> p.move(1, 2));
//
//    threadA.start();
//    threadB.start();
//    Thread.sleep(100);
//    System.out.println(p);
//    VarHandle varHandleX = MethodHandles.privateLookupIn(p.getClass(), MethodHandles.lookup())
//                                        .findVarHandle(p.getClass(), "x", double.class);
//    varHandleX.set(p, 2.3);
//    System.out.println(p);
//    Class pClasss = MethodHandles.lookup().findClass(p.getClass().getCanonicalName());
//    VarHandle varHandleY = MethodHandles.privateLookupIn(pClasss, MethodHandles.lookup())
//                                        .findVarHandle(pClasss, "y", double.class);
//    Object o = pClasss.getDeclaredConstructor().newInstance();
//    System.out.println(o);
//    System.out.println(varHandleY.get(o));
//    HashMap<Object, Object> map = Maps.newHashMap(new BeanMap(p));
//
//    System.out.println(map);
//
//  }
//}
