160
https://raw.githubusercontent.com/c0ny1/java-object-searcher/master/src/test/java/me/gv7/tools/josearcher/searcher/SearchRequstByBFSTest.java
package me.gv7.tools.josearcher.searcher;

import java.util.List;
import java.util.ArrayList;

import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.test.TestClass;


class SearchRequstByBFSTest {
    public static void main(String[] args) {
        //Thread thread = Thread.currentThread();
        TestClass target =  new TestClass();
        List<Keyword> keys = new ArrayList<>();
        keys.add(new Keyword.Builder().setField_type("entity").build());
        List<Blacklist> blacklists = new ArrayList<>();
        blacklists.add(new Blacklist.Builder().setField_name("parallelLockMap").build());
        SearchRequstByBFS1 searcher = new SearchRequstByBFS1(target,keys);
        //searcher.setMax_search_depth(1000);
        searcher.setIs_debug(true);
        //searcher.setBlacklists(blacklists);
        searcher.setReport_save_path("/Users/c0ny1/Downloads/ResponseGrab/out/");
        searcher.searchObject();
    }
}