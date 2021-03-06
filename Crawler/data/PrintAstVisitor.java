3
https://raw.githubusercontent.com/valentjn/ltex-ls-old/master/src/test/java/org/bsplines/languagetool_languageserver/markdown/PrintAstVisitor.java
package org.bsplines.languagetool_languageserver.markdown;

import com.vladsch.flexmark.util.ast.Node;

class PrintAstVisitor {
    public void visit(Node node, int indent) {
        String i = "";

        for (int j = 0; j < indent; j++) {
            i += "  ";
        }

        System.out.println(i + node.toAstString(true));

        node.getChildIterator().forEachRemaining(n -> this.visit(n, indent + 1));
    }
}
