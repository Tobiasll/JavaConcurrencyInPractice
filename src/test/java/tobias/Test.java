package tobias;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Test {

  private static ThreadGroup rootThreadGroup = null;


  @org.junit.Test
  public void test() {
    char[] s = "(1 + ((2 + 3) * (4 * 5)))".toCharArray();
    Stack<Character> characters = new Stack<>();
    Stack<Double> values = new Stack<>();
    for (char ch : s) {
      if (ch == ' ' | ch == '(') {
        continue;
      }
      if (ch == '+') {
        characters.push(ch);
      } else if (ch == '*') {
        characters.push(ch);
      } else if (ch == ')') {
        double num1 = values.pop();
        double num2 = values.pop();
        Character c = characters.pop();
        if (c == '+') {
          values.push(num1 + num2);
        } else if (c == '*') {
          values.push(num1 * num2);
        }
      } else {
        values.push(Double.parseDouble(ch + ""));
      }
    }

  }


  @org.junit.Test
  public void test2() {
    ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
    int threadCount = tmx.getThreadCount();
    long[] allThreadIds = tmx.getAllThreadIds();
    ThreadInfo[] threadInfo = tmx.getThreadInfo(allThreadIds);
    for (ThreadInfo info : threadInfo) {
      String threadName = info.getThreadName();
      System.out.println(threadName);
    }
    ThreadInfo info = tmx.getThreadInfo(29660);
    System.out.println(info);
  }

  @org.junit.Test
  public void test3() {
    System.out.println(1 << 16);
  }

  @org.junit.Test
  public void test4() throws Exception {
    Map<String, String> map = new HashMap<>(33);
    map.put("dsd", "dsd");
    Class[] classes = map.getClass().getDeclaredClasses();
    Class nodeClass = null;
    for (Class aClass : classes) {
      if (aClass.getName().equals("java.util.HashMap$Node")) {
        nodeClass = aClass;
      }
    }
    System.out.println(nodeClass);
    assert nodeClass != null;
//    VarHandle table = MethodHandles.privateLookupIn(map.getClass(), MethodHandles.lookup())
//                                   .findVarHandle(map.getClass(), "table", nodeClass);

  }

  @org.junit.Test
  public void test5() {
    System.out.println(System.nanoTime());
    System.out.println(System.currentTimeMillis());
    System.out.println(System.nanoTime() == System.currentTimeMillis());
  }

}
