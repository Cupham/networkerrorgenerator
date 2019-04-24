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

import object.Flow;
import object.FlowEntry;

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
    	Date date;
		try {
			sdf.setLenient(true);
			date = sdf.parse(line.get(0));
			System.out.println(line.get(0));
			System.out.println(date.toString());
			fe.setTime(date);
		} catch (ParseException e) {
			System.out.println("Error while parsing date time");
			e.printStackTrace();
		}
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
    
    public static void writeFlowToFile(FileWriter fw, Flow flow) {
    	 System.out.println("Writing a Flow to File:" + flow.toString());
         try
         {         
         	
         	StringBuffer oneLine = new StringBuffer();
             
             oneLine.append(flow.getFlowNo());
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(sdf.format(flow.getFirstSentTime()));
             oneLine.append(DEFAULT_SEPARATOR);
             oneLine.append(sdf.format(flow.getLastReceivedTime()));
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
