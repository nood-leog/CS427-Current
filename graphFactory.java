//graphFactory.java
//Alex Boyce
//CS427 Final Project

import java.util.*;

//should have all static methods
public class graphFactory
{
    //generates a random undirected graph with a given number of vertices and edge density
    //returns a GraphInfo object that contains the new generated graph and the actual number of edges
    public static GraphInfo buildGraph(int numVertices, double density)
    {
        graph g = new graph(numVertices); //new empty graph instance with the specified number of vertices
        Random rand = new Random(); //random number generator to be used for picking vertices and weights

        //HashSet to keep track of the edges we have already added thus preventing adding duplicate edges like 1-2 then 2-1
        Set<String> existingEdges = new HashSet<>();

        long maxEdges = (long) numVertices * (numVertices - 1) / 2; //calculate max num of edges using the formula: n * (n-1) / 2
        long targetEdges = (long) (density * maxEdges);  //calculate goal num of edges
        int edgeCount = 0; //counter for edges

        //keep adding edges until we reach the target number
        while (edgeCount < targetEdges && existingEdges.size() < maxEdges)
        {
            //pick 2 random vertices
            int u = rand.nextInt(numVertices);
            int v = rand.nextInt(numVertices);

            //make sure they are not the same
            if (u == v) {continue;}

            //make an edge key that avoids duplicates by always putting the smaller vertex first
            String edgeKey = (u < v) ? u + "-" + v : v + "-" + u;

            //check if this edge key already exists in our set
            if (!existingEdges.contains(edgeKey))
            {
                int weight = 1 + rand.nextInt(100); //weight between 1 and 100
                g.addEdge(u, v, weight); //add new edge to the graph
                existingEdges.add(edgeKey); //add to set
                edgeCount++; //counter
            }
        }
        //return the graph
        return new GraphInfo(g, edgeCount);
    }
}

//helper class to hold the results from the graph factory
class GraphInfo
{
    graph graph;
    int edgeCount;

    GraphInfo(graph g, int e)
    {
        this.graph = g;
        this.edgeCount = e;
    }
}