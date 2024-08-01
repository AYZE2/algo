import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
	
	private Vector<List<Integer>> adjacencyList = new Vector<List<Integer>>();
	
	public Graph(int size) {
		for (int i=0; i <size; i++) {
			this.adjacencyList.add(i, new ArrayList<Integer>());
		}
	}
	
	
	private void addEdge(int source, int destination) {
		// Add the edge to the adjacency list.
		this.adjacencyList.get(source).add(destination);
	}
	
	
	public void parseInputFile(String fileName) {
		// Initialising the file object.
		File inputFile = new File(fileName);
		try (Scanner scanner = new Scanner(inputFile)) {
			while (scanner.hasNextLine()) {
				String string = (String) scanner.nextLine();
				String[] edgeStrings = string.split(" ");
				addEdge(Integer.parseInt(edgeStrings[0]), Integer.parseInt(edgeStrings[1]));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	boolean isSink(int vertex) {
		// Check if the vertex has any outgoing edges.
		return this.adjacencyList.get(vertex).isEmpty();
	}
	
	// Finds and returns the first sink in the graph. If there are no sinks, returns -1.
	public int findSink() {
		for (int i = 0; i < this.adjacencyList.size(); i++) {
			if (this.adjacencyList.get(i).isEmpty())
				return i;
		}
		
		return -1;
	}
	
	public Vector<Integer> findAllSinks() {
		Vector<Integer> sinks = new Vector<Integer>();
		// Iterate over the adjacency list and add any sinks to the list.
		for (int i = 0; i < this.adjacencyList.size(); i++)
			if (this.adjacencyList.get(i).isEmpty())
				sinks.add(i);
		
		return sinks;	
	}
	
	public Vector<Integer> findAllSources() {
		Vector<Integer> sources = new Vector<Integer>();
		// Iterate over the adjacency list and add any sources to the list.
		for (int i = 0; i < this.adjacencyList.size(); i++) {
			boolean hasIncomingEdges = false;
			for (int j = 0; j < this.adjacencyList.size(); j++) {
				if (!this.adjacencyList.get(j).isEmpty()) {
					hasIncomingEdges = true;
					break;
				}
			}
			if (!hasIncomingEdges)
				sources.add(i);
		}
		
		return sources;
	}

	
	// Removes the specified sink from the graph.
	public void removeSink(int sink) {
		// Remove the sink from the adjacency list.
		this.adjacencyList.get(sink).clear();
		// Remove all incoming edges to the sink.
		for (int i = 0; i < this.adjacencyList.size(); i++) {
			this.adjacencyList.get(i).remove(sink);
		}
	}
	
	public boolean removeASink() {
		// Find all sinks in the graph.
		Vector<Integer> sinks = findAllSinks();
		
		// If no sinks were found, return false.
		if (sinks.isEmpty())
			return false;
		
		// Remove all incoming edges to the sink.
		for (int i = 0; i < this.adjacencyList.size(); i++)
			this.adjacencyList.get(i).remove(sinks.get(0));
		
		// Remove the sink from the adjacency list.
		this.adjacencyList.get(sinks.get(0)).clear();
		
		return true;
	}
	
	private boolean findCyclesUtil(int vertex, boolean[] visited, boolean[] recursiveStack) {
		// Mark the current node as visited and
        // part of recursion stack
        if (recursiveStack[vertex])
            return true;
 
        if (visited[vertex])
            return false;
             
        visited[vertex] = true;
        recursiveStack[vertex] = true;
        
        List<Integer> children = this.adjacencyList.get(vertex);
        
        for (Integer c: children)
            if (findCyclesUtil(c, visited, recursiveStack)) {
            	System.out.print(vertex + " <- ");
            	return true;
            }
                 
        recursiveStack[vertex] = false;
 
        return false;
	}
	
	public boolean isAcyclic() {
		// Mark all the vertices as not visited and
        // not part of recursion stack
        boolean[] visited = new boolean[this.adjacencyList.size()];
        boolean[] recursiveStack = new boolean[this.adjacencyList.size()];
		// Perform a DFS from each vertex in the graph.
		for (int i = 0; i < this.adjacencyList.size(); i++) {
			if (findCyclesUtil(i, visited, recursiveStack)) {
				// A cycle was found, so the graph is not acyclic.
				System.out.println("cycle exists");
				System.out.println();
				return false;
			}
		}
		
		// No cycles were found, so the graph is acyclic.
		return true;
	}
	
	public boolean isEmpty() {
		// Check if the adjacency list is empty.
		if (this.adjacencyList.isEmpty()) 
			return true;
		
		// Check if any vertices have outgoing edges.
		for (var edges : this.adjacencyList) 
			if (!edges.isEmpty())
				return false;
		
		// If the adjacency list is not empty and all vertices have no outgoing edges,the graph is empty.
		return true;
	}
	
	// Method to print the adjacency list.
	public void printAdjacencyList() {
		for (int i = 0; i< this.adjacencyList.size(); i++) {
			System.out.print(i + " --> ");
			for (int j: this.adjacencyList.get(i)) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
	
}
