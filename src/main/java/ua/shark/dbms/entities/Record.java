package ua.shark.dbms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable {
	private List<Value> values;
	
	public Record() {
		values = new ArrayList<Value>();
	}
	
	public Record(List<Value> values) {
		this.values = values;
	}
	
	public void addValue(Value val) {
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
	}
	
	public List<Value> getValueList() {
		return values;
	}
	
	public Value getValue(int i) {
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