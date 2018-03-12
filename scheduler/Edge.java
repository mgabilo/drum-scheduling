package scheduler;

public class Edge {
	public Node start;
	public Node finish;
	
	public Edge(Node finish, Node start)
	{
		this.start = start;
		this.finish = finish;
	}
	
	public double cost()
	{
		if (this.start.id == 0) {
			return 0;
		}
		if (this.start.compareTo(this.finish) >= 0)
			return this.start.position - this.finish.position;
		else
			return 1 + (this.start.position - this.finish.position);
	}
	public String toString()
	{
		return this.finish + "  --->  " + this.start;
	}
}
