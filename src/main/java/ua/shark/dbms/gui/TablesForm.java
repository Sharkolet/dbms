package ua.shark.dbms.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.io.IOException;
import java.util.ArrayList;

import ua.shark.dbms.entities.Record;
import ua.shark.dbms.entities.Table;
import static ua.shark.dbms.Main.*;

import javax.swing.SwingConstants;

public class TablesForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = -3141841820911031573L;
	private JPanel contentPane;
	private JTable table;
	private JComboBox<String> tablesBox;
	private JLabel tableLbl;
	private JButton btnDeleteTable;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private JButton btnAddRecord;
	private JButton btnConfirmChanges;
	private JButton btnDeleteRecord;
	private JLabel lblRecords;
	private JButton btnAddTable;
	private JLabel lblTables;
	private JButton btnCancelChanges;
	JButton btnOperations;
	private JLabel lblChanges;
	
	private JMenuBar menuBar;
	private JMenuItem mntmOpenDatabase;
	private JMenuItem mntmSave;
	private JMenuItem mntmExit;
	private JMenu mnFile;
	
	public TablesForm() {
		//load initial tableComboBox
		table = new JTable();
		tableModel = new DefaultTableModel();
		tablesBox = new JComboBox();
		refreshData();
		init();
	}
	
	public void init() {
		setTitle(manager.getCurDB().getName());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 740, 400);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpenDatabase = new JMenuItem("Change Database");
		mnFile.add(mntmOpenDatabase);
		mntmOpenDatabase.addActionListener(this);
		
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(this);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tablesBox.setBounds(118, 14, 379, 20);
		tablesBox.addActionListener(this);
		contentPane.add(tablesBox);
		
		tableLbl = new JLabel("Table");
		tableLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableLbl.setBounds(41, 15, 46, 14);
		contentPane.add(tableLbl);
		
		btnDeleteTable = new JButton("Delete");
		btnDeleteTable.addActionListener(this);
		btnDeleteTable.setIcon(null);
		btnDeleteTable.setBounds(627, 80, 97, 23);
		contentPane.add(btnDeleteTable);
		
		table = new JTable(tableModel);
		//table.setRowSelectionAllowed(true);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 607, 300);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		btnAddRecord = new JButton("Add");
		btnAddRecord.setBounds(627, 163, 97, 23);
		btnAddRecord.addActionListener(this);
		contentPane.add(btnAddRecord);
		
		btnConfirmChanges = new JButton("Confirm");
		btnConfirmChanges.setBounds(627, 283, 97, 23);
		btnConfirmChanges.addActionListener(this);
		contentPane.add(btnConfirmChanges);
		
		btnDeleteRecord = new JButton("Delete");
		btnDeleteRecord.setBounds(627, 197, 97, 23);
		btnDeleteRecord.addActionListener(this);
		contentPane.add(btnDeleteRecord);
		
		lblRecords = new JLabel("Records:");
		lblRecords.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecords.setBounds(650, 143, 59, 14);
		contentPane.add(lblRecords);
		
		btnAddTable = new JButton("Add");
		btnAddTable.setBounds(627, 46, 97, 23);
		btnAddTable.addActionListener(this);
		contentPane.add(btnAddTable);
		
		lblTables = new JLabel("Tables:");
		lblTables.setHorizontalAlignment(SwingConstants.LEFT);
		lblTables.setBounds(656, 27, 56, 14);
		contentPane.add(lblTables);
		
		btnCancelChanges = new JButton("Cancel");
		btnCancelChanges.setBounds(627, 317, 97, 23);
		btnCancelChanges.addActionListener(this);
		contentPane.add(btnCancelChanges);
		
		lblChanges = new JLabel("Changes:");
		lblChanges.setHorizontalAlignment(SwingConstants.LEFT);
		lblChanges.setBounds(650, 264, 59, 14);
		contentPane.add(lblChanges);
		
		btnOperations = new JButton("Operations");
		btnOperations.setBounds(507, 13, 110, 23);
		btnOperations.addActionListener(this);
		contentPane.add(btnOperations);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == mntmOpenDatabase) {
			/*SaveDialogBox dialog = new SaveDialogBox();
			if (!dialog.cancelClicked)
				dispose();*/
			dispose();
			new MainForm();
		} else if (src == mntmSave) {
			try {
				btnConfirmChanges.doClick();
				manager.saveDB();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (src == mntmExit) {
			System.exit(0);
		} else if (src == tablesBox) { 
			String[] columnNames = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getColumnNames();
			Object[][] data = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getRecordsAsArray();
			tableModel = new DefaultTableModel(data, columnNames);
			table.setModel(tableModel);
		} else if (src == btnDeleteTable) {
			manager.getCurDB().delTable((String)tablesBox.getSelectedItem());
			refreshData();
		} else if (src == btnAddRecord) {
			if (tablesBox.getSelectedIndex() != -1) {
				tableModel.addRow(new Object[] { null });
			}
		} else if (src == btnAddTable) {
			new AddTableForm();
			refreshData();
		} else if (src == btnDeleteRecord) {
			if (table.getSelectedRow() != -1)
				tableModel.removeRow(table.getSelectedRow());
		} else if (src == btnCancelChanges) {
			if (tablesBox.getSelectedIndex() != -1) {
				String[] columnNames = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getColumnNames();
				Object[][] data = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getRecordsAsArray();
				tableModel = new DefaultTableModel(data, columnNames);
				table.setModel(tableModel);
			}
		} else if (src == btnConfirmChanges) {
			ArrayList<Record> res = new ArrayList<Record>();
			Table selectedTable = manager.getCurDB().getTable((String)tablesBox.getSelectedItem());
			for (int i = 0; i < table.getRowCount(); ++i) {
				try {
					String[] stringFromTable = new String[table.getColumnCount()];
					for (int j = 0; j < table.getColumnCount(); ++j) {
						stringFromTable[j] = (String)table.getValueAt(i, j).toString();
					}
					res.add(new Record(stringFromTable , selectedTable.getHeader()));
				} catch (NullPointerException nullEx) { 
					JOptionPane.showMessageDialog(null, "There should be no empty fields!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
					ListSelectionModel selectionModel = 
							  table.getSelectionModel();
							selectionModel.setSelectionInterval(i, i);
					return;
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid data in the field!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
					ListSelectionModel selectionModel = 
							  table.getSelectionModel();
							selectionModel.setSelectionInterval(i, i);
					return;
				}
			}
			selectedTable.setRecords(res);
		} else if (src == btnOperations) {
			new OperationsForm();
		}
	}
	
	
	public void refreshData() {
		ArrayList<Table> ar = manager.getCurDB().getTableList();
		String[] tables = new String[ar.size()];
		for (int i = 0; i < ar.size(); ++i) {
			tables[i] = ar.get(i).getName();
		}
		tablesBox.setModel(new DefaultComboBoxModel<String>(tables));
		
		if (tables.length > 0) {
			String[] columnNames = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getColumnNames();
			Object[][] data = manager.getCurDB().getTable((String)tablesBox.getSelectedItem()).getRecordsAsArray();
			tableModel = new DefaultTableModel(data, columnNames);
		} else {
			tableModel = new DefaultTableModel();
		}
		table.setModel(tableModel);
	}
}
