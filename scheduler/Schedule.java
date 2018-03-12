package scheduler;

import java.util.*;
import java.io.*;

public class Schedule {
	
	Drum drum;

	public Schedule(String records_filename) throws FileNotFoundException
	{
		drum = new Drum(records_filename);
		Match match = new Match(drum);
		int[] permutation = match.minimumCostSingleCyclePermutation();
		//for (int i = 0; i < permutation.length; i++) {
		//	System.out.println(i + "   " + permutation[i]);
		//}
		
		
		System.out.println("The minimum latency schedule is");
		int next_finish_id = 0;
		while (permutation[next_finish_id] != 0) {
			System.out.printf("%d  ", permutation[next_finish_id]);
			next_finish_id = permutation[next_finish_id];
		}
		            
		System.out.printf("\nwith cost %.2f\n", match.permutation_cost(permutation));
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		new Schedule("scheduler/records.conf");
	}
}
