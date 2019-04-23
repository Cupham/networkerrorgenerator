package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import object.Flow;

public class mainLoop {
	private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static int lineNo;
    private static FileWriter fw;

	public static void main(String[] args) throws ParseException, IOException {
		String csvFile = "error.csv";
        String delayIP = "192.168.2.167";
        String packetsizeIP = "192.168.2.195";
        String protocol = "eth:ethertype:ip:udp:data";
        ArrayList<Flow> flows  = new ArrayList<Flow>();
        lineNo = 0;
        
        fw = new FileWriter(new File("injected.csv"),true);
        System.out.println("Reading file....");
        Scanner scanner = new Scanner(new File(csvFile));
        while (scanner.hasNext()) {
        	lineNo++;
        	if(lineNo >4534670) {
            List<String> line = parseLine(scanner.nextLine());
            if(line.get(1).equals(delayIP) && line.get(6).equals(protocol) && isTarget(lineNo)) {   	
            	Flow flow = new Flow();
            	Date date  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(line.get(0));
            	flow.setTime(createDelayTime(date));
            	flow.setSrcIP(line.get(1));
            	flow.setDstIP(line.get(2));
            	flow.setSrcPort(Integer.parseInt(line.get(3)));
            	flow.setDstPort(Integer.parseInt(line.get(4)));
            	flow.setFrameSize(Integer.parseInt(line.get(5)));
            	flow.setProtocol(line.get(6));
            	flow.setPkSize(Integer.parseInt(line.get(7)));
            	flow.setData(line.get(8));
            	flow.setError(true);
            	writeToCSV(flow);
            	//flows.add(flow);
            } else if(line.get(1).equals(packetsizeIP) && line.get(6).equals(protocol) && isTarget(lineNo)) {
            	Flow flow = new Flow();
            	Date date  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(line.get(0));
            	int addedTime = random();
            	int frameSize =Integer.parseInt(line.get(5));
            	int pckSize = Integer.parseInt(line.get(7));    	
            	flow.setTime(date);
            	flow.setSrcIP(line.get(1));
            	flow.setDstIP(line.get(2));
            	flow.setSrcPort(Integer.parseInt(line.get(3)));
            	flow.setDstPort(Integer.parseInt(line.get(4)));
            	flow.setFrameSize(frameSize + addedTime);
            	flow.setProtocol(line.get(6));
            	flow.setPkSize(pckSize + addedTime);
            	flow.setData(line.get(8));
            	flow.setError(true);
            	writeToCSV(flow);
            	flows.add(flow);
            	
            } else {
            	Flow flow = new Flow();
            	Date date  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(line.get(0));
            	flow.setTime(date);
            	if(!line.get(1).equals("")) {
            		flow.setSrcIP(line.get(1));
            	}
            	if(!line.get(2).equals("")) {
            		flow.setDstIP(line.get(2));
            	}
            	if(!line.get(3).equals("")) {
            		flow.setSrcPort(Integer.parseInt(line.get(3)));
            	}
            	if(!line.get(4).equals("")) {
            		flow.setDstPort(Integer.parseInt(line.get(4)));
            	}
            	if(!line.get(5).equals("")) {
            		flow.setFrameSize(Integer.parseInt(line.get(5)));
            	}
            	if(!line.get(6).equals("")) {
            		flow.setProtocol(line.get(6));
            	}
            	if(!line.get(7).equals("")) {
            		flow.setPkSize(Integer.parseInt(line.get(7)));
            	}
            	if(!line.get(8).equals("")) {
            		flow.setData(line.get(8));
            	}
            	writeToCSV(flow);
            	flows.add(flow);
            }
        }
        }
        scanner.close();
        fw.close();
        System.out.println("Finished reading file");

       // writeToCSV(flows);

	}

	
	private static void writeToCSV(ArrayList<Flow> flows)
    {
		 System.out.println("Start writing file...");
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("injected.csv"), "UTF-8"));
            for (Flow flow : flows)
            {
                StringBuffer oneLine = new StringBuffer();
                
                oneLine.append(flow.getTime());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getSrcIP());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getDstIP());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getSrcPort());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getDstPort());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getFrameSize());
                oneLine.append(DEFAULT_SEPARATOR);        
                oneLine.append(flow.getProtocol());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getPkSize());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.getData());
                oneLine.append(DEFAULT_SEPARATOR);
                oneLine.append(flow.isError());
        
                bw.write(oneLine.toString());
                bw.newLine();
                
            }
            bw.flush();
            bw.close();
            System.out.println("I finished!!");
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
	
	private static void writeToCSV( Flow flow)
    {
		 System.out.println("Writing file... line : " + lineNo);
        try
        {         
        	
        	StringBuffer oneLine = new StringBuffer();
            
            oneLine.append(flow.getTime());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getSrcIP());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getDstIP());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getSrcPort());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getDstPort());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getFrameSize());
            oneLine.append(DEFAULT_SEPARATOR);        
            oneLine.append(flow.getProtocol());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getPkSize());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.getData());
            oneLine.append(DEFAULT_SEPARATOR);
            oneLine.append(flow.isError());
            oneLine.append("\n");
    
        	fw.write(oneLine.toString());
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
	
	public static Date createDelayTime(Date old) {
		Random r = new Random();
		Long newdate = old.getTime() + r.nextInt(1000) + 3000 ;
		return new Date(newdate);
	}
	
	public static boolean isTarget(int packetno) {
		Random r = new Random();
		int number = r.nextInt(10);
		int test = packetno % 9;
		if(test == number) {
			return true;
		} else {
			return false;
		}
		
	}
	public static int random() {
		Random r = new Random();
		int rs =  r.nextInt(100) + 20 ;
		return rs;
	}
	
	
	public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
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

}

