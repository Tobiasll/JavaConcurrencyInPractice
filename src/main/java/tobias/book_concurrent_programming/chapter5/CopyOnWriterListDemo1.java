package tobias.book_concurrent_programming.chapter5;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriterListDemo1 {

  private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

  public static void main(String[] args) throws InterruptedException {
    list.add("a");
    list.add("b");
    list.add("c");
    list.add("d");
    list.add("e");
    list.add("f");
    list.add("g");

    Iterator<String> iterator = list.iterator();

    Thread t = new Thread(() -> {
      list.set(1, "bb");
      list.remove(2);
      list.remove(3);
      list.remove(4);
    });

    t.start();
    t.join();

    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }

    System.out.println(list);
  }

}
