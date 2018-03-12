package scheduler;

import java.util.*;

public class Match {
	public Edge[] edges;
	public Drum drum;
	public Node[] sorted_nodes;
	
	public Match(Drum drum)
	{
		this.drum = drum;
		
		this.edges = new Edge[this.drum.finish.length];
		
		Node[] nodes = new Node[drum.finish.length + drum.start.length - 1];
		
		int i;
		for (i = 1; i < drum.start.length; i++) {
			nodes[i-1] = drum.start[i];
		}
		for (i = drum.start.length - 1; i < drum.start.length + drum.finish.length - 1; i++) {
			nodes[i] = drum.finish[i - (drum.start.length - 1)];
		}
		Arrays.sort(nodes);
		
		this.sorted_nodes = nodes;
	}
	
	private void makeBottomNode(Node f_delta)
	{
		double f_delta_position = f_delta.position;
		
		for (Node node : this.sorted_nodes) {
			if (node.position >= f_delta_position) {
				node.position = node.position - f_delta_position;
			}
			else {
				node.position = 1 + (node.position - f_delta_position);
			}
		}
		
		// sort and ensure f_delta comes first
		f_delta.position = -1;
		Arrays.sort(this.sorted_nodes);
		this.sorted_nodes[0].position = 0.0;
	}
	
	private boolean inHalfOpenRange(double s, double f_i, double f_j)
	{
		return s >= f_i && s < f_j;
	}
	private boolean zeroCostInterchange(Edge e1, Edge e2)
	{
		double low = Math.min(e1.finish.position, e2.finish.position);
		double high = Math.max(e1.finish.position, e2.finish.position);
		return (inHalfOpenRange(e1.start.position, low, high) &&
			inHalfOpenRange(e2.start.position, low, high)) ||
			
			(!inHalfOpenRange(e1.start.position, low, high) &&
					!inHalfOpenRange(e2.start.position, low, high));
	}
	
	public int[] minimumCostSingleCyclePermutation()
	{
		this.minimumCostPermutation();
		
		int[] permutation = new int[this.drum.finish.length];
		DisjointSets dj = new DisjointSets(this.drum.finish.length);
		for (Edge e : this.edges){
			if (dj.disjoint(e.finish.id, e.start.id)) {
				dj.union(e.finish.id, e.start.id);
			}
			permutation[e.finish.id]= e.start.id;
			
		}
		
		for (int i = 0; i < this.edges.length-1; i++)
		{
			if (zeroCostInterchange(this.edges[i], this.edges[i+1]) &&
					dj.disjoint(this.edges[i].finish.id, this.edges[i+1].finish.id)) {
				dj.union(this.edges[i].finish.id, this.edges[i+1].finish.id);
				
				int temp = permutation[this.edges[i].finish.id];
				permutation[this.edges[i].finish.id] = permutation[this.edges[i+1].finish.id];
				permutation[this.edges[i+1].finish.id] = temp;
				
				Node temp1 = this.edges[i].finish;
				this.edges[i].finish = this.edges[i+1].finish;
				this.edges[i+1].finish = temp1;
			}
		}
		
		for (int i = this.edges.length-1; i > 0; i--)
		{
			if (dj.disjoint(this.edges[i].finish.id, this.edges[i-1].finish.id)) {
				dj.union(this.edges[i].finish.id, this.edges[i-1].finish.id);
				int temp = permutation[this.edges[i].finish.id];
				permutation[this.edges[i].finish.id] = permutation[this.edges[i-1].finish.id];
				permutation[this.edges[i-1].finish.id] = temp;
				
				Node temp1 = this.edges[i].finish;
				this.edges[i].finish = this.edges[i-1].finish;
				this.edges[i-1].finish = temp1;
			}
		}
		return permutation;
	}
	
	public double permutation_cost(int[] permutation) {
		
		Edge[] edges_by_fin_id = new Edge[this.edges.length];
		for (Edge e : this.edges) {
			edges_by_fin_id[e.finish.id] = e;
		}
		
		double cost = 0;
		for (int finish_id = 0; finish_id < permutation.length; finish_id++) {
			cost += edges_by_fin_id[finish_id].cost();
		}
		return cost;
	}
	
	public void minimumCostPermutation()
	{
		Stack<Node> stack = new Stack<Node>();
		
		int i = 0;
		
		while (i < this.sorted_nodes.length) {
			
			// step 3
			while (i < this.sorted_nodes.length && !this.sorted_nodes[i].finish_node)
				i++;
			
			do {
				// steps 4 and 5
				while (i < this.sorted_nodes.length && this.sorted_nodes[i].finish_node) {
					stack.push(sorted_nodes[i]);
					i++;
				}
				
				// step 6
				stack.pop();
				i++;
				
			} while (!stack.isEmpty() && i < this.sorted_nodes.length);
		}
		
		Node f_delta = null;
		while (!stack.isEmpty()) {
			f_delta = stack.pop();
		}
		makeBottomNode(f_delta);
		
		Vector<Node> finish_list = new Vector<Node>();
		Vector<Node> start_list = new Vector<Node>();
		for (int j = this.sorted_nodes.length - 1; j >= 1; j--) {
			if (this.sorted_nodes[j].finish_node) {
				finish_list.add(this.sorted_nodes[j]);
			}
			else {
				start_list.add(this.sorted_nodes[j]);
			}
		}
		
		int k;
		for (k = 0; k < finish_list.size(); k++) {
			Node node1 = finish_list.elementAt(k);
			Node node2 = start_list.elementAt(k);
			this.edges[k] = new Edge(node1, node2);
		}
		this.edges[k] = new Edge(f_delta, this.drum.start[0]);
		
		//for (Edge e : this.edges){
		//	System.out.println(e);
		//}
		
	}

}
