package ua.shark.dbms.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.EOFException;
import java.io.IOException;

import ua.shark.dbms.entities.DBManager;
import ua.shark.dbms.entities.Database;
import static ua.shark.dbms.Main.*;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 6992541981639826538L;
	private JPanel contentPane;
	private JTextField txtName;
	private JList<String> list;
	private DefaultListModel<String> model;

	public MainForm() {
		setTitle("DBMS");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 520, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDbms = new JLabel("DBMS");
		lblDbms.setHorizontalAlignment(SwingConstants.CENTER);
		lblDbms.setFont(new Font("Tahoma", Font.PLAIN, 46));
		lblDbms.setBounds(0, 11, 514, 54);
		contentPane.add(lblDbms);
		
		JLabel lblCreateDb = new JLabel("Create DB");
		lblCreateDb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCreateDb.setBounds(55, 111, 100, 14);
		contentPane.add(lblCreateDb);
		
		JLabel lblNewLabel = new JLabel("DB List");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(55, 150, 85, 14);
		contentPane.add(lblNewLabel);
		
		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBounds(175, 109, 163, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		final JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(231, 94, 46, 14);
		contentPane.add(lblName);
		
		model = new DefaultListModel<String>();
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText() != null && !txtName.getText().isEmpty() && !txtName.getText().trim().equals("")) {
					for (int i = 0; i < model.getSize(); ++i) {
						if (model.get(i).equals(txtName.getText())) {
							txtName.setText("");
							txtName.requestFocus();
							return;
						}
					}
					manager.setCurDB(new Database(txtName.getText()));
					dispose();
					new TablesForm();
				}
				txtName.setText("");
				txtName.requestFocus();
			}
		});
		btnCreate.setBounds(369, 107, 89, 23);
		contentPane.add(btnCreate);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					manager.loadDB(list.getSelectedValue());
					dispose();
					new TablesForm();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "This file is corrupted!",
							"File corrupted!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLoad.setBounds(369, 150, 89, 23);
		contentPane.add(btnLoad);
		
		JLabel lblMasVitaliy = new JLabel("Mas Vitaliy,\r\n2014 (c)");
		lblMasVitaliy.setBounds(388, 344, 120, 20);
		contentPane.add(lblMasVitaliy);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(175, 151, 163, 179);
		contentPane.add(scrollPane);
		list = new JList<String>(model);
		for (String s: DBManager.getDBList()) {
			model.addElement(s);
		}
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.deleteDB(list.getSelectedValue());
				model.remove(list.getSelectedIndex());
				list.setModel(model);
				list.setSelectedIndex(0);
			}
		});
		btnDelete.setBounds(369, 184, 89, 23);
		contentPane.add(btnDelete);
		setVisible(true);
	}
}
