package ua.shark.dbms.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ua.shark.dbms.entities.Table;
import static ua.shark.dbms.Main.*;

public class OperationsForm extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> comboTable1;
	private JComboBox<String> comboTable2;
	private JComboBox<String> comboOperation;
	private JButton btnCalculate;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JTable table1;
	private JTable table2;
	private JTable tableResult;
	private DefaultTableModel tableModel1;
	private DefaultTableModel tableModel2;
	private DefaultTableModel tableModel3;
	
	public OperationsForm() {
		comboOperation = new JComboBox<String>(new DefaultComboBoxModel(new Object[]{"Join", "Intersect"}));
		comboTable1 = new JComboBox<String>();
		comboTable2 = new JComboBox<String>();
		table1 = new JTable();
		table2 = new JTable();
		tableResult = new JTable();
		table1.setColumnSelectionAllowed(true);
		table2.setColumnSelectionAllowed(true);
		table1.setRowSelectionAllowed(false);
		table2.setRowSelectionAllowed(false);
		table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		ArrayList<Table> ar = manager.getCurDB().getTableList();
		String[] tables = new String[ar.size()];
		for (int i = 0; i < ar.size(); ++i) {
			tables[i] = ar.get(i).getName();
		}
		comboTable1.setModel(new DefaultComboBoxModel<String>(tables));
		comboTable2.setModel(new DefaultComboBoxModel<String>(tables));
		
		if (tables.length > 0) {
			String[] columnNames = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).getColumnNames();
			Object[][] data = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).getRecordsAsArray();
			tableModel1 = new DefaultTableModel(data, columnNames);
			tableModel2 = new DefaultTableModel(data, columnNames);
		} else {
			tableModel1 = new DefaultTableModel();
			tableModel2 = new DefaultTableModel();
		}
		table1.setModel(tableModel1);
		table2.setModel(tableModel2);
		init();
	}
	
	public void init() {
		setTitle("Operations");
		setResizable(false);
		setModal(true);
		setBounds(350, 250, 850, 400);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboTable1.setBounds(10, 11, 266, 20);
		comboTable1.addActionListener(this);
		contentPanel.add(comboTable1);
		
		comboTable2.setBounds(286, 11, 268, 20);
		comboTable2.addActionListener(this);
		contentPanel.add(comboTable2);
		
		comboOperation.setBounds(564, 11, 167, 20);
		contentPanel.add(comboOperation);
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.setBounds(741, 10, 93, 23);
		btnCalculate.addActionListener(this);
		contentPanel.add(btnCalculate);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 270, 319);
		contentPanel.add(scrollPane);
		scrollPane.setViewportView(table1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(286, 42, 270, 319);
		contentPanel.add(scrollPane_1);
		scrollPane_1.setViewportView(table2);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(564, 42, 270, 319);
		contentPanel.add(scrollPane_2);
		tableResult = new JTable();
		scrollPane_2.setViewportView(tableResult);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == comboTable1) {
			String[] columnNames = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).getColumnNames();
			Object[][] data = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).getRecordsAsArray();
			tableModel1 = new DefaultTableModel(data, columnNames);
			table1.setModel(tableModel1);
		} else if (src == comboTable2) {
			String[] columnNames = manager.getCurDB().getTable((String)comboTable2.getSelectedItem()).getColumnNames();
			Object[][] data = manager.getCurDB().getTable((String)comboTable2.getSelectedItem()).getRecordsAsArray();
			tableModel2 = new DefaultTableModel(data, columnNames);
			table2.setModel(tableModel2);
		} else if (src == btnCalculate) {
			if (comboTable1.getSelectedIndex() == -1 || comboTable2.getSelectedIndex() == -1 || comboOperation.getSelectedIndex() == -1)
				return;
			if (comboOperation.getSelectedIndex() == 0 && (table1.getSelectedColumn() == -1 || table2.getSelectedColumn() == -1)) {
				tableModel3 = new DefaultTableModel();
				tableResult.setModel(tableModel3);
				return;
			}
			if (comboOperation.getSelectedIndex() == 0) {
				Table test = manager.getCurDB().getTable((String)comboTable1.getSelectedItem());
				try {
					Class[] args = new Class[]{Table.class, Integer.class, Integer.class};
					Method m = test.getClass().getDeclaredMethod("joinTables", args);
					Table res = (Table)m.invoke(manager.getCurDB().getTable((String)comboTable1.getSelectedItem()), 
							manager.getCurDB().getTable((String)comboTable2.getSelectedItem()),
							table1.getSelectedColumn(),
							table2.getSelectedColumn());
					String[] columnNames = res.getColumnNames();
					Object[][] data = res.getRecordsAsArray();
					tableModel3 = new DefaultTableModel(data, columnNames);
					tableResult.setModel(tableModel3);
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}
				/*Table res = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).joinTables(
						manager.getCurDB().getTable((String)comboTable2.getSelectedItem()), table1.getSelectedColumn(), table2.getSelectedColumn());
				String[] columnNames = res.getColumnNames();
				Object[][] data = res.getRecordsAsArray();
				tableModel3 = new DefaultTableModel(data, columnNames);
				tableResult.setModel(tableModel3);*/
			} else if (comboOperation.getSelectedIndex() == 1) {
				Table res = manager.getCurDB().getTable((String)comboTable1.getSelectedItem()).intersectTables(manager.getCurDB().getTable((String)comboTable2.getSelectedItem()));
				String[] columnNames = res.getColumnNames();
				Object[][] data = res.getRecordsAsArray();
				tableModel3 = new DefaultTableModel(data, columnNames);
				tableResult.setModel(tableModel3);
			}
		}
	}
}
