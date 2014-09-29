package ua.shark.dbms.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4373584817640249387L;
	
	private String name;
	private SimpleDateFormat date;
	private List<Table> tables;
	
	public Database(String name) {
		this.name = name;
		tables = new ArrayList<Table>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SimpleDateFormat getDate() {
		return date;
	}
	
	public void setDate(SimpleDateFormat date) {
		this.date = date;
	}
	
	public void addTable(Table table) {
		tables.add(table);
	}
	
	public Table getTable(String name) {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getName().equals(name))
				return tables.get(i);
		}
		return null;
	}
	
	public boolean delTable(String name) {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getName().equals(name)) {
				tables.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public List<Table> getTableList() {
		return tables;
	}
	
	
}
