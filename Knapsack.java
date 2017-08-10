
public class Knapsack {
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		int W = in.readInt(); //knapsack size
		int N = in.readInt(); //number of items

        int[] v = new int[N + 1];
        int[] w = new int[N + 1];

        for (int n = 1; n <= N; n++) {
        	v[n] = in.readInt();
            w[n] = in.readInt();
        }
        
        Stopwatch sw = new Stopwatch();

        int[] f = new int[W + 1];
        for (int i= 1; i <= N; i++) { 
        	for (int j = W; j > 1; j--) { 
	        	if (w[i] <= j && f [j - w[i]] + v[i] > f [j])
	        		f [j] = f [j - w[i]] + v[i];
        	}
        }

        System.out.println("Result: " + f[W] + " Elapsed time: " + sw.elapsedTime());
        
    }

}
