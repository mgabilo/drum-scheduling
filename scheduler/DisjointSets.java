package scheduler;

/**
 * DisjointSets implements a disjoint sets data structure with union and find operations
 * @author michael
 *
 */
public class DisjointSets {
	
	private int[] uptree;
	
	public DisjointSets(int size)
	{
		uptree = new int[size];
		for (int i = 0; i < size; i++)
			uptree[i] = i;
	}
	
	public boolean disjoint(int element1, int element2)
	{
		return find(element1) != find(element2);
	}
	
	public int find(int element)
	{
		if (uptree[element] == element) {
			return element;
		}
		
		int canonical_element = find(uptree[element]);
		uptree[element] = canonical_element;
		
		return canonical_element;
	}
	
	public void union(int element1, int element2)
	{
		int canonical_element1 = find(element1);
		int canonical_element2 = find(element2);
		uptree[canonical_element1] = canonical_element2;
	}
}
