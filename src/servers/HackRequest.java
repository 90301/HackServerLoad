package servers;

import user.User;

public class HackRequest {
	public int cpuLoad;
	public int cacheReq;
	public int dataSize;
	public User user;
    public int latencyTracker = 0;
	
	public HackRequest() {
		// TODO Auto-generated constructor stub
	}
	
}
