package servers;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class HackServer implements ServerInterface {

    public class CpuCore {
    	
    	
    	
        public final int maxLoadHandlingRate;
        public int cpuLoadHandlingRate, assignedPendingLoad;
        public int completedLoadThisTick;
        public ArrayList<HackRequest> assignedPendingRequests;
		private Integer cpuCoreNum;
		
		public Integer cpuLeft = 0;

        public CpuCore() {
            cpuLoadHandlingRate = 0;
            maxLoadHandlingRate = cpuSpeed;
            assignedPendingLoad = 0;
            assignedPendingRequests = new ArrayList<HackRequest>();
        }

        /**
         * Can the processor handle MORE REQUESTS!!!!!!
         * @return if it can handle more requests
         */
        public Boolean canHandleMore() {
        	int totalCPU = 0;
        	for (HackRequest req : assignedPendingRequests) {
        		totalCPU += req.cpuLoad;
        	}
        	
        	if (totalCPU < cpuSpeed) {
        		return true;
        	} else {
        		return false;
        	}
        }
        /**
         * Shows the cpu load (in percent, rounded to 1
         * @return cpu load percent as an integer. 
         */
        public Integer getLoad() {
        	Integer loadPercent = cpuSpeed*100/cpuLeft;
        	
			return loadPercent;
        	
        }
        
        public void tick() {
        	LinkedList<HackRequest> removal = new LinkedList<HackRequest>();
        	int totalCPU = cpuSpeed;
        	boolean outOfPower = false;
            for (HackRequest req : assignedPendingRequests) {
            	if (!outOfPower) {
            	Double percentCPUGimp = 1.0;//no gimp
            	
                //req.latencyTracker++;
                int cpuLoad = req.cpuLoad;
                if (totalCPU > cpuLoad) {
                	//enough cpu to spare
                totalCPU -= cpuLoad;
                } else {
                	//get percentage of gimp based on needed resource vs the resource
                	percentCPUGimp = (double) (totalCPU*1000 / cpuLoad)/1000;
                	outOfPower = true;
                }
                
                req.dataSize -= (int) (req.cacheReq*percentCPUGimp);
                if (req.dataSize<=0) {
                	req.user.receiveResponse(req.latencyTracker);
                	removal.add(req);
                	
                }
                
                
            	}//end of out of power
               
            }
            
            if (outOfPower) {
            	cpuLeft = 0;
            } else {
            	cpuLeft = totalCPU;
            }
            
            //remove everything that is done running
            while (!removal.isEmpty()) {
            	assignedPendingRequests.remove(removal.remove());
            }
            for (HackRequest req : assignedPendingRequests) {
            	System.out.println("CPU CORE: " + this.cpuCoreNum);
            	
            	
            	req.addTimeToProcess();
            }
            /*
            if (assignedPendingLoad < cache) {
                cpuLoadHandlingRate = maxLoadHandlingRate;
                completedLoadThisTick = assignedPendingLoad;
                assignedPendingLoad = 0;
            } else {
            	cpuLoadHandlingRate = ramSpeed;
                completedLoadThisTick = assignedPendingLoad;
                assignedPendingLoad = 0;
            }
            for (HackRequest req : assignedPendingRequests) {
                req.dataSize-=maxLoadHandlingRate;
                if (req.dataSize<=0)
            }
            */
        }
    }

    public static int lifeLoad(HackRequest req) {
        return req.cpuLoad * req.dataSize;
    }

    public int cpuSpeed, cpuCores;//CPU Speed, the clock cycles per tick
    public int cache, ram;
    public int cacheSpeed, ramSpeed;

    public ArrayList<CpuCore> coreObjects;
    public LinkedList<HackRequest> unassignedPendingRequests;

    
    public HackServer(int cpuSpeed, int cpuCores, int cache, int ram,
            int cacheSpeed, int ramSpeed) {
        this.cpuSpeed = cpuSpeed;
        this.cpuCores = cpuCores;
        this.cache = cache;
        this.ram = ram;
        this.cacheSpeed = cacheSpeed;
        this.ramSpeed = ramSpeed;
        //coreObjects = new CpuCore[cpuCores];
        coreObjects = new ArrayList<CpuCore>();
        for (int i = 0; i < cpuCores; ++i) {
        	
        	CpuCore core = new CpuCore();
        	core.cpuCoreNum = i;
            coreObjects.add(core);
        }
        unassignedPendingRequests = new LinkedList<HackRequest>();
    }

    public int getRamUse() {
        int use = 0;
        for (HackRequest req : unassignedPendingRequests) {
            use += req.dataSize;
        }
        for (CpuCore core : coreObjects) {
            for (HackRequest req : core.assignedPendingRequests) {
                use += req.dataSize;
            }
        }
        return use;
    }

    public void fireRequest(HackRequest request) {
        unassignedPendingRequests.add(request);
    }

    public void returnToSender(HackRequest request) {
        request.user.receiveResponse(request.latencyTracker);
    }

    public void tick() {
       
        
        for (CpuCore cpu : coreObjects) {
        	
        	while (cpu.canHandleMore() && !unassignedPendingRequests.isEmpty()) {
        		cpu.assignedPendingRequests.add(unassignedPendingRequests.removeFirst());
        	}
        	
        	//cpu.tick();
        }
        
        for (HackRequest req : unassignedPendingRequests) {
            req.addTimeToProcess();
        }
    }
    
    public Collection<CpuCore> getCpuCores() {
    	return coreObjects;
    }

}
