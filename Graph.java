package A3;

import java.util.HashSet;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Graph<T extends Comparable<? super T>> {
	
	public int[][] Graph;
	public int[][] minimalSpanningTree;
	public String[] nodeNames;
	public int size;
	public boolean isConnected = false;
	public int nodeIndex = 0;
	
	public Graph(int size) //Create the graph with the node names
	{
		this.Graph = new int[size][size];
		this.size = size;
		/*Starting at 0,0 we populate the graph with zeros, 
		 * this represents no connectivity.*/
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				this.Graph[i][j] = 0;
		}
		this.nodeNames = new String[size];
		nodeIndex = 0;
	}
	public boolean addName(String Name){
		/*If the new nodes index is bigger than the size 
		 * of the array, we can't add so return false */
		if(this.nodeIndex > this.size)
			return false;
		/* Otherwise, we should have no problem adding*/
		this.nodeNames[nodeIndex] = Name;
		nodeIndex++;
		
		/* This will make the main diagonal of the matrix non-zero*/
		this.Graph[nodeIndex - 1][nodeIndex - 1] = 1;
		/* Which will help when we are determining connectivity later*/
		return true;
	}
	
	public void namesToString()	
	{ /* Prints out the names of the nodes in the graph.*/
		for (int i = 0; i < size; i++)
		{
			if(nodeNames[i] != null)	
				System.out.print("[" + i + "] is " + nodeNames[i] + ", ");
			else
				System.out.print("[" + i + "] is null, ");
		}
		System.out.print("\n");
	}
	
	public int findNodeName(String name){
		/* If the name is found, return the corresponding index.
		 * Else, return -1.*/
		for (int i = 0; i < size; i++)
		{
			if (nodeNames[i] != null && nodeNames[i].equals(name))
				return i;
		}
		return -1;
	}
	
	public boolean addEdge(String node1, String node2, int weight) {
		int rowIndex = this.findNodeName(node1);
		int columnIndex = this.findNodeName(node2);
		/*When we find the indices then we know where we should
		 * add the weight into the matrix. Since the matrix is
		 * symmetric then we will add it on both sides of the 
		 * main diagonal.*/
		if ((rowIndex != -1) && (columnIndex != -1))
		//If both indices exist, then add the weight.
		{
			Graph[rowIndex][columnIndex] = weight;
			Graph[columnIndex][rowIndex] = weight; 
			return true;
		}
		return false;
	}
	
	public void edgesPrint() {
		for (int i = 0; i < size; i++)
		/* Iterates through the matrix, and prints out the nodes, and weights.*/
		{
			for (int j = 0; j < size; j++)
			{
				if (Graph[i][j] != 0)
					System.out.print("[" + i + "]" + "[" + j + "] = " + this.Graph[i][j] + " ");
			}
		}
		System.out.print(" ");
	}
	
	public boolean Connected() {
		/* If the given graph passes the connected test,
		 * then it is connected and return true. Else,
		 * return false. */
		if (this.ConnectedTest())
			return true;
		return false;	
	}
	
	public boolean ConnectedTest(){
		/* This checks a current node to make sure that it is 
		 * connected and that it has not been previously been
		 * visited before pushing it onto the stack*/
		if (nodeNames[0] == null || nodeNames == null)
			return false;
		Stack<Integer> connectedStack = new Stack<Integer>();
		
		boolean[] isVisited = new boolean[this.size];
		isVisited[0] = true;
		int current = 0;
		connectedStack.push(current);

		while(!connectedStack.isEmpty())
		{
			current = connectedStack.pop();
			for (int i = 0; i < this.size; i++)
			{
				if(Graph[current][i] != 0)
				{
					if(!isVisited[i])
					{
						isVisited[i] = true;
						connectedStack.push(i);
					}// end if(!isVisited[i])
				}// end if(Graph[current][i] != 0)
			}//end for (int i = 0; i < size; i++)
		}// end while(!connectedStack.isEmpty())
		for (int i = 0; i < size; i++)
		{
			if (!isVisited[i])
				return false;
		}
		return true;
	}
	
	public boolean BreadthFirstTraversal(String node1, String node2) {
		ArrayBlockingQueue<String> bftQueue = new ArrayBlockingQueue<String>(this.size);
		HashSet<String> visitedNodes = new HashSet<String>();
		String placeHolder;

		int start = this.findNodeName(node1);
		if (start == -1) 
			return false; 
		/* The node doesn't exist in the graph.*/
		bftQueue.add(nodeNames[start]);
		visitedNodes.add(nodeNames[start]);
		while (!bftQueue.isEmpty()) {
			placeHolder = bftQueue.remove();
			System.out.print(placeHolder + " ");
			if (placeHolder.equals(node2))
				return true;

			/* If we didn't find the node we were looking for,
			 * then we need to find it's edges and traverse
			 * the nodes attached to them as well.
			 *  We only need to bother with nodes we haven't 
			 *  visited yet.*/
			int temp = this.findNodeName(placeHolder);
			for (int i = 0; i < this.size; i++) {
				if (this.Graph[temp][i] != 0)
					if (visitedNodes.add(nodeNames[i]))
						bftQueue.add(nodeNames[i]);
			}// end for (int i = 0; i < this.size; i++);
		}// end while (!bftQueue.isEmpty())	
		return false;
	}

	public boolean depthFirstTraversal(String node1, String node2) {
		Stack<String> dftStack = new Stack<String>();
		HashSet<String> visitedNodes = new HashSet<String>();
		String placeHolder;

		int start = this.findNodeName(node1);
		if (start == -1) 
			return false; 
		/*The node doesn't exist in the graph.*/
		dftStack.push(nodeNames[start]);

		while(!dftStack.isEmpty())
		{
			placeHolder = dftStack.pop();
			System.out.print(placeHolder + " ");
			if (placeHolder.equals(node2))
				return true;
			/* If we didn't find the node we were looking for,
			 * then we need to find it's edges and traverse
			 * the nodes attached to them as well.
			 *  We only need to bother with nodes we haven't 
			 *  visited yet.*/
			int temp = this.findNodeName(placeHolder);
			for (int i = 0; i < this.size; i++) {
				if (this.Graph[temp][i] != 0)
					if (visitedNodes.add(nodeNames[i]))
						dftStack.push(nodeNames[i]);
			}// end for (int i = 0; i < this.size; i++);
		}// end while(!dftStack.isEmpty())
		return false;
	}
	
	public void minSpanningTree() {
		int[][] matrixCopy = new int[this.size][this.size];
		int minWeight = 0;
		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size; j++)
				matrixCopy[i][j] = this.Graph[i][j];
		
		int[] nodes = new int[this.size];
		int[][] minSTree = new int[this.size][this.size];
		int[] minimal = new int[3];
		int index = 0;
		nodes[0] = 0;
		index++;
		for (int i = 0; i < this.size; i++)
			matrixCopy[i][0] = 0;
		while (index < this.size)
		{
			minimal = mstHelper(nodes, matrixCopy, index);
			for (int j = 0; j < this.size; j++)
				matrixCopy[j][minimal[1]] = 0;
			/* Set the node in the MST, keeping in mind
			 * that the matrix is symmetric*/
			minSTree[minimal[0]][minimal[1]] = minimal[2];
			minSTree[minimal[1]][minimal[0]] = minimal[2];
			nodes[index-1] = 0;
			index++;
		}// end while(index < this.size)
		minimalSpanningTree = minSTree;
		
		//COMMENCE PRINTING >:D
		for (int i = 0; i < this.size; i++)
		{
			for (int j = 0; j < this.size; j++)
				if (minSTree[i][j] != 0){
					System.out.print("[" + i +"][" + j + "] = " + minSTree[i][j] + " ");
					minWeight += minSTree[i][j];
					}
		}// end for(int i = 0; i < this.size; i++)
		System.out.print("\n");
		System.out.println("Minimal spanning tree weight is " + minWeight + ".");
		
	}// end public void minSpanningTree()
	
	public int[] mstHelper(int[] nodes, int[][] matrix, int index) {
		int[] minimum = new int[3];
		minimum[2] = 0;
		int temp = 0;
		int indexCount = 0;
		/* While you still have things to iterate through, 
		 * set the first thing we look at to the minimum,
		 * then on every iteration through we compare
		 * the values to see if we get a new minimum.*/
		while (indexCount < index)
		{
			temp = nodes[indexCount];
			for (int i = 0; i < this.size; i++)
			{
				if (matrix[temp][i] > 0){
					if (minimum[2] == 0){
						minimum[0] = temp;
						minimum[1] = i;
						minimum[2] = matrix[temp][i];
					}// end if(minimum[2] == 0)
					if (matrix[temp][i] < minimum[2])
					{
						minimum[0] = temp;
						minimum[1] = i;
						minimum[2] = matrix[temp][i];
					}// end if(matrix[temp][i] < minimum[2])
				}// end if(matrix[temp][i] > 0)
			}// end for(int i = 0; i< this.size; i++)
			indexCount++;
			if (indexCount < index)
				temp = nodes[indexCount];
		}
		return minimum;
	}
