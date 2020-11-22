// Main Madden Class

import java.util.*;
import java.io.*;

public class Madden {
	///// PROGRAM SETTINGS /////
	public static boolean GAME_RANDOMIZE;
	public static Random GAME_SEED;

	public static boolean GAME_DELAY;

	public static PrintStream output;

	public static void main(String[] args) {
		Scanner testscan = new Scanner(System.in);
		System.out.println("Testing Arguments:");
		if (args.length > 0 ) {
			for (int x = 0; x < args.length; x++) {
				System.out.println("args[" + x + "]: " + args[x]);
			}
		}
		else {
			System.out.println("No args");
		}
		String userscan = testscan.nextLine();
		System.out.println("setting:" + userscan);

	}
}

			
		
		