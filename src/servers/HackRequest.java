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

	@Override
	public String toString() {
		return "HackRequest [cpuLoad=" + cpuLoad + ", cacheReq=" + cacheReq + ", dataSize=" + dataSize + ", user="
				+ user + ", latencyTracker=" + latencyTracker + "]";
	}
	
}
