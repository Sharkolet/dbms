package ua.shark.dbms.types;

import java.io.Serializable;

public class RealInterval implements Serializable{
	private double from;
	private double to;
	
	public RealInterval(String interval) {
        try {
            String[] str = interval.split(";");
            from = Double.parseDouble(str[0]);
            to = Double.parseDouble(str[1]);
        } catch (Exception e) {
            throw new NumberFormatException();
        }
    }

    public double getFrom() {
        return from;
    }

    public void setFromPoint(double fromPoint) {
        this.from = fromPoint;
    }

    public double getTo() {
        return to;
    }

    public void setToPoint(double to) {
        this.to = to;
    }
    
    @Override
    public String toString() {
    	return new String(from + ";" + to);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(from);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(to);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RealInterval other = (RealInterval) obj;
		if (Double.doubleToLongBits(from) != Double
				.doubleToLongBits(other.from))
			return false;
		if (Double.doubleToLongBits(to) != Double.doubleToLongBits(other.to))
			return false;
		return true;
	}
    
}