// This function is commented out because it doesn't work D:	
//	public boolean shortestPath(String node1, String node2) {
//		ArrayBlockingQueue<String> bftQueue = new ArrayBlockingQueue<String>(this.size);
//		HashSet<String> visitedNodes = new HashSet<String>();
//		String placeHolder;
//
//		int start = this.findNodeName(node1);
//		if (start == -1) //The node doesn't exist in the graph.
//			return false; 
//
//		bftQueue.add(nodeNames[start]);
//		visitedNodes.add(nodeNames[start]);
//		while (!bftQueue.isEmpty()) {
//			placeHolder = bftQueue.remove();
//			System.out.print(placeHolder + " ");
//			if (placeHolder.equals(node2))
//				return true;
//
//			/* If we didn't find the node we were looking for,
//			 * then we need to find it's edges and traverse
//			 * the nodes attached to them as well.
//			 *  We only need to bother with nodes we haven't 
//			 *  visited yet.*/
//			int temp = this.findNodeName(placeHolder);
//			for (int i = 0; i < this.size; i++) {
//				/* Insert some correct code in this area*/
//				if (this.Graph[temp][i] != 0)
//					if (visitedNodes.add(nodeNames[i]))
//						bftQueue.add(nodeNames[i]);
//			}// end for (int i = 0; i < this.size; i++);
//		}// end while (!bftQueue.isEmpty())	
//		return false;
//
//	}// end shortestPath function

}

