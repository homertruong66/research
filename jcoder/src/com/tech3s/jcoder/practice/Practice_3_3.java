package com.tech3s.jcoder.practice;

import java.text.SimpleDateFormat;
import java.util.*;

public class Practice_3_3 {

	private static List<Integer> participants = Collections.unmodifiableList(Arrays.asList (
		25, 29, 22, 5, 11, 29, 4, 18, 6, 18, 8, 32, 17, 21,
		24, 36, 46, 54, 20, 27, 5, 33, 13, 14, 65, 62,  63,
		61, 9, 27, 48, 28, 19, 46, 34)
	);

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();
		System.out.println("There are " + participants.size() + " participants !!!");
		System.out.println("Generating 6 random numbers from 1 to 66...");
		//System.out.print("Please enter the number of integers to be randomly generated: ");
		int numberOfIntegers = 6; // scanner.nextInt();
		boolean noLuckyParticipant = true;
		while (noLuckyParticipant){
			for (int i = 1; i <= numberOfIntegers; i++) {
				int luckyNumber = random.nextInt(66);
				System.out.println(formatDate(new Date()) + " - Random number " + i + ": " + luckyNumber);
				if (participants.contains(luckyNumber)) {
					System.out.println("Congratulate lucky participant(s) with number: " + luckyNumber);
					noLuckyParticipant = false;
				}
			}
		}
		scanner.close();
	}

	private static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-YYYY");
		return sdf.format(date);
	}

}
