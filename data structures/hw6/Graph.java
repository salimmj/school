import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Graph {

  // Keep a fast index to nodes in the map
  private Map<Integer, Vertex> vertexNames;

  /**
   * Construct an empty Graph with a map. The map's key is the name of a vertex
   * and the map's value is the vertex object.
   */
  public Graph() {
    vertexNames = new HashMap<>();
  }

  /**
   * Adds a vertex to the graph. Throws IllegalArgumentException if two vertices
   * with the same name are added.
   * 
   * @param v
   *          (Vertex) vertex to be added to the graph
   */
  public void addVertex(Vertex v) {
    if (vertexNames.containsKey(v.name))
      throw new IllegalArgumentException("Cannot create new vertex with existing name.");
    vertexNames.put(v.name, v);
  }

  /**
   * Gets a collection of all the vertices in the graph
   * 
   * @return (Collection<Vertex>) collection of all the vertices in the graph
   */
  public Collection<Vertex> getVertices() {
    return vertexNames.values();
  }

  /**
   * Gets the vertex object with the given name
   * 
   * @param name
   *          (String) name of the vertex object requested
   * @return (Vertex) vertex object associated with the name
   */
  public Vertex getVertex(int name) {
    return vertexNames.get(name);
  }

  /**
   * Adds a directed edge from vertex u to vertex v
   * 
   * @param nameU
   *          (String) name of vertex u
   * @param nameV
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addEdge(int nameU, int nameV, Double cost) {
    if (!vertexNames.containsKey(nameU))
      throw new IllegalArgumentException(nameU + " does not exist. Cannot create edge.");
    if (!vertexNames.containsKey(nameV))
      throw new IllegalArgumentException(nameV + " does not exist. Cannot create edge.");
    Vertex sourceVertex = vertexNames.get(nameU);
    Vertex targetVertex = vertexNames.get(nameV);
    Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
    sourceVertex.addEdge(newEdge);
  }

  /**
   * Adds an undirected edge between vertex u and vertex v by adding a directed
   * edge from u to v, then a directed edge from v to u
   * 
   * @param name
   *          (String) name of vertex u
   * @param name2
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addUndirectedEdge(int name, int name2, double cost) {
    addEdge(name, name2, cost);
    addEdge(name2, name, cost);
  }


  /**
   * Computes the euclidean distance between two points as described by their
   * coordinates
   * 
   * @param ux
   *          (double) x coordinate of point u
   * @param uy
   *          (double) y coordinate of point u
   * @param vx
   *          (double) x coordinate of point v
   * @param vy
   *          (double) y coordinate of point v
   * @return (double) distance between the two points
   */
  public double computeEuclideanDistance(double ux, double uy, double vx, double vy) {
    return Math.sqrt(Math.pow(ux - vx, 2) + Math.pow(uy - vy, 2));
  }

  /**
   * Computes euclidean distance between two vertices as described by their
   * coordinates
   * 
   * @param u
   *          (Vertex) vertex u
   * @param v
   *          (Vertex) vertex v
   * @return (double) distance between two vertices
   */
  public double computeEuclideanDistance(Vertex u, Vertex v) {
    return computeEuclideanDistance(u.x, u.y, v.x, v.y);
  }

  /**
   * Calculates the euclidean distance for all edges in the map using the
   * computeEuclideanCost method.
   */
  public void computeAllEuclideanDistances() {
    for (Vertex u : getVertices())
      for (Edge uv : u.adjacentEdges) {
        Vertex v = uv.target;
        uv.distance = computeEuclideanDistance(u.x, u.y, v.x, v.y);
      }
  }



  // STUDENT CODE STARTS HERE

  public void generateRandomVertices(int n) {

    this.vertexNames = new HashMap<>(); // reset the vertex hashmap

    Random r = new Random();

    // making vertices
    for(int i = 0; i < n; i++){


      // coordinates between 0 and 100
      int x = (int) (r.nextFloat()*100);
      int y = (int) (r.nextFloat()*100);


      // putting the vertex in the HashMap
      vertexNames.put(i, new Vertex(i, x, y));


      // making the graph complete

      if(i != 0){


        for(int j = 0; j < i; j++){


          addUndirectedEdge(j, i, 0.0);


        }
      }
    }
    computeAllEuclideanDistances(); // compute distances
  }

  // method returns the min of adjacent edges not yet visited
  private Edge getMinEdge(ArrayList<Vertex> toVisit, Vertex u){

    //edge will update
    Edge min = new Edge(null, null, Double.MAX_VALUE);


    // comparing the edge to all edges to find smallest
    for(Edge e : u.adjacentEdges){

      if(toVisit.contains(e.target) && e.distance < min.distance){
        min = e;
      }
    }


    return min;
  }


  public List<Edge> nearestNeighborTsp() {

    //we re returning a linkedlist
    LinkedList<Edge> perfPath = new LinkedList<>();

    double perfCost = Double.MAX_VALUE;

    LinkedList<Edge> path;
    ArrayList<Vertex> unknown;


    for(int st = 0; st < vertexNames.size(); st++){

      path = new LinkedList<>();
      unknown = new ArrayList<>(getVertices());

      double cost = 0.0;

      //new start
      Vertex stv = getVertex(st);
      unknown.remove(stv);
      Vertex nextV = stv;

      // go to all unknown vertices
      while(unknown.size() > 0) {



        // getting the smallest adjacent edge of nextVertex
        Edge minEdge = getMinEdge(unknown, nextV);

        unknown.remove(minEdge.target);

        cost += minEdge.distance;

        path.add(minEdge);

        nextV = minEdge.target;
      }

      //close cycle
      Edge cc = new Edge(nextV, stv, computeEuclideanDistance(nextV, stv));
      cost += cc.distance;
      path.add(cc);

      // if this path is better than the last cost we update it
      if(cost < perfCost){
        perfPath = new LinkedList<>();
        perfCost = cost;
        for(Edge e : path){
          perfPath.addLast(e);
        }
      }
    }

    System.out.println("Best Path: " + perfPath);
    System.out.println("Cost: " + perfCost);

    return perfPath;

  }

  private int[] BFPath;
  private double BFCost;

  //we first make a path

  public List<Edge> bruteForceTsp() {

    int n = vertexNames.size();

    ArrayList<Edge> path = new ArrayList<>();

    // initializing the best path and best cost
    BFPath = new int[n + 1];
    BFCost = Double.MAX_VALUE;

    // creating an initial permutation
    int[] permutation = new int[n];
    for(int i = 0; i < n; i++)
      permutation[i] = i;

    // running the recursive tryAll method
    tryAll(permutation, 0, n - 1);

    // creating the shortest path
    for(int i = 0; i < BFPath.length - 1; i++) {
      Vertex u = getVertex(BFPath[i]);
      Vertex v = getVertex(BFPath[i + 1]);
      path.add(new Edge(u, v, computeEuclideanDistance(u, v)));
    }

    // printing the path and cost
    System.out.println("Path: " + path);
    System.out.println("Cost: " + this.BFCost);

    return path;

  }

  // recursive method to find the best path with brute force
  private void tryAll(int[] comb, int start, int fin) {

    //base case
    if (start == fin) {


      double Coost = 0.0;


      for(int name = 0; name < comb.length - 1; name++){

        Coost += computeEuclideanDistance(getVertex(comb[name]), getVertex(comb[name + 1]));

      }

      int n = comb.length;
      Coost += computeEuclideanDistance(getVertex(comb[n - 1]), getVertex(comb[0]));

      // update the best cost and the best path
      if(Coost < BFCost){
        BFPath[comb.length] = comb[0];


        for(int i = 0; i < comb.length; i++){
          BFPath[i] = comb[i];
        }


        BFCost = Coost;
      }
    } else {

      // all vertices
      for(int i = start; i < fin + 1; i++) {


        comb = switchh(start, i, comb);
        tryAll(comb, start + 1, fin);
        comb = switchh(start, i, comb);

      }
    }
  }

  // helper method to switch spots in array
  private int[] switchh(int i, int j, int[] ar){

    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
    return ar;
  }

  // STUDENT CODE ENDS HERE



  /**
   * Prints out the adjacency list of the graph for debugging
   */
  public void printAdjacencyList() {
    for (int u : vertexNames.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertexNames.get(u).adjacentEdges) {
        sb.append(e.target.name);
        sb.append("(");
        sb.append(e.distance);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }
}
