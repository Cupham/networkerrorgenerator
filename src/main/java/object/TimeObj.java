package object;

public class TimeObj {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private int millisecond;
	
	
	
	
	public TimeObj() {
		super();
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(year);
		str.append("-");
		str.append(month);
		str.append("-");
		str.append(day);
		str.append(" ");
		str.append(hour);
		str.append(":");
		str.append(minute);
		str.append(":");
		str.append(second);
		str.append(".");
		str.append(millisecond);
		return str.toString();
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getMillisecond() {
		return millisecond;
	}
	public void setMillisecond(int millisecond) {
		this.millisecond = millisecond;
	}
	
	
	public double toMillis() {
		
		double rs = hour*3600*1000 + minute*60*1000 + second*1000 + millisecond/1000;
		
		return rs;
	}
	

}
