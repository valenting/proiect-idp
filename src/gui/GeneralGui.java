package gui;

import gui.buttons.CreateGroup;
import gui.buttons.LogOutButton;
import app.Mediator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;


public class GeneralGui extends JPanel {
	private static final long serialVersionUID = 1L;

	public JLabel lblUsername;
	JList list;
	GroupTree tree;
	Mediator med;
	JTabbedPane tabbedPane;
	Gui gg;
	public GeneralGui(Gui g, Mediator m) {
		gg = g;
		m.setGeneralGui(this);
		med = m;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.15, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.05, 1.0, 5.0, Double.MIN_VALUE};
		this.setLayout(gbl_contentPane);


		// User List
		list = new JList(m.getListModel());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 2;
		gbc_list.insets = new Insets(0, 0, 30, 30);
		gbc_list.anchor = GridBagConstraints.CENTER;
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		JScrollPane p = new JScrollPane(list);
		p.setPreferredSize(new Dimension(0, 0));
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(p, gbc_list);

		
		// Create Group & Log out button
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		this.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		// Log Out button
		JLabel lblUser = new JLabel("Connected User: ");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 4;
		gbc_lblUser.gridy = 0;
		panel.add(lblUser, gbc_lblUser);

		lblUsername = new JLabel("username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 5;
		gbc_lblUsername.gridy = 0;
		panel.add(lblUsername, gbc_lblUsername);

		JButton btnLogout = new LogOutButton(g, m);
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.ipadx = 5;
		gbc_btnLogout.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.gridx = 6;
		gbc_btnLogout.gridy = 0;
		panel.add(btnLogout, gbc_btnLogout);


		// Create Group Button
		JButton btnCreateGroup = new CreateGroup(g, m);

		GridBagConstraints gbc_btnCreateGroup = new GridBagConstraints();
		gbc_btnCreateGroup.anchor = GridBagConstraints.WEST;
		gbc_btnCreateGroup.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateGroup.gridx = 6;
		gbc_btnCreateGroup.gridy = 2;
		panel.add(btnCreateGroup, gbc_btnCreateGroup);


		// Group list
		tree = new GroupTree(m.getTreeModel(),m);
		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.insets = new Insets(0, 0, 0, 5);
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridx = 0;
		gbc_tree.gridy = 2;
		p = new JScrollPane(tree);
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(p, gbc_tree);
		//m.registerGroupTree(tree);

		// TabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 1;
		this.add(tabbedPane, gbc_tabbedPane);

		//TODO - remove them
		//tabbedPane.addTab("tab1", new GroupTab(g,m) );
		//tabbedPane.addTab("tab2", new GroupTab(g,m) );
	}
	
	public GroupTab addTab(String group, DefaultListModel l) {
		GroupTab tb = new GroupTab(gg,med);
		tb.setLegendModel(l);
		tabbedPane.addTab(group, tb);
		return tb;
	}
	 
	public void logOut() {
		tabbedPane.removeAll();
	}
	
	public String getSelectedUser() {
		return (String) list.getSelectedValue();
	}
	
	public DefaultMutableTreeNode getSelectedGroup() {
		return (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
	}
	
	public void setUser(String user) {
		tree.setCurrentUser(user);
	}
}
