package servers;

import java.util.LinkedList;

public class HackServer implements ServerInterface {

    public class CpuCore {
        public int cpuLoadHandlingRate, maxLoadHandlingRate;
        public int assignedPendingLoad, completedLoadThisTick;
        public LinkedList<HackRequest> assignedPendingRequests;

        public CpuCore() {
            cpuLoadHandlingRate = 0;
            maxLoadHandlingRate = Math.min(cacheSpeed, cpuSpeed);
            assignedPendingLoad = 0;
        }

        public void tick() {
            for (HackRequest req : assignedPendingRequests) {
                req.latencyTracker++;
            }
        }
    }

    public int cacheUse, ramUse;

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
