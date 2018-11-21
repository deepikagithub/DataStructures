package electronicvotingmachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class ElectionCountDirectAddressing {

	//If we check the sample voter IDs, they have a sequence, starting 151..hence to save the memory I have taken only 3 digits array 
	//as last 3 digits are the ones which are changing. I am deducting 151000 from every voter ID to get the unique index
	//When different IDs arrive in file(not matching our sequence) , array size can be increased later and logic for 
	//finding index can be modified accordingly.
	
	private static int[] candidateVoterInfo = new int[1000];

	//Takes a (voter_id, candidate_id) pair from the given file as input and stores it. Returns the array.
	private int[] ADD(int voter_id, int candidate_id) {

		//Since we are using direct addressing , subtracting numeral 151000 from every voter id to find a unique array index
		if((voter_id > 151000 && voter_id<151999) && (candidate_id > 0 && candidate_id<999)) {
			int i = voter_id - 151000;
			candidateVoterInfo[i] = candidate_id;

			return candidateVoterInfo;
		}
		else {
			System.out.println("Either voter id(151000-151999) or candidate id(0-999) is not in range. Please try again!!!");
			return candidateVoterInfo;
		}

	}

	//Takes a voter_id as input and outputs the candidate_id for whom the vote was cast
	private int FIND(int voter_id) {

		if(voter_id > 151000) {
			int a = voter_id - 151000;
			int x = candidateVoterInfo[a];

			if (x > 0) {
				return x;
			}
			else {
				System.out.println("No candidate id available for the input. Please try again !!!");
				return 0;
			}

		}
		else {
			System.out.println("The voter id does not exist");
			return 0;
		}
	}

	//Takes a candidate_id as input and outputs the total number of votes received by him/her
	private int COUNT(int candidate_id) {

		int count=0;
		for(int i=0 ; i<1000; i++) {

			if(candidateVoterInfo[i] == candidate_id) {
				count++;
			}
		}

		return count;
	}

	public static void main(String[] args) {

		int voter_id , candidate_id , voterIDFromUser , candidateIDFromUser = 0;
		ElectionCountDirectAddressing ec = new ElectionCountDirectAddressing();

		try { //Reading the input file line by line and feeding it into ADD method.
			FileReader fr = new FileReader("data.txt");
			BufferedReader reader = new BufferedReader(fr);

			String contentLine = reader.readLine();
			while(contentLine != null){  

				voter_id = Integer.parseInt(contentLine.substring(0, 6));
				candidate_id = Integer.parseInt(contentLine.substring(7));
				//System.out.println(voter_id + "," + candidate_id);
				ec.ADD(voter_id, candidate_id);
				contentLine = reader.readLine();
			}
			reader.close();
			fr.close(); 

		}catch(IOException e) {
			e.printStackTrace();
		}


		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Voter ID for which you want to see the candidate: ");
		voterIDFromUser = input.nextInt();
		System.out.printf("The candidate ID for which vote was casted by Voter ID %d is : " + ec.FIND(voterIDFromUser) , voterIDFromUser);

		System.out.println("\n");
		System.out.println("Enter the candidate ID for which you want to see the vote count: ");
		candidateIDFromUser = input.nextInt();
		System.out.printf("The no. of votes cast for candidate %d is : " +ec.COUNT(candidateIDFromUser) , candidateIDFromUser);
		input.close();

	}

}
