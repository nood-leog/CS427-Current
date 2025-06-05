//Node.java

class Node implements Comparable<Node>
{
    int vertex;
    int weight; //used to represent edge weight or distance from source

    //constructor
    public Node(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }

    //compare based on weight
    public int compareTo(Node other)
    {
        return Integer.compare(this.weight, other.weight);
    }

    //toString for format
    public String toString()
    {
        return "(" + vertex + ", " + weight + ")";
    }
}
