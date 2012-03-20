package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class GroupTree extends JTree {
	DefaultMutableTreeNode root;
	private JPopupMenu menu = new JPopupMenu("Popup");
	public GroupTree(TreeModel model) {
		super(model);
		this.setToggleClickCount(1);
		this.add(new JPopupMenu());
		
		this.addMouseListener(new MouseAdapter() {
	           public void mouseReleased(MouseEvent e) {
	                if (e.getButton()==MouseEvent.BUTTON3) {
	                    TreePath tp = getClosestPathForLocation(e.getX(),e.getY());
	                    if (tp != null) {
	                        System.out.println(tp);
	                        setSelectionPath(tp);
	                    }
	                    menu.show(e.getComponent(), e.getX(), e.getY());
	                }
	            }
	        });
		
		String letters = "ABCDEF";

		for (final char letter : letters.toCharArray()) {
            JMenuItem item = new JMenuItem(String.valueOf(letter));
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(GroupTree.this, "abc" );
                }
            });
            menu.add(item);
        }

	}
}
