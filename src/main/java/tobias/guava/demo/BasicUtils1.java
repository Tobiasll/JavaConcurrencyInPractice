package tobias.guava.demo;

import com.google.common.base.Objects;

public class BasicUtils1 {

  public static void main(String[] args) {
    int code = Objects.hashCode("sda", 1);
    System.out.println(code + Objects.hashCode(1));
    boolean equal = Objects.equal(1, 2);
    System.out.println(equal);

  }

}
