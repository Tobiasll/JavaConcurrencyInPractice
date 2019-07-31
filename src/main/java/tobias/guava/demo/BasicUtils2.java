package tobias.guava.demo;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BasicUtils2 {

  public static void main(String[] args) {
    List<Integer> list  = ImmutableList.of(1, 2, 3, 4);
    Ordering ordering = new Ordering<Integer>() {
      @Override
      public int compare(@Nullable Integer left, @Nullable Integer right) {
        return right - left;
//        return left - right;
      }
    };
    System.out.println(ordering.isOrdered(list));
    System.out.println(ordering.max(list));
    System.out.println(ordering.sortedCopy(list));
    System.out.println(ordering.leastOf(list, 3));
  }

}
