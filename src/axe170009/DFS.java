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
import java.util.List;
import java.util.Scanner;

public class DFS extends GraphAlgorithm < DFS.DFSVertex > {

    List < Vertex > topologicalList = new ArrayList < Vertex > ();

    enum Color {
        Black,
        White,
        Gray;
    }

    static boolean notDAG;
    public static class DFSVertex implements Factory {

        int cno;
        Color col;

        public DFSVertex(Vertex u) {
            cno = 0;
            col = Color.White;
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
        graphDFS.dfs(g);
        return graphDFS;

    }
    public void dfs(Graph g) {
        for (Vertex v: g) {
            get(v).col = Color.White;
        }

        for (Vertex u: g) {
            if (get(u).col == Color.White) {
                DFS_Visit(u);
            }
        }

    }
    private void DFS_Visit(Vertex u) {
        get(u).col = Color.Gray;
        for (Edge e: g.incident(u)) {

            Vertex v = e.otherEnd(u);

            if (get(v).col == Color.White) {
                DFS_Visit(v);
            } else if (get(v).col == Color.Gray) {
                notDAG = true;
            }
        }
        get(u).col = Color.Black;
        topologicalList.add(0, u);
    }

    // Member function to find topological order
    public List < Vertex > topologicalOrder1() {

        return topologicalList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
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
    	return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        Scanner in ;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph( in , true);
        g.printGraph(false);

        System.out.println("Topological Order: ");
        System.out.println(topologicalOrder1(g));

    }
}