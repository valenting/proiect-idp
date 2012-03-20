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
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class GroupTree extends JTree {
	DefaultMutableTreeNode root;
	private JPopupMenu menu = new JPopupMenu("Popup");
	public GroupTree() {
		super(new DefaultMutableTreeNode("Groups"));
		root = (DefaultMutableTreeNode) this.getModel().getRoot();
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
	
	public void addGroup(String groupName) {
		root.add(new DefaultMutableTreeNode(groupName, true));
		setModel(new DefaultTreeModel(root));
	}
	
	public void addUser(String group, String user) {
		for (Enumeration<DefaultMutableTreeNode> e = root.children(); e.hasMoreElements();) {
			DefaultMutableTreeNode t = e.nextElement();
			if (t.getUserObject().equals(group)) {
				t.add(new DefaultMutableTreeNode(user, false));
				setModel(new DefaultTreeModel(root));
				return;
			}
		}
	}
}
