package tobias.part_one.chapter3;

public class Visibility {

  private static boolean flag;
  private static Integer number = 0;

  private static class WhileThread extends Thread {

    @Override
    public void run() {
      while (!flag) {
        Thread.yield();
        System.out.println("执行while任务");
      }
      System.out.println(number);
    }
  }

  public static void main(String[] args) {

    new WhileThread().start();

    /*new Thread(() -> {
      try {
        Thread.sleep(1000);
        flag = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }).start();*/

    flag = true;
    number = 42;
  }

}
