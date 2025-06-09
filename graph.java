//graph.java
//Alex Boyce
//CS427 Final Project

import java.util.*;

//graph class
public class graph
{
    public static final int INFINITY = Integer.MAX_VALUE;

    int nVertices;
    int[][] adjMatrix;
    List<List<Node>> adjList;

    //constructor
    public graph(int nVertices)
    {
        //vertices
        this.nVertices = nVertices;

        //initialize adjacency matrix
        adjMatrix = new int[nVertices][nVertices];
        for (int i = 0; i < nVertices; i++)
        {
            Arrays.fill(adjMatrix[i], INFINITY);
            adjMatrix[i][i] = 0; // Distance to self is 0
        }

        //initialize adjacency list
        adjList = new ArrayList<>(nVertices);
        for (int i = 0; i < nVertices; i++)
        {
            adjList.add(new ArrayList<>());
        }
    }

    //method to add an edge
    public void addEdge(int u, int v, int weight)
    {
        //adjacency Matrix
        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight;

        //adjacency List
        adjList.get(u).add(new Node(v, weight));
        adjList.get(v).add(new Node(u, weight));
    }

    //Dijkstra's algorithm using adjacency matrix
    public int[] dijkstraAdjMatrix(int startVertex)
    {

        //check if the start vertex is within bounds of the graph
        if (startVertex < 0 || startVertex >= nVertices)
        {
            throw new IllegalArgumentException("Source vertex out of bounds.");
        }

        //initialize distance array and visited array
        int[] dist = new int[nVertices];
        boolean[] visited = new boolean[nVertices]; //to keep track of visited vertices

        //set all distances to infinity except the start vertex
        Arrays.fill(dist, INFINITY);
        dist[startVertex] = 0; //start vertex 0

        //for each vertex, find the minimum distance vertex
        for (int count = 0; count < nVertices; count++) //loop through all vertices - outer loop
        {
            int u = -1; //initialize u to -1, representing no vertex selection
            int minDistance = INFINITY; //set minDistance to infinity

            for (int v = 0; v < nVertices; v++) //loop through all vertices to find the minimum distance vertex
            {
                if (!visited[v] && dist[v] < minDistance) //check if vertex has been visited and if its distance is less than the current minimum distance
                {
                    minDistance = dist[v];  //update minimum distance
                    u = v; //set u to the current vertex
                }
            }

            if (u == -1) break; //if no vertex is found, break the loop

            visited[u] = true; //mark the vertex as visited

            //update distances for all neighbors of u
            for (int vNeighbor = 0; vNeighbor < nVertices; vNeighbor++)
            {
                //check if there is an edge between u and vNeighbor, if vNeighbor has not been visited, and if the distance to u is not infinity
                if (adjMatrix[u][vNeighbor] != INFINITY && !visited[vNeighbor] && dist[u] != INFINITY)
                {
                    //if so, update the distance to vNeighbor if the new distance is less than the current distance
                    if (dist[u] + adjMatrix[u][vNeighbor] < dist[vNeighbor])
                    {
                        dist[vNeighbor] = dist[u] + adjMatrix[u][vNeighbor]; //update the distance to vNeighbor
                    }
                }
            }
        }
        return dist; //return the distance array
    }

    //Dijkstra's algorithm using adjacency list with min-heap
    public int[] dijkstraAdjListHeap(int startVertex)
    {
        int[] dist = new int[nVertices]; //initialize distance array
        Arrays.fill(dist, INFINITY); //fill distance array to infinity
        dist[startVertex] = 0; //set the distance to the start vertex to 0

        PriorityQueue<Node> pq = new PriorityQueue<>(); //initialize min-heap
        pq.add(new Node(startVertex, 0)); //add the start vertex to the min-heap with distance 0

        while (!pq.isEmpty()) //while the min-heap is not empty
        {
            Node currentNode = pq.poll(); //remove the node with the minimum distance from the min-heap
            int u = currentNode.vertex; //get the vertex of the current node
            int d_u = currentNode.weight; //get the distance of the current node

            if (d_u > dist[u]) continue; //if the distance of the current node is greater than the distance in the distance array, skip it

            for (Node neighborEdge : adjList.get(u)) //loop through all neighbors of the current vertex
            {
                int v_neighbor = neighborEdge.vertex; //set the neighbor vertex
                int weight_uv = neighborEdge.weight; //set the weight of the edge between u and v_neighbor

                if (dist[u] != INFINITY && dist[u] + weight_uv < dist[v_neighbor]) //check if the distance to u is not infinity and if the new distance to v_neighbor is less than the current distance
                {
                    //if so, update the distance to v_neighbor
                    dist[v_neighbor] = dist[u] + weight_uv; //update the distance to v_neighbor
                    pq.add(new Node(v_neighbor, dist[v_neighbor])); //add the neighbor to the min-heap with the updated distance
                }
            }
        }
        return dist; //return the distance array
    }
}