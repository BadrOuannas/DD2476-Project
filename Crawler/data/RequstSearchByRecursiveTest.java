160
https://raw.githubusercontent.com/c0ny1/java-object-searcher/master/src/test/java/me/gv7/tools/josearcher/searcher/RequstSearchByRecursiveTest.java
package me.gv7.tools.josearcher.searcher;

import java.util.List;
import java.util.ArrayList;
import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.test.TestClass;


class RequstSearchByRecursiveTest {
    public static void main(String[] args)  {
        //Thread thread = Thread.currentThread();
        TestClass target =  new TestClass();
        List<Keyword> keys = new ArrayList<>();
        keys.add(new Keyword.Builder().setField_type("entity").build());

        List<Blacklist> blacklists = new ArrayList<>();
        blacklists.add(new Blacklist.Builder().setField_name("parallelLockMap").build());

        SearchRequstByRecursive1 searcher = new SearchRequstByRecursive1(target,keys);
        searcher.setIs_debug(true);
        //searcher.setMax_search_depth(1000);
        //searcher.setBlacklists(blacklists);
        //searcher.setReport_save_path("/Users/c0ny1/Downloads/ResponseGrab/out/");
        searcher.searchObject();
    }
}