package util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import object.Flow;
import object.FlowEntry;
import object.TimeObj;

public class utils {
	private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    
	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
	
	public static void writeFlowHeader (FileWriter fw) {
		 System.out.println("Writing header");
	        try
	        {         
	        	
	        	StringBuffer oneLine = new StringBuffer();
	            
	        	oneLine.append("FlowNo");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("FirstSentTime");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("LastReceivedTime");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("FromIP");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("ToIP");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("SendToPort");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("ReceivedFromPort");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("SentFrameSize");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("ReceivedFrameSize");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("SentData");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("ReceivedData");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("Protocol");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("TransactionTime");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("HasSubFlow");
	            oneLine.append(DEFAULT_SEPARATOR);
	            oneLine.append("isAbnormal");
	            oneLine.append("\n");
	    
	        	fw.write(oneLine.toString());
	        }
	        catch (UnsupportedEncodingException e) {}
	        catch (FileNotFoundException e){}
	        catch (IOException e){}
        
        
	}
	public static void writeFileHeader(FileWriter fw)
    {
		 System.out.println("Writing header");
        try
        {         
        	
        	StringBuffer oneLine = new StringBuffer();
            
            oneLine.append("Time");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("SrcIP");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("DstIP");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("SrcPort");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("DstPort");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("FrameSize");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("Protocol");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("PacketSize");
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append("Data");
          //  oneLine.append(DEFAULT_SEPARATOR);
           // oneLine.append("isAbnormal");
            oneLine.append("\n");
    
        	fw.write(oneLine.toString());
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
	public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }
    
    public static FlowEntry flowEntryFromLine(List<String> line,int lineNo) {
    	FlowEntry fe  = new FlowEntry();
		fe.setTime(utils.stringToTime(line.get(0)));
		fe.setPacketNo(lineNo);
    	if(!line.get(1).equals("")) {
    		fe.setSrcIP(line.get(1));
    	}
    	if(!line.get(2).equals("")) {
    		fe.setDstIP(line.get(2));
    	}
    	if(!line.get(3).equals("")) {
    		fe.setSrcPort(Integer.parseInt(line.get(3)));
    	}
    	if(!line.get(4).equals("")) {
    		fe.setDstPort(Integer.parseInt(line.get(4)));
    	}
    	if(!line.get(5).equals("")) {
    		fe.setFrameSize(Integer.parseInt(line.get(5)));
    	}
    	if(!line.get(6).equals("")) {
    		fe.setProtocol(line.get(6));
    	}
    	if(!line.get(7).equals("")) {
    		fe.setPkSize(Integer.parseInt(line.get(7)));
    	}
    	if(!line.get(8).equals("")) {
    		fe.setData(line.get(8));
    	}
    	return fe;
    }
    public static FlowEntry flowEntryFromLine(List<String> line,String targetIP,String protocol) {
    	FlowEntry fe  = new FlowEntry();
    	return fe;
    }
    public static FlowEntry flowEntryFromLine(List<String> line,String targetIP) {
    	FlowEntry fe  = new FlowEntry();
    	return fe;
    }
    
    public static Flow flowFromEntries(String srcIP) {
    	Flow flow = new Flow();
    	
    	return flow;
    }
    
    public static TimeObj stringToTime (String time) {
    	String pattern ="(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}).(\\d{6})";

		Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(time);
	    TimeObj etime = new TimeObj();
	    if(m.find()) {
	    	etime.setYear(Integer.parseInt(m.group(1)));
	    	etime.setMonth(Integer.parseInt(m.group(2)));
	    	etime.setDay(Integer.parseInt(m.group(3)));
	    	etime.setHour(Integer.parseInt(m.group(4)));
	    	etime.setMinute(Integer.parseInt(m.group(5)));
	    	etime.setSecond(Integer.parseInt(m.group(6)));
	    	etime.setMillisecond(Integer.parseInt(m.group(7)));
	    }
	    return etime;
    }
    public static double deltaTimeMillis(TimeObj greater, TimeObj smaller) {
		TimeObj delta = new TimeObj();
		delta.setYear(greater.getYear() - smaller.getYear());		
		delta.setMonth(greater.getMonth() - smaller.getMonth());
		delta.setDay(greater.getDay() - smaller.getDay());
		delta.setHour(greater.getHour() - smaller.getHour());
		delta.setMinute(greater.getMinute() - smaller.getMinute());
		delta.setSecond(greater.getSecond() - smaller.getSecond());
		delta.setMillisecond(greater.getMillisecond() - smaller.getMillisecond());
		System.out.println(greater.toString());
		System.out.println(smaller.toString());
		System.out.println(delta.toString());
		System.out.println(delta.toMillis() + " ms");
		return delta.toMillis();
	}
    public static void writeFlowToFile(FileWriter fw, Flow flow) {
    	 System.out.println("Writing a Flow to File:" + flow.toString());
         try
         {         
         	
         	StringBuffer oneLine = new StringBuffer();
             
             oneLine.append(flow.getFlowNo());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getFirstSentTime().toString());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getLastReceivedTime().toString());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getFromIP());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getToIP());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getSendToPort());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getReceivedFromPort());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getSentFrameSize());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getReceivedFrameSize());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getSentData());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getReceivedData());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getProtocol());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.getTransactionTime());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.isHasSubFlow());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(flow.isAbnormal());
             oneLine.append("\n");
     
         	fw.write(oneLine.toString());
         }
         catch (UnsupportedEncodingException e) {}
         catch (FileNotFoundException e){}
         catch (IOException e){}
    	
    }
}
