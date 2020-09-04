import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.NoSuchElementException;

import javax.xml.transform.SourceLocator;

/**
 * This class is an implementation of a Directed Graph Using structures in the java.uitl package. This graph grows and shrinks
 * depending on what you do(add vertices\remove vertices).
 * @param <V> a generic object that will serve as a vertix in the graph
 * @param <E> a genric object that will serve as a edge in the graph
 */
class ThreeTenGraph<V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent>
	implements Graph<V, E>, DirectedGraph<V, E> {
	//We promise to never try to add a node with an id smaller/bigger than this!
	//But IDs for edges may be any int value (including negative numbers).
	/**
	 * The minimum number the ID of a V node can be.
	 */
	private static final int MIN_NODE_ID = 0;
	/**
	 * The maximum number the ID of a V node can be.
	 */
	private static final int MAX_NODE_ID = 200;
	
	/* +++++++++++++++++++ YOUR CODE HERE +++++++++++++++++++ */
	//Add additional instance variables here!
	//You may also add additional _private_ methods if you'd like.
	/**
	 * A collection of vertices that is stored to keep track of the V's in the graph.
	 */
	private ArrayList<V> vertices;
	/**
	 * A 2d Arraylist that will work as a ajancency matrix to store the graph and keep
	 * track of edges and where there located based on their index location. If
	 * edge[x][y], x represents a vertex in the collection vertices at vertices.get(x)
	 * and y represent where the edge is pointed to, therefore vertices.get(y).
	 */
	private ArrayList<ArrayList<E>> edge;
	/**
	 * a Collection the stores coordinates to all the edges in the graph, the coordinates 
	 * are parallel to the edges in the edgeAtCord Collection.
	 */
	private ArrayList<Integer[]> edgeCord;
	/**
	 * A collection of all the Edges in the graph. the index of the edges are parallel
	 * to the indexs in edgeCord collection.
	 */
	private ArrayList<E> edgeAtCord;
	/**
	 * The size of the initial graph which in this case will be a 
	 * 10 x 10 graph that can keep track of 10 vertices and their edges.
	 * If more vertices are added. graph will expand 2x.
	 */
	private int matrixSize = 10;
	/**
	 * the number of vertices in the graph.
	 */
	private int numV = 0;
	
	
	//**************** IMPORTANT WARNING ****************
	//Due to Java complexities with bounded genics that it would be difficult to explain here,
	//if you want an array of V[] or E[], the following format ___SHOULD NOT___ be used:
	//         V[] items = (V[]) Object[10];
	//instead, use this format:
	//         V[] items = (V[]) ThreeTenGraphComponent[10];
	
	
	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {
		
		vertices = new ArrayList<V>();
		edge = new ArrayList<ArrayList<E>>();
		edgeAtCord = new ArrayList<E>();
		for(int i = 0; i < matrixSize; i++){
			ArrayList<E> column = new ArrayList<E>();
			int count = 0;
			while(count < matrixSize){
				column.add(null);
				count++;
			}
			edge.add(column);
		}
		edgeCord = new ArrayList<Integer[]>();
		
		//Please read the warning in the above section on arrays with bounded generics.
	}
	/**
	* a Private method used to display the current graph for testing purposes.
	*/
	private void matrix(){
		for(int i = 0; i < matrixSize; i++){
            System.out.println("\n");
			for(int j = 0;j < matrixSize; j++){
				System.out.print(edge.get(i).get(j) + " ");
			
			}
		}
	}
	/**
	* A private method that potrays the current vertices in the current graph.
	*/
	private void verticesList(){
		for(int i = 0; i < numV; i++){
			System.out.print(vertices.get(i) + " ");
		}
	
	}
	/**
	 * a private method used to potray all the coordinates where a edge is located.
	 */
	private void coorList(){
		for(int i = 0; i < edgeCord.size(); i++){
			System.out.print(edgeCord.get(i)[0] + " "  +  edgeCord.get(i)[1] + "    ");
		}
	 
	} 
	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<E> getEdges() {
		ArrayList<E> returnThis = new ArrayList<E>();
		for(int i = 0; i < edgeAtCord.size(); i++){
			returnThis.add(edgeAtCord.get(i));
		}
		return returnThis;
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		
	}
	
	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<V> getVertices() {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		return vertices;
	}
	
	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		int count  = 0;
		for(int i = 0; i < numV; i++){
			for(int j = 0; j < numV; j++){
				if(edge.get(i).get(j) != null){
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		return vertices.size();
	}
	
	/**
	* Returns a Collection view of the incoming edges incident to vertex
	* in this graph.
	* @param vertex	the vertex whose incoming edges are to be returned
	* @return  a Collection view of the incoming edges incident 
    to vertex in this graph
	*/
	public Collection<E> getInEdges(V vertex) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		ArrayList<E> returnThis = new ArrayList<E>();
		int index = vertices.indexOf(vertex);
		for(int i = 0; i < numV; i++ ){
			if(edge.get(i).get(index) != null){
				returnThis.add(edge.get(i).get(index));
			}
		}
		return returnThis;
	}
	
	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose outgoing edges are to be returned
	 * @return  a Collection view of the outgoing edges incident 
	 to vertex in this graph
	 */
	public Collection<E> getOutEdges(V vertex) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		ArrayList<E> returnThis = new ArrayList<>();
		int index = vertices.indexOf(vertex);
		for(int i = 0; i < numV; i++){
			if(edge.get(index).get(i) != null){
				returnThis.add(edge.get(index).get(i));
			}
		}
		return returnThis;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph.  A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of 
	 vertex in this graph
	 */
	public Collection<V> getPredecessors(V vertex) {
		ArrayList<V> returnThis = new ArrayList<>();
		int index = vertices.indexOf(vertex);
		for(int i = 0; i < numV; i++){
			if(edge.get(i).get(index) != null){
				returnThis.add(vertices.get(i));
			}
		}
		
		return returnThis;
	}
	
	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph.  A successor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of 
	 vertex in this graph
	 */
	public Collection<V> getSuccessors(V vertex) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		ArrayList<V> returnThis = new ArrayList<>();
		int index = vertices.indexOf(vertex);
		for(int i = 0; i < numV; i++){
			if(edge.get(index).get(i) != null){
				returnThis.add(vertices.get(i));
			}
		}
		return returnThis;
	}
	
	/**
	 * If directedEdge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge a directed edge in the graph that will be searched for
	 * @return  the source of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public V getSource(E directedEdge) {
		for(int i = 0; i < edgeCord.size();i++){
			int x = edgeCord.get(i)[0];
			int y = edgeCord.get(i)[1];
			if(this.edge.get(x).get(y) != null){
				if(edge.get(x).get(y).compareTo(directedEdge) == 0){
					return vertices.get(x);
				}
			}
		}
		return null;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge a directed edge that will be searched for.
	 * @return  the destination of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public V getDest(E directedEdge) {
		for(int i = 0; i < edgeCord.size();i++){
			int x = edgeCord.get(i)[0];
			int y = edgeCord.get(i)[1];
			if(this.edge.get(x).get(y) != null){
				if(edge.get(x).get(y).compareTo(directedEdge) == 0){
					return vertices.get(y);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the endpoints of edge as a Pair V.
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	public Pair<V> getEndpoints(E edge) {
		//Tip... the constructor for a pair of verticies looks like: new Pair<>(v1, v2);
		if(edge == null){
			return null;
		}
		for(int i = 0; i < edgeCord.size();i++){
			int x = edgeCord.get(i)[0];
			int y = edgeCord.get(i)[1];
			if(this.edge.get(x).get(y) != null){
				if(this.edge.get(x).get(y).compareTo(edge) == 0){
					Pair<V> apair = new Pair<V>(vertices.get(x),vertices.get(y));
					return apair;
				}
			}
		}
		
		
		return null;
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, 
	 or null if vertex is not present
	 */
	public Collection<V> getNeighbors(V vertex) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		if(vertex == null){
			return null;
		}
		int index = vertices.indexOf(vertex);
		if(index == -1){
			return null;
		}
		ArrayList<V> returnThis = new ArrayList<V>();
		for(int i = 0; i < numV; i++){
			if(edge.get(index).get(i) != null){
				returnThis.add(vertices.get(i));
				continue;
			}
			if(edge.get(i).get(index) != null){
				returnThis.add(vertices.get(i));
			}
		}
		return returnThis;
		
		
	}
	
	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex, 
	 or null if vertex is not present
	 */
	public Collection<E> getIncidentEdges(V vertex) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		if(vertex == null){
			return null;
		}
		int index = vertices.indexOf(vertex);
		if(index == -1){
			return null;
		}
		ArrayList<E> returnThis = new ArrayList<E>();
		for(int i = 0; i < numV; i++){
			if(edge.get(index).get(i) != null){
				returnThis.add(edge.get(index).get(i));
			}
			if(i == index){
				continue;
			}
			if(edge.get(i).get(index) != null){
				returnThis.add(edge.get(i).get(index));
			}
		}
		return returnThis;
	}
	
	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge, 
	 or null if edge is not present
	 */
	public Collection<V> getIncidentVertices(E edge) {
		//Tip... all JCF classes (including ArrayList and LinkedList) are subclasses of Collection
		if(edge == null || !(edgeAtCord.contains(edge))){
			return null;
		}
		ArrayList<V> returnThis = new ArrayList<V>();
		for(int i = 0; i < edgeCord.size();i++){
			int x = edgeCord.get(i)[0];
			int y = edgeCord.get(i)[1];
			if(this.edge.get(x).get(y) != null){
				if(this.edge.get(x).get(y).compareTo(edge) == 0){
					returnThis.add(vertices.get(x));
					returnThis.add(vertices.get(y));
				}
			}
		}
		return returnThis;
	}
	
	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul> 
	 * p  b Note /b : for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * 
	 * @param v1 the vertex that will be checked for a outgoing edge that connectes to v2
	 * @param v2 the vertex that will be checked for a incoming edge that connectes from v1
	 * @return  an edge that connects v1 to v2, 
	 or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object) 
	 */
	public E findEdge(V v1, V v2) {
		int fromIndex = vertices.indexOf(v1);
		int toIndex = vertices.indexOf(v2);
		if(fromIndex == -1 || toIndex == -1){
			return null;
		}
		if(edge.get(fromIndex).get(toIndex) != null){
			return edge.get(fromIndex).get(toIndex); 
		}

		return null;
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair V (v1, v2)).
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(E e, V v1, V v2) {
		if(e == null || v1 == null || v2 == null || edgeAtCord.contains(e)){
			throw new IllegalArgumentException("Edge is null or Edge already exist in the Graph");
			//return false;
		}
		boolean isV1 = false;
		boolean isV2 = false;
		int v1Pos = 0;
		int v2Pos = 0;
		/*for(int i = 0; i < vertices.size(); i++){
			if(vertices.get(i).compareTo(v1) == 0){
				isV1 = true;
				v1Pos = i;
			}
			if(vertices.get(i).compareTo(v2) == 0){
				isV2 = true;
				v2Pos = i;
			}
		} */
		v1Pos = vertices.indexOf(v1);
		v2Pos = vertices.indexOf(v2);
		if(v1Pos != -1 && v2Pos != -1){
			if(edge.get(v1Pos).get(v2Pos) != null){
				//throw new IllegalArgumentException("An edge already connects v1 to v2");
				return false;
			}
			edge.get(v1Pos).set(v2Pos,e);
			edgeCord.add(new Integer[]{v1Pos,v2Pos});
			edgeAtCord.add(e);
			return true;

		}
		return false;
	}
	/**
	 * a private method used to expand the matrix in case the number of vertex
	 * exceed 10. The graph will double each time the maximum is met.
	 */
	private void expandMatrix(){
		int add = 0;
		if(matrixSize == numV){
			int newSize = matrixSize * 2;
			for(int i = 0; i < matrixSize; i++){
				add = 0;
				while(add < matrixSize){
					add++;
					edge.get(i).add(null);
				}
			}
			add = 0;
			while(add < matrixSize){
				add++;
				int count = 0;
				ArrayList<E> column = new ArrayList<E>();
				while(count < newSize){
					column.add(null);
					count++;
				}
				edge.add(column);
			}
			matrixSize = newSize;

		}
	}

	
	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(V vertex) {
		if(vertex == null){
			throw new IllegalArgumentException("vertex can't be null");
		}
		if(vertices.contains(vertex)){
			return false;
		}
		expandMatrix();
		numV++;
		vertices.add(vertex);
		return true;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(E edge) {
		if(edge == null){
			return false;
		}
		int index = edgeAtCord.indexOf(edge);
		if(index != -1){
			int x = edgeCord.get(index)[0];
			int y = edgeCord.get(index)[1];
			this.edge.get(x).set(y,null);
			edgeAtCord.remove(index);
			edgeCord.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.) 
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(V vertex) {
		if(vertex == null){
			return false;
		}
		int index = vertices.indexOf(vertex);
		if(index != -1){
			vertices.remove(vertex);
			numV--;
			int oldSize = edgeCord.size();
			int count = 0;
			while(count < oldSize){
				int x = edgeCord.get(count)[0];
				int y = edgeCord.get(count)[1];
				int xupdate = edgeCord.get(count)[0];
				int yupdate = edgeCord.get(count)[1];
				if(x < index && y < index){
					count++;
					continue;
				}
				if(x == index || y == index ){
					if(edge.get(x).get(y).compareTo(edgeAtCord.get(count)) == 0){
						edge.get(x).set(y,null);
					}
					edgeCord.remove(count);
					edgeAtCord.remove(count);
					oldSize--;
					continue;
				}
				if(x > index){ 
					xupdate--;
					edgeCord.get(count)[0] = xupdate;
				}
				if(y > index){ 
					yupdate--;
					edgeCord.get(count)[1] = yupdate;
				}
				if(edge.get(x).get(y).compareTo(edgeAtCord.get(count)) == 0){
					edge.get(x).set(y,null);
				}
				edge.get(xupdate).set(yupdate, edgeAtCord.get(count));
				count++;
				

			}
			return true;
		}
		
			
		
		return false;
	}
	
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// This is a good place to look for "optimizing" your code to be faster.
	//********************************************************************************
	
	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}
	
	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns true if vertex and edge 
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex A vertex to be checked to see if it's connected to edge
	 * @param edge a edge to be checked to see if connected to vertex
	 * @return true if vertex and edge 
	 are incident to each other
	 */
	public boolean isIncident(V vertex, E edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}
	
	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}
	
	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}
	
	/**
	 * Returns the number of edges incident to vertex.  
	 * Special cases of interest:
	 * <ul>
	 * <li/> Incident self-loops are counted once.
	 * <li> If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned 
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li> If the graph is directed, then the value returned will be 
	 * the sum of this vertex's indegree (the number of edges whose 
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 *  p Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}
	
	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}
	
	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}
	
	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}
	
	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public V getOpposite(V vertex, E edge) {
		Pair<V> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
	
	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	 *  
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * 
	 * @param v1 a vertex to be checked for to see if it contains a outgoing edge to v2
	 * @param v2 a vertex to be checked for to see if it contains a incoming edge from v1
	 * @return  a collection containing all edges that connect v1 to v2, 
	 or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object) 
	 */
	public Collection<E> findEdgeSet(V v1, V v2) {
		E edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		ArrayList<E> ret = new ArrayList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}
	
	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair V (v1, v2)).
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
	
	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge a edge to be added in the current graph
	 * @param vertices a pair of vertices to will be connected with the edge
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 or if a different vertex set in this graph is already connected by edge, 
	 or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge_type.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edge_type is not legal for this graph
	 * </ul>
	 * 
	 * @param edge an edge to be added
	 * @param vertices a pair of vertices to be connected with edge
	 * @param edgeType the type of edge the edge is, however for this project, it will always be of type DIRECTED
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 or if a different vertex set in this graph is already connected by edge, 
	 or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edgeType in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	 * Returns the collection of edges in this graph which are of type edge_type.
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edgeType, or
	 null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<E> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * a String representation of the current graph.
	 * @return a potrayal of the graph
	 */
	public String toString() {
		//you may edit this to make string representations of your
		//graph for testing, making a string representation for
		//ArrayOfListsOfPairs might be helpful here too.
		return super.toString();
	}
	/**
	* A main method used for testing purposes.
	* @param args an empty string array, won't be used
	*/	
	public static void main(String[] args) {
		//Some example testing code...

		/**
		 * an abstract class used for testing purposes. The ThreeTenGraph class
		 * is generic so a node of any class should work.
		 */
		class Person extends ThreeTenGraphComponent {
			public Person(int id) { super(id); }
		}
		/**
		 * an abstract class used for testing purposes. The ThreeTenGraph class
		 * is generic so a node of any class should work.
		 */
		class Cat extends ThreeTenGraphComponent {
			public Cat(int id) { super(id); }
		}
		/**
		 * an abstract class used for testing purposes. The ThreeTenGraph class
		 * is generic so a node of any class should work.
		 */
		class IntComponent extends ThreeTenGraphComponent {
			public IntComponent(int id) { super(id); }
		}
		
		//constructs a graph
		
		ThreeTenGraph<Person,Cat> graph1 = new ThreeTenGraph<>();
		for(int i = 0; i < 3; i++) {
			graph1.addVertex(new Person(i));
		}
		for(Person p : graph1.getVertices()) {
			graph1.addEdge(new Cat((int)(Math.random()*100)), p, p);
		}
		/*System.out.println(graph1.getEdgeCount());
		graph1.matrix();*/
		
		if(graph1.getVertexCount() == 3 && graph1.getEdgeCount() == 3) {
			System.out.println("Yay 1");
		}
		
		
		//create a set of nodes and edges to test with
		IntComponent[] nodes = {
			new IntComponent(0), 
			new IntComponent(1), 
			
		};
		
		IntComponent[] edges = {
			new IntComponent(0), 
			new IntComponent(1), 
			
		};
		
		//constructs a graph
		ThreeTenGraph<IntComponent,IntComponent> graph2 = new ThreeTenGraph<>();
		for(IntComponent n : nodes) {
			graph2.addVertex(n);
		}
		graph2.addEdge(edges[0],nodes[0],nodes[1]);
		graph2.addEdge(edges[0],nodes[0],nodes[0]);
		
		
		graph2.verticesList();
		graph2.coorList();
		graph2.matrix();
		System.out.println(graph2.getEdgeCount());
		System.out.println(graph2.getEdges());
		System.out.println();
		System.out.println();
		//graph2.removeVertex(new IntComponent(5));
		
		//graph2.removeVertex(new IntComponent(1));
		System.out.println();
		System.out.println();
		graph2.verticesList();
		graph2.coorList();
		graph2.matrix();
		System.out.println(graph2.getEdgeCount());
		System.out.println(graph2.getEdges());
		/*System.out.println(graph2.getEdges());
		System.out.println(graph2.getVertices());
		System.out.println(graph2.getInEdges(new IntComponent(26)));
		System.out.println(graph2.getOutEdges(new IntComponent(26))); */
		
		if(graph2.containsVertex(new IntComponent(1)) && graph2.containsEdge(new IntComponent(10000000))) {
			System.out.println("Yay 2");
		}
		
		//lot more testing here...
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to edit/fix the JavaDocs)
	//********************************************************************************
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> the vertex type for the graph factory
	 * @param <E> the edge type for the graph factory
	 * @return an instance of this graph type
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<Graph<V,E>> getFactory() { 
		return new Factory<Graph<V,E>> () {
			public Graph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	
	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge an edge of any type
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(E edge) {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). 
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(E edge) {
		return 2;
	}
}
