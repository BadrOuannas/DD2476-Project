140
https://raw.githubusercontent.com/GraxCode/threadtear/master/src/me/nov/threadtear/swing/dialog/ExecutionSelection.java
package me.nov.threadtear.swing.dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.tree.*;

import me.nov.threadtear.execution.Execution;
import me.nov.threadtear.execution.allatori.*;
import me.nov.threadtear.execution.analysis.*;
import me.nov.threadtear.execution.cleanup.*;
import me.nov.threadtear.execution.cleanup.remove.RemoveUnnecessary;
import me.nov.threadtear.execution.dasho.StringObfuscationDashO;
import me.nov.threadtear.execution.generic.*;
import me.nov.threadtear.execution.stringer.*;
import me.nov.threadtear.execution.tools.*;
import me.nov.threadtear.execution.zkm.*;
import me.nov.threadtear.swing.tree.component.ExecutionTreeNode;
import me.nov.threadtear.swing.tree.renderer.ExecutionTreeCellRenderer;

public class ExecutionSelection extends JDialog {
  private static final long serialVersionUID = 1L;
  public JTree tree = null;
  private JButton ok;

  public ExecutionSelection(Component parent) {
    setLocationRelativeTo(parent);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setTitle("Select one or more executions");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(450, 300);
    setMinimumSize(new Dimension(450, 300));
    getContentPane().setLayout(new BorderLayout());
    JPanel cp = new JPanel();
    cp.setBorder(new EmptyBorder(10, 10, 10, 10));
    getContentPane().add(cp, BorderLayout.CENTER);
    cp.setLayout(new BorderLayout(0, 0));
    tree = new ExecutionSelectionTree();
    JPanel treePanel = new JPanel(new BorderLayout());
    treePanel.setBorder(BorderFactory.createLoweredBevelBorder());
    treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);
    cp.add(treePanel);
    JPanel buttons = new JPanel();
    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(e -> {
      tree.clearSelection();
      dispose();
    });
    buttons.add(cancel);
    buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(buttons, BorderLayout.SOUTH);
    ok = new JButton("OK");
    ok.setEnabled(false);
    ok.addActionListener(e -> {
      dispose();
    });
    ok.setActionCommand("OK");
    buttons.add(ok);
    getRootPane().setDefaultButton(ok);
    cancel.setActionCommand("Cancel");

  }

  public class ExecutionSelectionTree extends JTree implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;

    public ExecutionSelectionTree() {
      this.setRootVisible(false);
      this.setShowsRootHandles(true);
      this.setFocusable(true);
      this.setCellRenderer(new ExecutionTreeCellRenderer());
      ExecutionTreeNode root = new ExecutionTreeNode("");
      DefaultTreeModel model = new DefaultTreeModel(root);
      this.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
      addExecution(root, new InlineMethods());
      addExecution(root, new InlineUnchangedFields());
      addExecution(root, new RemoveUnnecessary());
      addExecution(root, new RemoveAttributes());

      addExecution(root, new ObfuscatedAccess());
      addExecution(root, new KnownConditionalJumps());
      addExecution(root, new ConvertCompareInstructions());

      addExecution(root, new RestoreSourceFiles());
      addExecution(root, new ReobfuscateClassNames());
      addExecution(root, new ReobfuscateMembers());
      addExecution(root, new RemoveMonitors());
      addExecution(root, new RemoveTCBs());

      addExecution(root, new StringObfuscationStringer());
      addExecution(root, new AccessObfusationStringer());

      addExecution(root, new TryCatchObfuscationRemover());
      addExecution(root, new StringObfuscationZKM());
      addExecution(root, new AccessObfusationZKM());

      addExecution(root, new StringObfuscationAllatori());
      addExecution(root, new ExpirationDateRemoverAllatori());

      addExecution(root, new StringObfuscationDashO());

      addExecution(root, new Java7Compatibility());
      addExecution(root, new Java8Compatibility());
      addExecution(root, new IsolatePossiblyMalicious());
      addExecution(root, new AddLineNumbers());
      addExecution(root, new LogAllExceptions());
      addExecution(root, new RemoveMaxs());

      this.setModel(model);
      ToolTipManager.sharedInstance().registerComponent(this);
      this.addTreeSelectionListener(this);
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            ExecutionTreeNode tn = (ExecutionTreeNode) getLastSelectedPathComponent();
            if (tn != null && tn.member != null)
              ExecutionSelection.this.dispose();
          }
        }
      });
      this.addTreeSelectionListener(e -> {
        TreePath[] paths = e.getPaths();
        for (int i = 0; i < paths.length; i++) {
          DefaultMutableTreeNode tn = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
          if (tn.getChildCount() > 0)
            removeSelectionPath(paths[i]);
        }
      });
    }

    private void addExecution(ExecutionTreeNode root, Execution e) {
      String[] split = (e.type.name + "." + e.name).split("\\.");
      addToTree(root, e, split, 0);
    }

    public void addToTree(ExecutionTreeNode current, Execution e, String[] subfolders, int folder) {
      String node = subfolders[folder];
      if (subfolders.length - folder <= 1) {
        current.add(new ExecutionTreeNode(e, false));
        return;
      }
      for (int i = 0; i < current.getChildCount(); i++) {
        ExecutionTreeNode child = (ExecutionTreeNode) current.getChildAt(i);
        if (child.toString().equals(node)) {
          addToTree(child, e, subfolders, ++folder);
          return;
        }
      }
      ExecutionTreeNode newChild = new ExecutionTreeNode(node);
      current.add(newChild);
      addToTree(newChild, e, subfolders, ++folder);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
      ExecutionTreeNode node = (ExecutionTreeNode) tree.getLastSelectedPathComponent();
      ok.setEnabled(node != null && node.member != null);
    }
  }
}
