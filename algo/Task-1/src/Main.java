public class Main {

	public static void main(String[] args) {
		
		Graph graph = new Graph(5);
		graph.parseInputFile("input.txt");
		System.out.println("**Adjacency List of given Graph**");
		System.out.println();
		graph.printAdjacencyList();
		System.out.println();
		System.out.println("All Sinks: " + graph.findAllSinks());
		System.out.println();
		System.out.println("Is Acyclic? " + graph.isAcyclic());
		
	}
	
}
