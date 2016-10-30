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
        }
    }

    public int cacheUse, ramUse;
    public LinkedList<Integer> unassignedPendingLoads;

    public int cpuSpeed, cpuCores;
    public int cache, ram;
    public int cacheSpeed, ramSpeed;

    public CpuCore[] coreObjects;
    public LinkedList<HackRequest> pendingRequests;

    public void fireRequest(HackRequest request) {
        pendingRequests.add(request);
    }

    public void returnToSender(HackRequest request) {
        request.user.receiveResponse();
    }

    public void tick() {
    }

}
