/** Starter code for SP8
 *  @author
 *  Ketki Mahajan (krm150330)
 * 	Anirudh Erabelly (axe170009)
 */
package axe170009;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DFS extends GraphAlgorithm < DFS.DFSVertex > {
	enum Color { BLACK, WHITE, GRAY; }
	
    List < Vertex > finishList;
    //List< List < Vertex > > scc;
    static boolean notDAG;
    int componentCount;
    
    public static class DFSVertex implements Factory {

        int cno;
        Color col;

        public DFSVertex(Vertex u) {
            cno = 0;
            col = Color.WHITE;
        }
        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    public DFS(Graph g) {
    	super(g, new DFSVertex(null));
    }
    
    public static DFS depthFirstSearch(Graph g) {
    	DFS graphDFS = new DFS(g);
        graphDFS.dfs();
        return graphDFS;
    }
    
    public void dfs() {
    	finishList = new LinkedList < Vertex > ();
    	//scc = new ArrayList< List< Vertex > >();
    	componentCount = 0;
    	
        for (Vertex v: g) {
            get(v).col = Color.WHITE;
        }
        
        for (Vertex u: g) {
            if (get(u).col == Color.WHITE) {
            	componentCount++;
            	/*if(componentCount > scc.size()) {
            		scc.add(new LinkedList<Vertex>());
            	}*/
            	DFS_Visit(u);
            }
        }
    }
    
    private void dfs(List<Vertex> list) {
    	finishList = new LinkedList < Vertex > ();
    	//scc = new ArrayList< List< Vertex > >();
    	componentCount = 0;
    	
    	Iterator<Vertex> listIt = list.iterator();
    	while(listIt.hasNext()) {
    		get(listIt.next()).col = Color.WHITE;
    	}
    	
    	for(Vertex u : list) {
    		if (get(u).col == Color.WHITE) {
            	componentCount++;
            	/*if(componentCount > scc.size()) {
            		scc.add(new LinkedList<Vertex>());
            	}*/
                DFS_Visit(u);
            }
    	}
    }
    
    private void DFS_Visit(Vertex u) {
        get(u).col = Color.GRAY;
        get(u).cno = componentCount;
        for (Edge e: g.incident(u)) {

            Vertex v = e.otherEnd(u);

            if (get(v).col == Color.WHITE) {
                DFS_Visit(v);
            } else if (get(v).col == Color.GRAY) {
                notDAG = true;
            }
        }
        get(u).col = Color.BLACK;
        finishList.add(0, u);
        //scc.get(componentCount).add(0, u);
    }

    // Member function to find topological order
    public List < Vertex > topologicalOrder1() {
    	return finishList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return this.componentCount;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
    	return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List < Vertex > topologicalOrder1(Graph g) {
    	DFS dfsGraph = depthFirstSearch(g);
        
        if (!g.isDirected() || notDAG) {
            return null;
        } else {
            return dfsGraph.topologicalOrder1();
        }
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List < Vertex > topologicalOrder2(Graph g) {
        return null;
    }
    
    public static DFS stronglyConnectedComponents(Graph g) {
    	DFS graphDFS = new DFS(g); 
		graphDFS.dfs();
		/*concurrent modification exception will be thrown if finishTimeList = graphDFS.finishList
		is directly used 
		*/
		List<Vertex> finishTimeList = new LinkedList<Vertex>();
		for(Vertex u : graphDFS.finishList) {
			finishTimeList.add(u);
		}
		g.reverseGraph();
    	graphDFS.dfs(finishTimeList);
    	g.reverseGraph();
    	
    	return graphDFS;
    }

    public static void main(String[] args) throws Exception {
        String string = "11 17   1 11 1   2 3 1   2 7 1   3 10 1   4 9 1   4 1 1   5 7 1   5 8 1   5 4 1   6 3 1   7 8 1   8 2 1   9 11 1   10 6 1   11 6 1   11 3 1   11 4 1 0";
        Scanner in ;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph( in , true);
        g.printGraph(false);

        System.out.println("Topological Order: ");
        System.out.println(topologicalOrder1(g));
        
        System.out.println("Strongly connected components: ");
        System.out.println(stronglyConnectedComponents(g).componentCount);
        //System.out.println(stronglyConnectedComponents(g).scc);
    }
}