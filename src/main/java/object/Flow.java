package object;

import java.util.Date;

public class Flow {
	private int flowNo;
	private Date firstSentTime;
	private Date lastReceivedTime;
	private String fromIP;
	private String toIP;
	private int sendToPort;
	private int receivedFromPort;
	private int sentFrameSize;
	private int receivedFrameSize;
	private String sentData;
	private String receivedData;
	private String protocol;
	private boolean isAbnormal;
	private long transactionTime;
	private boolean hasSubFlow;
	
	public Flow() {
		
	}

	public int getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(int flowNo) {
		this.flowNo = flowNo;
	}

	public Date getFirstSentTime() {
		return firstSentTime;
	}

	public void setFirstSentTime(Date firstSentTime) {
		this.firstSentTime = firstSentTime;
	}

	public Date getLastReceivedTime() {
		return lastReceivedTime;
	}

	public void setLastReceivedTime(Date lastReceivedTime) {
		this.lastReceivedTime = lastReceivedTime;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}

	public String getToIP() {
		return toIP;
	}

	public void setToIP(String toIP) {
		this.toIP = toIP;
	}

	public int getSendToPort() {
		return sendToPort;
	}

	public void setSendToPort(int sendToPort) {
		this.sendToPort = sendToPort;
	}

	public int getReceivedFromPort() {
		return receivedFromPort;
	}

	public void setReceivedFromPort(int receivedFromPort) {
		this.receivedFromPort = receivedFromPort;
	}

	public int getSentFrameSize() {
		return sentFrameSize;
	}

	public void setSentFrameSize(int sentFrameSize) {
		this.sentFrameSize = sentFrameSize;
	}

	public int getReceivedFrameSize() {
		return receivedFrameSize;
	}

	public void setReceivedFrameSize(int receivedFrameSize) {
		this.receivedFrameSize = receivedFrameSize;
	}

	public String getSentData() {
		return sentData;
	}

	public void setSentData(String sentData) {
		this.sentData = sentData;
	}

	public String getReceivedData() {
		return receivedData;
	}

	public void setReceivedData(String receivedData) {
		this.receivedData = receivedData;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isAbnormal() {
		return isAbnormal;
	}

	public void setAbnormal(boolean isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(long transactionTime) {
		this.transactionTime = transactionTime;
	}

	public boolean isHasSubFlow() {
		return hasSubFlow;
	}

	public void setHasSubFlow(boolean hasSubFlow) {
		this.hasSubFlow = hasSubFlow;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Flow #: " + getFlowNo() + "---");
		str.append("From IP: " + getFromIP() + "---");
		str.append("To IP: " + getToIP() + "\n");
		return str.toString();
	}
	
	
}
