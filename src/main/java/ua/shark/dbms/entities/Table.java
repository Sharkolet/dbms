package ua.shark.dbms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
	private String name;
	private List<Attribute> header;
	private List<Record> records;

	public Table(String name, List<Attribute> header) {
		this.name = name;
		this.header = header;
		records = new ArrayList<Record>();
	}

	public Table(String name) {
		this.name = name;
		this.header = new ArrayList<Attribute>();
		records = new ArrayList<Record>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Attribute> getHeader() {
		return header;
	}

	public void addAttribute(Attribute atr) {
		header.add(atr);
	}

	public boolean delAttribute(String name) {
		for (int i = 0; i < header.size(); ++i) {
			if (header.get(i).getName().equals(name)) {
				header.remove(i);
				for (int j = 0; j < records.size(); j++) {
					records.get(j).delValue(i);
				}
				return true;
			}
		}
		return false;
	}

	public boolean renameAttribute(String whatName, String forName) {
		for (int i = 0; i < header.size(); ++i) {
			if (header.get(i).equals(whatName)) {
				header.get(i).setName(forName);
				return true;
			}
		}
		return false;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public boolean addRecord(Record rec) {
		if (header.size() != rec.getValueList().size()) {
			return false;
		}
		for (int i = 0; i < header.size(); i++) {
			if (header.get(i).getType() != rec.getValue(i).getType())
				return false;
		}
		records.add(rec);
		return true;
	}
	
	// TODO we need to add implementating id
	public void delRecord(int id) { 
		records.remove(id);
	}
	
	public Table joinTables(Table b, int indexA, int indexB) { 
		if (this == null || b == null)
			return null;
		if (this.getHeader().size() < (indexA + 1) || b.getHeader().size() < (indexB + 1))
			return null;
		if (this.getHeader().get(indexA).getType() != b.getHeader().get(indexB).getType()) {
			return null;
		}
		Table res = new Table("Joining result");
		for (int i = 0; i < this.getHeader().size(); ++i ) {
			res.addAttribute(this.getHeader().get(i));
		}
		for (int i = 0; i < b.getHeader().size(); ++i ) {
			//if (i == indexB) continue;
			res.addAttribute(b.getHeader().get(i));
		}
		for (int i = 0; i < this.getRecords().size(); i++) {
			for (int j = 0; j < b.getRecords().size(); j++) {
					if (this.getRecords().get(i).getValue(indexA).equals(b.getRecords().get(j).getValue(indexB))) {
						Record rec = new Record();
						rec.getValueList().addAll(this.getRecords().get(i).getValueList());
						rec.getValueList().addAll(b.getRecords().get(j).getValueList());
						System.out.println("res " + rec);
						res.addRecord(rec);
					}
			}
		}
		return res;
		
	}
	
	public Table intersectTables(Table b) {
		Table res = new Table("Intersection result");
		if (this == null || b == null)
			return null;
		if (this.getHeader().size() != b.getHeader().size())
			return null;
		for (int i = 0; i < this.getHeader().size(); i++) {
			if (this.getHeader().get(i).getType() != b.getHeader().get(i).getType())
				return null;
		}
		for (int i = 0; i < this.getHeader().size(); i++) {
			res.addAttribute(new Attribute(this.getHeader().get(i).getName(), this.getHeader().get(i).getType()));
		}
		for (int i = 0; i < this.getRecords().size(); i++) {
			for (int j = 0; j < b.getRecords().size(); j++) {
				if (this.getRecords().get(i).equals(b.getRecords().get(i)))
					res.addRecord(this.getRecords().get(i));
			}
		}
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (records == null) {
			if (other.records != null)
				return false;
		} else if (!records.equals(other.records))
			return false;
		return true;
	}

}