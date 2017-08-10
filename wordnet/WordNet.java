import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Natalija Ciric Ilic
 *
 */
public class WordNet {
	
	private Digraph G;
	private int V;
	private Map<Integer, Set<String>> nouns;
	private Map<String, Bag<Integer>> synsetsIds;
	private SAP sap;
	
	/**
	 * Constructor takes the name of the two input files
	 * @param synsets
	 * @param hypernyms
	 */
	public WordNet(String synsets, String hypernyms) {
		In inputFile  = new In(synsets);
		nouns = new HashMap<Integer, Set<String>>();
		synsetsIds = new HashMap<String, Bag<Integer>>();
		int v = 0;
		while(!inputFile.isEmpty()) {
			String[] lines = inputFile.readLine().split(",");
			Set<String>keys = new LinkedHashSet<String>();
			Bag<Integer> ids = null;
			String[] synsetS = lines[1].split(" ");
			for (int i = 0; i < synsetS.length; i++) {
				keys.add(synsetS[i]);
				if (synsetsIds.containsKey(synsetS[i])) {
					 ids = synsetsIds.get(synsetS[i]);
					 ids.add(Integer.parseInt(lines[0]));
				} else {
					ids = new Bag<Integer>();
					ids.add(Integer.parseInt(lines[0]));
				}
				synsetsIds.put(synsetS[i], ids);
			}
			nouns.put(Integer.parseInt(lines[0]), keys);
			v++;
		}

		this.V = v;
		int w = 0;
		G = new Digraph(V);
		inputFile = new In(hypernyms);
		while(!inputFile.isEmpty()) {
			String[] lines = inputFile.readLine().split(",");
			if (lines.length > 1)
				w++;
			int synsetId = Integer.parseInt(lines[0]);
			for (int i = 1; i < lines.length; i++) {
				G.addEdge(synsetId, Integer.parseInt(lines[i]));
			}
		}

		DirectedCycle dc = new DirectedCycle(G);
		if (dc.hasCycle()) 
			throw new java.lang.IllegalArgumentException();
		if (v - w >= 2) 
			throw new java.lang.IllegalArgumentException();
		
		sap = new SAP(G);
	}

	/**
	 * The set of nouns (no duplicates), returned as an Iterable
	 * @return
	 */
	public Iterable<String> nouns() {
		return synsetsIds.keySet();
	}

	/**
	 * Is the word a WordNet noun?
	 * @param word
	 * @return
	 */
	public boolean isNoun(String word) {
		if (word == null)
			throw new java.lang.NullPointerException();
		return synsetsIds.containsKey(word);
	}

	/**
	 * Distance between nounA and nounB (defined below)
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		if (isNoun(nounA) && isNoun(nounB)) {
			return sap.length(synsetsIds.get(nounA), synsetsIds.get(nounB));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * A synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	 * in a shortest ancestral path (defined below)
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public String sap(String nounA, String nounB) {
		String synset = "";
		if (isNoun(nounA) && isNoun(nounB)) {
			int n = sap.ancestor(synsetsIds.get(nounA), synsetsIds.get(nounB));
			for (String entry : nouns.get(n)) {
				synset += entry + " ";
	    	}
	    	return synset.trim();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * For unit testing of this class
	 * @param args
	 */
	public static void main(String[] args) {
		
		WordNet wordNet = new WordNet(args[0], args[1]);
		
		System.out.println(wordNet.distance("zebra","table"));
		System.out.println(wordNet.sap("zebra","table"));
	}

}
