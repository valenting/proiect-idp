package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	public GroupTree(TreeModel model, Mediator m) {
		super(model);
		this.m = m;
		this.setToggleClickCount(1);
		this.add(new JPopupMenu());
		
		
		
		CustomTreeCellRenderer r = new CustomTreeCellRenderer();
		this.setCellRenderer(r);
		r.setRendererIcon(new ImageIcon("src/gui/images/member.gif"));
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

    ImageIcon rendererIcon;


    public void setRendererIcon(ImageIcon myIcon){
         this.rendererIcon = myIcon;
    };


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
        		if (c.getUserObject().equals("user")) {
        			label.setIcon( rendererIcon );
        		}
        	}

        return ret;
    }
}


