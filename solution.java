//solution.java
//Alex Boyce
//CS427 Final Project

//TODO
//Compare performance by running both implementations on various test cases and measuring their execution times
//Build test cases with different graph structures (dense vs sparse) AND sizes to see how each implementation scales


public class solution
{
  public static void main(String[] args)
  {
    //values for the graph - Change these to test program
    int numVertices = 6;
    int sourceVertex = 0;
    int[][] undirectedEdges = {
            {0, 1, 7}, {0, 2, 9}, {0, 5, 14},
            {1, 2, 10}, {1, 3, 15},
            {2, 3, 11}, {2, 5, 2},
            {3, 4, 6},
            {4, 5, 9}
    };

    graph myGraph = new graph(numVertices); //new instance of the graph class in graph.java
    //build the graph with the edges
    for (int[] edge : undirectedEdges)
    {
      myGraph.addEdge(edge[0], edge[1], edge[2]);
    }

    //print the graph
    System.out.println("\nGraph where n = " + numVertices);
    myGraph.printAdjMatrix(); //print adjacency matrix
    myGraph.printAdjList(); //print adjacency list

    System.out.println("\n==== Dijkstra with Adjacency Matrix ====");

    int[] distancesMatrix = myGraph.dijkstraAdjMatrix(sourceVertex);
    printDistances(distancesMatrix, sourceVertex, "Adjacency Matrix", numVertices);

    System.out.println("\n==== Dijkstra with Adjacency List ====");

    int[] distancesListHeap = myGraph.dijkstraAdjListHeap(sourceVertex);
    printDistances(distancesListHeap, sourceVertex, "Adjacency List + Min-Heap", numVertices);

  }

  //helper function to print the calculated distance
  public static void printDistances(int[] distances, int source, String algorithmName, int numVertices)
  {
    System.out.println("\nShortest distances from source " + source + " using " + algorithmName + ":");
    for (int i = 0; i < numVertices; i++)
    {
      //if the distance is infinity, print unreachable
      if (distances[i] == graph.INFINITY)
      {
        System.out.println("Vertex " + i + ": Unreachable");
      }
      else //otherwise just print the distance as is
      {
        System.out.println("Vertex " + i + ": " + distances[i]);
      }
    }
  }
}