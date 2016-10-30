package main;

import java.util.ArrayList;

import servers.HackServer;
import servers.ServerInterface;
import user.User;

public class Main {

	//TODO make load balancer class
	
	public static ServerInterface server;
	public static ArrayList<ServerInterface> allServers = new ArrayList<ServerInterface>();
	public static ArrayList<User> allUsers = new ArrayList<User>();
	public static void main(String[] args) {
		server = new HackServer(1, 12, 10, 32, 1024, 32);
		allServers.add(server);
		
		User u = new User();
		
		allUsers.add(u);
		
		for (int i=0;i<100;i++) {
			tick();
		}
		
	}
	
	public static void tick() {
		for (ServerInterface si : allServers) {
			si.tick();
		}
		for (User u : allUsers) {
			u.tick();
		}
	}
	
	
}
