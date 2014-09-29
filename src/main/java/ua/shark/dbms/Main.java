package ua.shark.dbms;

import java.awt.EventQueue;

import ua.shark.dbms.entities.DBManager;
import ua.shark.dbms.gui.MainForm;

public class Main {
	public static DBManager manager;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
