/**
 * 
 * @author Natalija Ciric Ilic
 *
 */
public class Outcast {
	
	private WordNet wordNet;
	
	/**
	 * Constructor takes a WordNet object
	 * @param wordnet
	 */
	public Outcast(WordNet wordnet) {
		this.wordNet = wordnet;
	}

	/**
	 * Given an array of WordNet nouns, return an outcast
	 * @param nouns
	 * @return
	 */
	public String outcast(String[] nouns) {
		int[][] cache = new int[nouns.length][nouns.length];
		for (int row = 0; row < nouns.length; row++) {
			for (int col = row + 1; col < nouns.length; col++) {
				cache[row][col] = wordNet.distance(nouns[row], nouns[col]);
			}
		}
		return nouns[outcast(cache)];
	}
	
	private int outcast(int[][] cache) {
		int max = Integer.MIN_VALUE;
		int index = 0;
		for (int i = 0; i < cache.length; i++) {
			int sum = 0;
			for(int j = 0; j < i; j++)
				sum += cache[j][i];
			for (int j = i; j < cache.length; j++) 
				sum += cache[i][j];
			
			if(sum > max) {
				max = sum;
				index = i;
			}
		}
		
		return index;
	}
	
	/**
	 * For unit testing of this class (such as the one below)
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		//Stopwatch stopwatch = new Stopwatch();
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        String[] nouns = In.readStrings(args[t]);
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	        //System.out.println(stopwatch.elapsedTime());
	    }
	}

}
