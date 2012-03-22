package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import app.Mediator;

public class GroupTree extends JTree implements ActionListener {
	private static final long serialVersionUID = 1L;
	DefaultMutableTreeNode root;
	private JPopupMenu menu = new JPopupMenu("Popup");
	Mediator m;
	CustomTreeCellRenderer r;
	String user;
	public GroupTree(TreeModel model, final Mediator m) {
		super(model);
		this.m = m;
		this.setToggleClickCount(1);
		this.add(new JPopupMenu());
		
		
		
		r = new CustomTreeCellRenderer();
		this.setCellRenderer(r);
		r.setRendererIcon(new ImageIcon("src/gui/images/member.gif"));
		
		final JMenuItem item_add = new JMenuItem("Add User");
		item_add.addActionListener(this);
		menu.add(item_add);
		
		final JMenuItem item_join = new JMenuItem("Join Group");
		item_join.addActionListener(this);
		menu.add(item_join);
		
		final JMenuItem item_leave = new JMenuItem("Leave Group");
		item_leave.addActionListener(this);
		menu.add(item_leave);
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				item_add.setVisible(true);
				item_join.setVisible(true);
				item_leave.setVisible(true);
			}
			public void mouseReleased(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON3) {
					TreePath tp = getClosestPathForLocation(e.getX(),e.getY());
					if (tp != null) {
						System.out.println(tp);
						setSelectionPath(tp);
					}
					DefaultMutableTreeNode t = (DefaultMutableTreeNode) tp.getLastPathComponent();
					if (t.isRoot() || t.isLeaf()) {
						item_add.setVisible(false);
						item_join.setVisible(false);
						item_leave.setVisible(false);
					} else {
						if (m.userInGroup(user, (String) t.getUserObject())) {
							item_join.setVisible(false);
						} else
							item_leave.setVisible(false);
					}
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		
	}
	
	public 	void setCurrentUser(String user) {
		r.setUser(user);
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		if (arg0.getActionCommand().equals("Add User"))
			m.addUserCommand();
		if (arg0.getActionCommand().equals("Join Group"))
			m.joinGroupCommand();
		if (arg0.getActionCommand().equals("Leave Group"))
			m.leaveGroupCommand();
	} 
} 

class CustomTreeCellRenderer extends DefaultTreeCellRenderer{

	private static final long serialVersionUID = 1L;
	ImageIcon rendererIcon;
    String username;
    
    public void setRendererIcon(ImageIcon myIcon){
         this.rendererIcon = myIcon;
    };

    public void setUser(String user) {
    	username = user;
    }

    public Component getTreeCellRendererComponent(JTree tree,
        Object value, boolean selected, boolean expanded,
        boolean leaf, int row, boolean hasFocus){

        Component ret = super.getTreeCellRendererComponent(tree, value,
        selected, expanded, leaf, row, hasFocus);

        JLabel label = (JLabel) ret ;
        System.out.println(""+value.getClass().getName());
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) value;
        if (n.getParent()!=null) 
        	for (int i=0;i<n.getChildCount();i++) {
        		DefaultMutableTreeNode c = (DefaultMutableTreeNode) n.getChildAt(i);
        		if (c.getUserObject().equals(username)) {
        			label.setIcon( rendererIcon );
        		}
        	}

        return ret;
    }
}


