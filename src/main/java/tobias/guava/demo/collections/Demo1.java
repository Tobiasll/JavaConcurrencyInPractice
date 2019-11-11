package tobias.guava.demo.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanMap;


public class Demo1 {

  public static void main(String[] args) {
    Map<String, List<Integer>> map = Maps.newHashMapWithExpectedSize(3);
    Multimap<String, Integer> mapList = ArrayListMultimap.create();
    mapList.put("a", 1);
    System.out.println(mapList);
    mapList.put("a", 1);
    System.out.println(mapList);

    Multiset<String> multiset = HashMultiset.create();
    multiset.add("aa");
    System.out.println(multiset);
    multiset.add("aa");
    System.out.println(multiset);
    System.out.println(multiset.count("aa"));

    BiMap<String, Integer> biMap = HashBiMap.create();
    biMap.put("a", 1);
    System.out.println(biMap);
    System.out.println(biMap.get("a"));
    System.out.println(biMap.inverse());

    User user = new User("test", 1);
    System.out.println(user);
    BeanMap beanMap = new BeanMap(user);
    beanMap.put("test", 1);
    System.out.println(beanMap);

    System.out.println(beanMap);

  }
}
