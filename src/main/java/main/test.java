package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import object.TimeObj;
import util.utils;

public class test {

	public static void main(String[] args) {
		String pattern ="(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}).(\\d{6})";
		String time1 = "2019-03-08 16:19:31.369982";
		Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(time1);
	    System.out.println("Parsing..." );
	    TimeObj etime = new TimeObj();
	    if(m.find()) {
	    	etime.setYear(Integer.parseInt(m.group(1)));
	    	etime.setMonth(Integer.parseInt(m.group(2)));
	    	etime.setDay(Integer.parseInt(m.group(3)));
	    	etime.setHour(Integer.parseInt(m.group(4)));
	    	etime.setMinute(Integer.parseInt(m.group(5)));
	    	etime.setSecond(Integer.parseInt(m.group(6)));
	    	etime.setMillisecond(Integer.parseInt(m.group(7)));
	    	System.out.println(etime.toString() );
	    }
	    String time2 = "2019-03-08 16:18:30.070262";
	    m = p.matcher(time2);
	    TimeObj etime1 = new TimeObj();
	    if(m.find()) {
	    	etime1.setYear(Integer.parseInt(m.group(1)));
	    	etime1.setMonth(Integer.parseInt(m.group(2)));
	    	etime1.setDay(Integer.parseInt(m.group(3)));
	    	etime1.setHour(Integer.parseInt(m.group(4)));
	    	etime1.setMinute(Integer.parseInt(m.group(5)));
	    	etime1.setSecond(Integer.parseInt(m.group(6)));
	    	etime1.setMillisecond(Integer.parseInt(m.group(7)));
	    	System.out.println(etime1.toString() );
	    }
	    
	    System.out.println(utils.deltaTimeMillis(etime1, etime));
	    

	}

}
