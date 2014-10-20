package ua.shark.dbms.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.util.LinkedHashMap;

import static ua.shark.dbms.Main.*;
import ua.shark.dbms.entities.Attribute;
import ua.shark.dbms.entities.Table;
import ua.shark.dbms.types.*;

public class AddTableForm extends JDialog implements ActionListener{
	private LinkedHashMap<String, Class> classTypes;
	private final JPanel contentPanel = new JPanel();
	private JTextField textTableName;
	private JLabel lblTableName;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textAttributeName;
	private JLabel lblName;
	private JComboBox comboAttributeType;
	private JLabel lblType;
	private JButton btnAddAttribute;
	private JButton btnDelAttribute;
	private JButton btnCreate;

	public AddTableForm() {
		classTypes = new LinkedHashMap<String, Class>();
		classTypes.put("Integer", Integer.class);
		classTypes.put("Real", Double.class);
		classTypes.put("Long", Long.class);
		classTypes.put("String", String.class);
		classTypes.put("RealInterval", RealInterval.class);
		classTypes.put("Image", String.class);
		classTypes.put("HTML", String.class);
		
		model = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		        	return false;
		        }
		};
		model.setDataVector(new Object[0][0], new Object[]{"Name", "Type"});
		init();
	}
	
	public void init() {
		setTitle("New Table");
		setResizable(false);
		setModal(true);
		setBounds(350, 250, 500, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		textTableName = new JTextField();
		textTableName.setBounds(97, 11, 255, 20);
		contentPanel.add(textTableName);
		textTableName.setColumns(10);
		
		lblTableName = new JLabel("Table Name:");
		lblTableName.setBounds(20, 14, 77, 14);
		contentPanel.add(lblTableName);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 39, 332, 222);
		contentPanel.add(scrollPane);

		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		textAttributeName = new JTextField();
		textAttributeName.setHorizontalAlignment(SwingConstants.CENTER);
		textAttributeName.setBounds(362, 39, 122, 20);
		contentPanel.add(textAttributeName);
		textAttributeName.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setBounds(408, 24, 46, 14);
		contentPanel.add(lblName);
		
		comboAttributeType = new JComboBox<String>(new DefaultComboBoxModel<String>(classTypes.keySet()
				.toArray(new String[classTypes.size()])));
		comboAttributeType.setBounds(362, 81, 122, 20);
		contentPanel.add(comboAttributeType);
		
		lblType = new JLabel("Type");
		lblType.setBounds(410, 64, 46, 14);
		contentPanel.add(lblType);
		
		btnAddAttribute = new JButton("Add");
		btnAddAttribute.setBounds(374, 116, 97, 23);
		btnAddAttribute.addActionListener(this);
		contentPanel.add(btnAddAttribute);
		
		btnDelAttribute = new JButton("Delete");
		btnDelAttribute.setBounds(374, 148, 97, 23);
		btnDelAttribute.addActionListener(this);
		contentPanel.add(btnDelAttribute);
		
		btnCreate = new JButton("Create");
		btnCreate.setBounds(374, 238, 97, 23);
		btnCreate.addActionListener(this);
		contentPanel.add(btnCreate);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == btnAddAttribute) {
			if (!textAttributeName.getText().trim().equals("")) {
				for (int i = 0; i < model.getRowCount(); ++i) {
					if (model.getValueAt(i, 0).equals(textAttributeName.getText().trim()))
						return;
				}
				model.addRow(new Object[]{textAttributeName.getText().trim(), comboAttributeType.getSelectedItem().toString()});
				textAttributeName.setText("");
			}
		} else if (src == btnDelAttribute) {
			if (table.getSelectedRow() != -1)
				model.removeRow(table.getSelectedRow());
		} else if (src == btnCreate) {
			if (model.getRowCount() == 0 || textTableName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Insert name and some attributes!",
						"No attributes or name!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (Table t : manager.getCurDB().getTableList())
				if (t.getName().equals(textTableName.getText().trim())) {
					JOptionPane.showMessageDialog(null, "Table with such name is already exists!",
							"Duplicate name!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			
			Table res = new Table(textTableName.getText().trim());
			for (int i = 0; i < table.getRowCount(); ++i) {
				Attribute atr = new Attribute((String) table.getValueAt(i, 0), classTypes.get(table.getValueAt(i, 1)));
				res.addAttribute(atr);
			}
			manager.getCurDB().addTable(res);
			dispose();
		}
	}
}
