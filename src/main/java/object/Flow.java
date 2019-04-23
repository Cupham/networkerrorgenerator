package object;

import java.util.Date;

public class Flow {
	private Date time;
	private String srcIP;
	private String dstIP;
	private String protocol;
	private String data;
	private int srcPort;
	private int dstPort;
	private int frameSize;
	private int pkSize;
	private boolean isError;
	
	public Flow() {
		
	}
	public Flow(Date time, String srcIP, String dstIP, String protocol, int srcPort, int dstPort, int frameSize,
			int pkSize) {
		super();
		this.time = time;
		this.srcIP = srcIP;
		this.dstIP = dstIP;
		this.protocol = protocol;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.frameSize = frameSize;
		this.pkSize = pkSize;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSrcIP() {
		return srcIP;
	}
	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}
	public String getDstIP() {
		return dstIP;
	}
	public void setDstIP(String dstIP) {
		this.dstIP = dstIP;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}
	public int getDstPort() {
		return dstPort;
	}
	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}
	public int getFrameSize() {
		return frameSize;
	}
	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}
	public int getPkSize() {
		return pkSize;
	}
	public void setPkSize(int pkSize) {
		this.pkSize = pkSize;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
}
