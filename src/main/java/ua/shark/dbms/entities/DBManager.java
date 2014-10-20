package ua.shark.dbms.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DBManager {
	private Database curDB;
	
	public static ArrayList<String> getDBList() {
		ArrayList<String> lst = new ArrayList<String>();
		File[] fList;        
		File f = new File("src/main/resources");
		fList = f.listFiles();
		                
		for(int i = 0; i < fList.length; i++) {
		     if(fList[i].isFile() && fList[i].getName().endsWith(".mydb")) {
		    	 lst.add(fList[i].getName().substring(0, fList[i].getName().length() - 5));
		     }
		}
		return lst;
	}
	
	public Database getCurDB() {
		return curDB;
	}
	
	public void setCurDB(Database db) {
		curDB = db;
	}
	
	public void saveDB() throws IOException {
		FileOutputStream fos = new FileOutputStream("src/main/resources/" + curDB.getName() + ".mydb");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(curDB);
		oos.flush();
		oos.close();
	}
	
	public void loadDB(String name) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("src/main/resources/" + name + ".mydb");
		ObjectInputStream oin = new ObjectInputStream(fis);
		curDB = (Database) oin.readObject();
		oin.close();
	}
	
	public void deleteDB(String name) {
		File f = new File("src/main/resources/" + name + ".mydb");
		f.delete();
	}
}
