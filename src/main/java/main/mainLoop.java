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
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import object.Flow;
import object.FlowEntry;
import util.utils;

public class mainLoop {
	
    private static int lineCounter;
    private static int flowCounter;
    private static FileWriter fw;
    private static String sourceFileName = "error.csv";
    private static String exportFileName = "injectedFlow.csv";
    private static String targetIP_Delay = "192.168.2.167";
    private static String targetIP_PacketError = "192.168.2.195";
    private static String targetProtocol = "eth:ethertype:ip:udp:data";
    private static ArrayList<FlowEntry> entryQueue;
    private static ArrayList<Flow> flowQueue;
    private static ArrayList<Flow> toBeRemoved;
    
    public static ArrayList<String> controllerIP = new ArrayList<String>(
			Arrays.asList("192.168.3.163","192.168.2.238"));
    
	public static void main(String[] args) throws ParseException, IOException {
		
        lineCounter = 0;
        flowCounter = 0;
        entryQueue = new ArrayList<FlowEntry>();
        flowQueue= new ArrayList<Flow>();
        toBeRemoved= new ArrayList<Flow>();
        fw = new FileWriter(new File(exportFileName),true);
        utils.writeFlowHeader(fw);
        
        //load source file
        System.out.println("Reading file....");
        Scanner scanner = new Scanner(new File(sourceFileName));
        while (scanner.hasNext()) {
        	lineCounter++;
        	System.out.println("line " + lineCounter);
        	//limit number of line for testing purposes
        	if(lineCounter <2000000 ) {
	            List<String> line = utils.parseLine(scanner.nextLine());
	            if(line.get(6).equals(targetProtocol)) {
		            FlowEntry entry = utils.flowEntryFromLine(line,lineCounter);
		            if(controllerIP.contains(entry.getSrcIP())) {
		            	flowCounter++;
		            	Flow f = new Flow();
		            	f.setFlowNo(flowCounter);
		            	f.setFirstSentTime(entry.getTime());
		            	f.setFromIP(entry.getSrcIP());
		            	f.setToIP(entry.getDstIP());
		            	f.setReceivedFromPort(entry.getSrcPort());
		            	f.setSendToPort(entry.getDstPort());
		            	f.setSentFrameSize(entry.getFrameSize());
		            	f.setSentData(entry.getData());
		            	f.setProtocol(entry.getProtocol());
		            	flowQueue.add(f);
		            } else {
		            	entryQueue.add(entry);
		            }
		            for(int i = 0;i <flowQueue.size(); i++) {
		            	for(int j =0;j < entryQueue.size(); j++) {		         
		            		if(flowQueue.get(i).getFromIP().equals(entryQueue.get(j).getDstIP()) && 
		            		   flowQueue.get(i).getToIP().equals(entryQueue.get(j).getSrcIP())) {
		            			Flow aFlow = flowQueue.get(i);
		            			FlowEntry flowEntry  = entryQueue.get(j);
		            			aFlow.setLastReceivedTime(flowEntry.getTime());
			            		aFlow.setReceivedFrameSize(flowEntry.getFrameSize());
			            		aFlow.setReceivedData(flowEntry.getData());
			            		long time = aFlow.getLastReceivedTime().getTime() - aFlow.getFirstSentTime().getTime();
			            		if(time <0) {
			            			System.out.println("Something went wrong with flow: " + aFlow.toString());
			            		} else {
			            			aFlow.setTransactionTime(time);
			            		}
			            		//finish creating flow
			            		entryQueue.remove(j);
			            		utils.writeFlowToFile(fw, aFlow);
			            		toBeRemoved.add(aFlow);
		            		   }
		            	}
		            	
		            }
		            for(Flow flow : toBeRemoved) {
		            	flowQueue.remove(flow);
		            }
		            toBeRemoved.clear();
		            
	            }
	            /*
	            Iterator<Flow> flowIter = flowQueue.iterator(); 
	            while(flowIter.hasNext()){	      
	        		Flow aFlow = flowIter.next();	        	
	        		Iterator<FlowEntry> entryIter = entryQueue.iterator();
	        		while(entryIter.hasNext()) {
	        			FlowEntry flowEntry = entryIter.next();
	        			if(aFlow.getFromIP().equals(flowEntry.getDstIP()) && aFlow.getToIP().equals(flowEntry.getSrcIP())) {
		            		// Create a flow
		            		aFlow.setLastReceivedTime(flowEntry.getTime());
		            		aFlow.setReceivedFrameSize(flowEntry.getFrameSize());
		            		aFlow.setReceivedData(flowEntry.getData());
		            		long time = aFlow.getLastReceivedTime().getTime() - aFlow.getFirstSentTime().getTime();
		            		if(time <0) {
		            			System.out.println("Something went wrong with flow: " + aFlow.toString());
		            		} else {
		            			aFlow.setTransactionTime(time);
		            		}
		            		//finish creating flow
		            		entryIter.remove();
		            		utils.writeFlowToFile(fw, aFlow);
		            		flowIter.remove();
		            	}
	        			
	        		}                        	
	            }*/
	    
        	} 
        }
        scanner.close();
        fw.flush();
        fw.close();
        System.out.println("Finished reading file");
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
	
	
	


}

