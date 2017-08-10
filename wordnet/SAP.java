/**
 * 
 * @author Natalija Ciric Ilic
 *
 */
public class SAP {
	
	private Digraph G;
	private int ancestor;
	
	/**
	 * Constructor takes a digraph (not necessarily a DAG)
	 * @param G
	 */
	public SAP(Digraph G) {
		this.G = new Digraph(G);
	}

	/**
	 * Length of shortest ancestral path between v and w;
	 * @param v vertex
	 * @param w vertex
	 * @return -1 if no such path
	 */
	public int length(int v, int w) {
		validateInput(v, w);
		int min = Integer.MAX_VALUE;
		this.ancestor = -1;
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		for (int a = 0; a < G.V(); a++) {
			if (bfsv.hasPathTo(a) && bfsw.hasPathTo(a) && bfsv.distTo(a) + bfsw.distTo(a) < min) {
				min = bfsv.distTo(a) + bfsw.distTo(a);
				this.ancestor = a;
			}
		}
		if (min != Integer.MAX_VALUE) 
			return min;
		else 
			return -1;
	}

	/**
	 * A common ancestor of v and w that participates in a shortest ancestral path
	 * @param v vertex
	 * @param w vertex
	 * @return -1 if no such path
	 */
	public int ancestor(int v, int w) {
		validateInput(v, w);
		length(v, w);
		return this.ancestor;
	}

	/**
	 * Length of shortest ancestral path between any vertex in v and any vertex in w
	 * @param v vertex
	 * @param w vertex
	 * @return -1 if no such path
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int min = Integer.MAX_VALUE;
		this.ancestor = -1;
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		for (int a = 0; a < G.V(); a++) {
			if (bfsv.hasPathTo(a) && bfsw.hasPathTo(a) && bfsv.distTo(a) + bfsw.distTo(a) < min) {
				min = bfsv.distTo(a) + bfsw.distTo(a);
				this.ancestor = a;
			}
		}
		if (min != Integer.MAX_VALUE) 
			return min;
		else 
			return -1;
	}

	/**
	 * A common ancestor that participates in shortest ancestral path
	 * @param v vertex
	 * @param w vertex
	 * @return -1 if no such path
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		length(v, w);
		return this.ancestor;
	}

	/**
	 * For unit testing of this class (such as the one below)
	 * @param args
	 */
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
	
	//helper method to validate the input
	private void validateInput(int v, int w) {
		if(v < 0 || w < 0 || v > G.V() - 1 || w > G.V() - 1) {
			throw new IndexOutOfBoundsException();
		}
	}

}
