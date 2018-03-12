package scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Drum {
	public Node[] finish;
	public Node[] start;
	
	public Drum(String records_filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(records_filename));
		
		int read_state = 0;
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.startsWith("#") || line.trim().length() == 0)
				continue;
			
			String[] tokens = line.split(" .*?");
			
			if (read_state == 0) {
				this.finish = new Node[Integer.valueOf(tokens[0]) + 1];
				this.start = new Node[Integer.valueOf(tokens[0]) + 1];
				this.start[0] = new Node(false, 0, 0);
			}
			else if (read_state == 1) {
				this.finish[0] = new Node(true, 0, Double.valueOf(tokens[0]));
			}
			else {
				double start = Double.valueOf(tokens[0]);
				double finish = Double.valueOf(tokens[1]);
				this.start[read_state - 1] = new Node(false, read_state-1, start);
				this.finish[read_state - 1] = new Node(true, read_state-1, finish);
			}
			
			read_state++;
		}
	}
}
