140
https://raw.githubusercontent.com/GraxCode/threadtear/master/src/me/nov/threadtear/swing/tree/ExecutionListPanel.java
package me.nov.threadtear.swing.tree;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.*;

import com.github.weisj.darklaf.icons.IconLoader;

import me.nov.threadtear.execution.Execution;
import me.nov.threadtear.swing.Utils;
import me.nov.threadtear.swing.dialog.ExecutionSelection;
import me.nov.threadtear.swing.tree.component.ExecutionTreeNode;
import me.nov.threadtear.swing.tree.renderer.ExecutionTreeCellRenderer;

public class ExecutionListPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  public DefaultTreeModel model;
  private ExecutionTree executions;
  private JButton remove;
  private JButton up;
  private JButton down;

  public ExecutionListPanel() {
    this.setLayout(new BorderLayout());
    this.add(Utils.addTitleAndBorder("Executions in order (top to bottom)", new JScrollPane(executions = new ExecutionTree())), BorderLayout.CENTER);

    this.add(createButtons(), BorderLayout.SOUTH);
  }

  @Override
  public Dimension getMinimumSize() {
    Dimension minSize = super.getMinimumSize();
    minSize.width = 150;
    return minSize;
  }

  private JPanel createButtons() {
    JPanel panel = new JPanel(new GridLayout(1, 4, 4, 4));
    panel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
    JButton add = new JButton("Add", IconLoader.get().loadSVGIcon("res/add.svg", false));
    add.addActionListener(e -> {
      ExecutionSelection es = new ExecutionSelection(ExecutionListPanel.this);
      es.setVisible(true);
      ExecutionTreeNode root = ((ExecutionTreeNode) model.getRoot());
      if (es.tree.getSelectionPath() == null) {
        ExecutionTreeNode node = (ExecutionTreeNode) es.tree.getLastSelectedPathComponent();
        if (node != null && node.member != null) {
          root.add(new ExecutionTreeNode(node.member, true));
        }
      } else {
        for (TreePath path : es.tree.getSelectionPaths()) {
          ExecutionTreeNode node = (ExecutionTreeNode) path.getLastPathComponent();
          if (node != null && node.member != null) {
            root.add(new ExecutionTreeNode(node.member, true));
          }
        }
      }
      model.reload();
      executions.repaint();
    });
    panel.add(add);
    remove = new JButton("Remove", IconLoader.get().loadSVGIcon("res/remove.svg", false));
    remove.addActionListener(e -> {
      ExecutionTreeNode node = (ExecutionTreeNode) executions.getLastSelectedPathComponent();
      if (node != null && node.member != null) {
        model.removeNodeFromParent(node);
        model.reload();
        executions.repaint();
      }
    });
    panel.add(remove);
    remove.setEnabled(false);
    up = new JButton("Move up", IconLoader.get().loadSVGIcon("res/move_up.svg", false));
    up.addActionListener(e -> {
      Utils.moveTreeItem(executions, -1);
      executions.grabFocus();
    });
    panel.add(up);
    up.setEnabled(false);
    down = new JButton("Move down", IconLoader.get().loadSVGIcon("res/move_down.svg", false));
    down.addActionListener(e -> {
      Utils.moveTreeItem(executions, 1);
      executions.grabFocus();
    });
    panel.add(down);
    down.setEnabled(false);
    return panel;
  }

  public class ExecutionTree extends JTreeWithHint {
    private static final long serialVersionUID = 1L;

    public ExecutionTree() {
      super("Select your executions here");
      this.setRootVisible(false);
      this.setShowsRootHandles(true);
      this.setFocusable(true);
      this.setCellRenderer(new ExecutionTreeCellRenderer());
      ExecutionTreeNode root = new ExecutionTreeNode("");
      model = new DefaultTreeModel(root);
      this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      this.setModel(model);
      ToolTipManager.sharedInstance().registerComponent(this);
      this.addTreeSelectionListener(l -> {
        ExecutionTreeNode tn = (ExecutionTreeNode) getLastSelectedPathComponent();
        boolean selected = tn != null;
        remove.setEnabled(selected);
        up.setEnabled(selected && root.getChildCount() > 1);
        down.setEnabled(selected && root.getChildCount() > 1);
      });
    }
  }

  public ArrayList<Execution> getExecutions() {
    ArrayList<Execution> list = new ArrayList<>();
    ExecutionTreeNode root = (ExecutionTreeNode) model.getRoot();
    for (int i = 0; i < root.getChildCount(); i++) {
      ExecutionTreeNode child = (ExecutionTreeNode) root.getChildAt(i);
      assert (child.member != null);
      list.add(child.member);
    }
    return list;
  }
}
