package main;

import java.util.ArrayList;

import Balance.Balance;
import servers.HackServer;
import servers.ServerInterface;
import user.User;

public class Main {

	//TODO make load balancer class
	
	public static ServerInterface server;
	public static ServerInterface loadBal;
	public static ArrayList<ServerInterface> allServers = new ArrayList<ServerInterface>();
	public static ArrayList<User> allUsers = new ArrayList<User>();
	public static void main(String[] args) {
		server = new HackServer(1, 12, 10, 32, 1024, 32);
		allServers.add(server);
		loadBal = new Balance();
		
		allServers.add(loadBal);
		
		for (int i=0;i<1;i++) {
			genNewUser();
		}
		

		
		for (int i=0;i<100;i++) {
			tick();
		}
		
	}
	static int turn = 0;
	public static void tick() {
		turn ++;
		for (ServerInterface si : allServers) {
			si.tick();
			//System.out.println(((HackServer) si).getRamUse());
		}
		for (User u : allUsers) {
			u.tick();
		}
		System.out.println("Tick: " + turn);
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void genNewUser() {
		User u = new User(loadBal);
		
		allUsers.add(u);
	}
	
	
}
