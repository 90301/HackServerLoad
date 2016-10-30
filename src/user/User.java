package user;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import servers.HackRequest;

public class User {
	public int workingHourStart,workingHourEnd;
	public HashMap<HackRequest,Integer> requestTypes = new HashMap<HackRequest,Integer>();
	public Integer genTotalPercent = 0;
	
	public Integer minLoad = 1;
	public Integer maxLoad = 100;
	public Integer minCache = 1;
	public Integer maxCache = 128;
	public Integer minDataSize = 1;
	public Integer maxDataSize = 128;
	
	public Integer minReqPercent = 1;
	public Integer maxReqPercent = 50;
	
	
	public Integer requestPercentageWorking;
	public Integer minRequestPercentageWorking = 1;
	public Integer maxRequestPercentageWorking = 10;
	//<= request = fire the event
	
	
	
	public User() {
		genRequests();
		
	}
	
	public void genRequests() {
		while (genTotalPercent < 100) {
			HackRequest hr = new HackRequest();
			hr.cpuLoad = ThreadLocalRandom.current().nextInt(minLoad, maxLoad + 1);
			hr.cacheReq = ThreadLocalRandom.current().nextInt(minCache, maxCache + 1);
			hr.dataSize = ThreadLocalRandom.current().nextInt(minDataSize, maxDataSize + 1);
			hr.user = this;
			int perChance = ThreadLocalRandom.current().nextInt(minDataSize, maxDataSize + 1);
			
			requestTypes.put(hr, perChance);
			
			genTotalPercent += perChance;
			
		}
	}
	
	/**
	 * When a request is fired, this picks the request to be sent off
	 * @return
	 */
	public HackRequest pickRequest() {
		int requestNum = ThreadLocalRandom.current().nextInt(0, genTotalPercent + 1);
		
		return null;
		
	}

}
