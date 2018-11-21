package electronicvotingmachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElectionCountHashTable {

	private static HashMap<Integer,Integer> electionHashMap = new HashMap<Integer,Integer>();

	//Takes a (voter_id, candidate_id) pair from the given file as input and stores it
	private HashMap<Integer,Integer> ADD(int voter_id , int candidate_id){

		electionHashMap.put(voter_id, candidate_id);
		return electionHashMap;
	}

	//Takes a voter_id as input and outputs the candidate_id for whom the vote was cast
	private int FIND(int voter_id) {

		if(electionHashMap.containsKey(voter_id)) {

			return electionHashMap.get(voter_id);

		}
		else {
			System.out.println("No record for this voter id exists in the system");
			return 0;
		}

	}

	//Takes a candidate_id as input and outputs the total number of votes received by him/her
	private int COUNT(int candidate_id){
		int count = 0;
		try {
			for(Map.Entry<Integer, Integer> entry : electionHashMap.entrySet()){
				if(entry.getValue() == candidate_id) {
					count++;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public static void main(String[] args) {

		int voter_id , candidate_id , voterIDFromUser , candidateIDFromUser= 0;
		ElectionCountHashTable ecHash = new ElectionCountHashTable();

		try { //Reading the input file line by line and feeding it into ADD method.
			FileReader fr = new FileReader("data.txt");
			BufferedReader reader = new BufferedReader(fr);

			String contentLine = reader.readLine();
			while(contentLine != null){  

				voter_id = Integer.parseInt(contentLine.substring(0, 6));
				candidate_id = Integer.parseInt(contentLine.substring(7));
				//System.out.println(voter_id + "," + candidate_id);
				ecHash.ADD(voter_id, candidate_id);
				contentLine = reader.readLine();
			}
			reader.close();
			fr.close(); 

		}catch(IOException e) {
			e.printStackTrace();
		}

		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Voter ID for which you want to see the candidate ID: ");
		voterIDFromUser = input.nextInt();
		System.out.printf("The candidate ID for which vote was casted by Voter ID %d is : " + ecHash.FIND(voterIDFromUser) , voterIDFromUser);

		System.out.println("\n");
		System.out.println("Enter the candidate ID for which you want to see the vote count: ");
		candidateIDFromUser = input.nextInt();
		System.out.printf("The no. of votes cast for candidate %d is : " +ecHash.COUNT(candidateIDFromUser) , candidateIDFromUser);
		
		input.close();
	}

}
