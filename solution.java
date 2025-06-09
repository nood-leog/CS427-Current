// solution.java
//Alex Boyce
//CS427 Final Project

public class solution
{
  public static void main(String[] args)
  {
    //test cases
    // {numVertices, density}
    double[][] testConfigs = {
            //small graphs
            {50, 0.1},    //sparse
            {50, 0.8},    //dense

            //medium graph
            {250, 0.05},  //sparse
            {250, 0.6},   //dense

            //large graph
            {1000, 0.01}, //really sparse
            {1000, 0.5},  //dense

            //mega graph
            {2000, 0.005}, //really sparse
            {2000, 0.4}    // Dense
    };

    //table header for results
    System.out.println("--------------------------------------------------------------------------------------------------");
    //cool way to format text, use later
    System.out.printf("%-15s | %-10s | %-12s | %-25s | %-30s\n",
            "Graph Type", "Vertices", "Edges", "Adj. Matrix Time (ms)", "Adj. List + Min-Heap Time (ms)");
    System.out.println("--------------------------------------------------------------------------------------------------");

    //loop thru each test config
    for (double[] config : testConfigs)
    {
      int numVertices = (int) config[0]; //cast the vertex count back to an int from a double
      double density = config[1];        //single out the density element of the current config
      runBenchmark(numVertices, density); //run the benchmark with the params
    }

    System.out.println("--------------------------------------------------------------------------------------------------");
  }

  //runBenchmark method runs a single benchmark for a given graph configuration
  //calls graphFactory to produce a random undirected graph with the given params numVertices and density
  public static void runBenchmark(int numVertices, double density)
  {
    //generate the graph
    GraphInfo generatedGraphInfo = graphFactory.buildGraph(numVertices, density); //pass params to graphFactory
    graph myGraph = generatedGraphInfo.graph;
    int actualEdges = generatedGraphInfo.edgeCount; //get the edge count
    int sourceVertex = 0; //fixed source for all tests

    //IMPORTANT!!!!!
    //Benchmark Dijkstra with Adjacency Matrix
    long startTimeMatrix = System.nanoTime(); //start timer
    myGraph.dijkstraAdjMatrix(sourceVertex); //call the dijkstraAdjMatrix from graph class passing the source vertex
    long endTimeMatrix = System.nanoTime(); //end timer
    long durationMatrix = (endTimeMatrix - startTimeMatrix) / 1_000_000; //convert to ms

    //IMPORTANT!!!!!
    //Benchmark Dijkstra with Adjacency List + Min-Heap
    long startTimeList = System.nanoTime(); //start timer
    myGraph.dijkstraAdjListHeap(sourceVertex); //call the dijkstraAdjListHeap from graph class passing the source vertex
    long endTimeList = System.nanoTime(); //end timer
    long durationList = (endTimeList - startTimeList) / 1_000_000; //convert to ms

    //print the results
    String graphType = (density < 0.2) ? "Sparse" : "Dense";
    System.out.printf("%-15s | %-10d | %-12d | %-25d | %-30d\n",
            graphType, numVertices, actualEdges, durationMatrix, durationList);
  }
}