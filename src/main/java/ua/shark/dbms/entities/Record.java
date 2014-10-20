package ua.shark.dbms.entities;

import java.io.Serializable;
import java.util.ArrayList;

import ua.shark.dbms.types.RealInterval;

public class Record implements Serializable {
	private ArrayList<Object> values = new ArrayList<Object>();
	
	public Record() {
		values = new ArrayList<Object>();
	}
	
	public Record(ArrayList<Object> values) {
		this.values = values;
	}
	
	public Record(String[] str, ArrayList<Attribute> header) {
		if (str.length != header.size())
			throw new NumberFormatException();
		for (int i = 0; i < str.length; ++i) {
			Class clazz = header.get(i).getClazz();
			if (clazz.equals(Integer.class)) {
				values.add(new Integer(Integer.parseInt(str[i])));
			} else if (clazz.equals(Double.class)) {
				values.add(new Double(Double.parseDouble(str[i])));
			} else if (clazz.equals(Long.class)) {
				values.add(new Long(Long.parseLong(str[i])));
			} else if (clazz.equals(String.class)) {
				values.add(new String(str[i]));
			} else if (clazz.equals(RealInterval.class)) {
				values.add(new RealInterval(str[i]));
			}
		}
	}
	
	/*public void addValue(Object val) {
		values.add(val);
	}
	
	public boolean delValue(int i) {
		if (values.get(i) != null) {
			values.remove(i);
			return true;
		}
		return false;
	}
	
	public boolean editValue(int i, Value newVal) {
		if (values.get(i) != null) {
			values.set(i, newVal);
			return true;
		}
		return false;
	}*/
	
	public ArrayList<Object> getValueList() {
		return values;
	}
	
	public Object getValue(int i) {
		return values.get(i);
	}
	
	@Override
	public String toString() {
		String res = new String();
		for (int i = 0; i < values.size(); i++) {
			res = res + values.get(i) + "\t";
		}
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		Record other = (Record) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
}