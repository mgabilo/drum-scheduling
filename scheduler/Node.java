package scheduler;


public class Node implements Comparable<Node> {

	public boolean finish_node;
	
	public int id;
	public double position;
	
	public Node(boolean finish_node, int id, double position)
	{
		this.finish_node = finish_node;
		this.id = id;
		this.position = position;
	}
	
	public int compareTo(Node rhs)
	{
		if (this.position < rhs.position)
			return -1;
		else if (this.position > rhs.position)
			return 1;
		return 0;
	}
	
	public boolean equals(Node rhs)
	{
		return this.id == rhs.id && this.finish_node == rhs.finish_node &&
			this.position == rhs.position;
	}
	
	public String toString()
	{
		if (finish_node) {
			return "f" + id + " " + position;
		}
		else {
			return "s" + id + " " + position;
		}
	}
}
