package Balance;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import servers.HackRequest;
import servers.HackServer;
import servers.ServerInterface;
import user.User;

public class Balance implements ServerInterface{
	
	
	public static ArrayList<HackServer> Servers = new ArrayList<HackServer>();
	public Integer threshholdlimit = 30;
	public Integer threshhold = 0;
	public Integer minservers = 1;
	public Integer maxservers = 10;
	public Integer location = 0;
	public Integer TotalServers = 0;
	public Integer cpuSpeed = 200;    
	public Integer cpuCores = 12;
	public Integer cache = 32;
	public Integer ram = 1024;
	public Integer cacheSpeed = 1000;
	public Integer ramSpeed = 64;
	
	
	public Balance()
	{
		TotalServers = ThreadLocalRandom.current().nextInt(minservers, maxservers + 1);
		for(int x = 0; x < TotalServers; x++)
		{
		HackServer hs = new HackServer(cpuSpeed, cpuCores, cache, ram, cacheSpeed, ramSpeed);
		Servers.add(hs);
		}
	}
	
	@Override
	public void fireRequest(HackRequest request) 
	{
		
		if(location > TotalServers)
		{
		location = 0;
		}
		Servers.get(location).fireRequest(request);; 
		location = location + 1;
	}
	@Override
	public void tick() 
	{
		for (int x = 0; x < TotalServers; x++ )
		{
			Servers.get(x).tick();
		}
	}


}
