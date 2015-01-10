package A3;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {

	public static void main(String[] args) throws Exception{
		Scanner nodeFile = new Scanner(new File("C:\\Users\\Samantha\\workspace\\Assignment3\\src\\nodes.txt"));
		Scanner edgeFile = new Scanner(new File("C:\\Users\\Samantha\\workspace\\Assignment3\\src\\edges1.txt"));
		//Scanner edgeFile = new Scanner(new File("C:\\Users\\Samantha\\workspace\\Assignment3\\src\\edges2.txt"));
		String input;
		StringTokenizer operationsLine;
		StringTokenizer line;
		int counter = 0;
		Graph nodeGraph = new Graph(50);
		
		while (nodeFile.hasNext()){
			String nodeName = nodeFile.nextLine();
			nodeGraph.addName(nodeName);
			counter++;
		}
		System.out.println("There were "+ counter + " correct records found.");
		nodeGraph.namesToString();
		
		while (edgeFile.hasNext()){
			input = edgeFile.nextLine();
			line = new StringTokenizer(input);
			String node1 = line.nextToken();
			String node2 = line.nextToken();
			int weight = Integer.parseInt(line.nextToken());
			nodeGraph.addEdge(node1, node2, weight);
		}
		nodeGraph.edgesPrint();
		System.out.println("-----------------------------");
		edgeFile.close();
		
		Scanner operationsFile = new Scanner(new File("C:\\Users\\Samantha\\workspace\\Assignment3\\src\\operations.txt"));
		while (operationsFile.hasNext())
		{
			input = operationsFile.nextLine();
			operationsLine = new StringTokenizer(input);
			String operation = operationsLine.nextToken().trim();
			String node1 = null;
			String node2 = null;
			if (operationsLine.hasMoreTokens()){
				node1 = operationsLine.nextToken();
				node2 = operationsLine.nextToken();
				if (!operationsLine.hasMoreTokens() && node2.length() != 3 && node1.length() != 3)
				{
					System.out.println("Incorrectly formed operation.");
					node2 = null;
					node1 = null;
				}
			}
				if (operation.equals("B"))
				{
					if (node1 != null && node2 != null){
						System.out.println("Breadth-first search starting, " + node1 + " " + node2);
						if (nodeGraph.BreadthFirstTraversal(node1, node2) == true)
							System.out.println("The node " + node2 + "was found.");
					}
				}
				else if (operation.equals("C"))
				{
					if(nodeGraph.Connected() == true)
						System.out.println("The graph is connected.");
					else
						System.out.println("The graph is not connected");
				}
				else if (operation.equals("D"))
				{
					if (node1 != null && node2 != null)
						System.out.println("Depth-first search starting, " + node1 + " " + node2);
						if (nodeGraph.depthFirstTraversal(node1, node2) == true)
							System.out.println("The node " + node2 + " was found.");
				}
				else if (operation.equals("L"))
				{
				//	nodeGraph.longestPath(node1, node2);
				}
				else if (operation.equals("S"))
				{
				//	nodeGraph.shortestPath(node1, node2);
				}
				else if (operation.equals("T"))
				{
					System.out.print("Minimal spanning tree: ");
					nodeGraph.minSpanningTree();
				}
				else
					System.out.println("Incorrectly formed record.");
			
		}// end while (operationsFile.hasNext())
		operationsFile.close();
	}// end main function
}
