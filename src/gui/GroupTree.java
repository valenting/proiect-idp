package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class GroupTree extends JTree implements ActionListener {
	private static final long serialVersionUID = 1L;
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
					
					// TODO : eliminare setVisible(false)
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		JMenuItem item = new JMenuItem("Add User");
		item.addActionListener(this);
		menu.add(item);
		
		item = new JMenuItem("Join Group");
		item.addActionListener(this);
		menu.add(item);
		
		item = new JMenuItem("Leave Group");
		item.addActionListener(this);
		menu.add(item);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO
		
	}
}
