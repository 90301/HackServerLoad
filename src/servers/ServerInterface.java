package servers;

public interface ServerInterface {
	public void fireRequest(HackRequest request);
	public void tick();
}
