3
https://raw.githubusercontent.com/jroutine/jroutine/master/src/test/java/org/coral/jroutine/weave/rewrite/Loop.java
package org.coral.jroutine.weave.rewrite;

public class Loop implements Runnable {

    @Override
    public void run() {
        test();
        String s = new String();
        Object o = new Object();
        System.out.println(s.equals(o));
    }

    private static void test() {
        // TODO Auto-generated method stub
        
    }
}
