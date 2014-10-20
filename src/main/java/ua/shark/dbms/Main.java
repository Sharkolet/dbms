package ua.shark.dbms;

import java.awt.EventQueue;

import ua.shark.dbms.entities.DBManager;
import ua.shark.dbms.gui.MainForm;

public class Main {
	public static DBManager manager = new DBManager();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainForm();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}