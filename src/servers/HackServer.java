package servers;

import java.util.LinkedList;

public class HackServer implements ServerInterface {

    public class CpuCore {
        public final int maxLoadHandlingRate;
        public int cpuLoadHandlingRate, assignedPendingLoad;
        public int completedLoadThisTick;
        public LinkedList<HackRequest> assignedPendingRequests;

        public CpuCore() {
            cpuLoadHandlingRate = 0;
            maxLoadHandlingRate = cpuSpeed;
            assignedPendingLoad = 0;
            assignedPendingRequests = new LinkedList<HackRequest>();
        }

        public void tick() {
            for (HackRequest req : assignedPendingRequests) {
                req.latencyTracker++;
            }
            if (assignedPendingLoad < cache) {
                cpuLoadHandlingRate = maxLoadHandlingRate;
                completedLoadThisTick = assignedPendingLoad;
                assignedPendingLoad = 0;
            }
        }
    }

    public static int lifeLoad(HackRequest req) {
        return req.cpuLoad * req.dataSize;
    }

    public int cpuSpeed, cpuCores;
    public int cache, ram;
    public int cacheSpeed, ramSpeed;

    public CpuCore[] coreObjects;
    public LinkedList<HackRequest> unassignedPendingRequests;

    public HackServer(int cpuSpeed, int cpuCores, int cache, int ram,
            int cacheSpeed, int ramSpeed) {
        this.cpuSpeed = cpuSpeed;
        this.cpuCores = cpuCores;
        this.cache = cache;
        this.ram = ram;
        this.cacheSpeed = cacheSpeed;
        this.ramSpeed = ramSpeed;
        coreObjects = new CpuCore[cpuCores];
        for (int i = 0; i < cpuCores; ++i) {
            coreObjects[i] = new CpuCore();
        }
        unassignedPendingRequests = new LinkedList<HackRequest>();
    }

    public int getRamUse() {
    }

    public void fireRequest(HackRequest request) {
        unassignedPendingRequests.add(request);
    }

    public void returnToSender(HackRequest request) {
        request.user.receiveResponse(request.latencyTracker);
    }

    public void tick() {
        for (HackRequest req : unassignedPendingRequests) {
            req.latencyTracker++;
        }
    }

}
