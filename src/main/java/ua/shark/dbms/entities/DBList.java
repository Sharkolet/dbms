package ua.shark.dbms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBList implements Serializable {
	private List<Database> dbList = new ArrayList<Database>();
	
	public DBList() {
		
	}
	
	public void addDB(Database db) {
		dbList.add(db);
	}
	
	public boolean delDB(String name) {
		for (int i = 0; i < dbList.size(); ++i) {
			if (dbList.get(i).getName().equals(name)) {
				dbList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public Database getDB(String name) {
		for (int i = 0; i < dbList.size(); ++i) {
			if (dbList.get(i).getName().equals(name)) {
				return dbList.get(i);
			}
		}
		return null;
	}
	
	public List<Database> getList() {
		return dbList;
	}
}
