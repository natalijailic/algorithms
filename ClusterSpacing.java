import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterSpacing {

	private static List<String> hammingList = new ArrayList<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		In in = new In(args[0]);
		int E = in.readInt();
		int c = in.readInt();
		int duplicates = 0;
		System.out.println("E: " + E + " c: " + c);
		List<String> numbers = new ArrayList<String>();
		Map<String, Integer> points = new HashMap<String, Integer>();
        for (int i=0; i< E; i++) {
            String number="";
        	for (int j = 0; j < c; j++) {
        		number+=in.readString();
        	}
        	
        	if (numbers.contains(number))
        		duplicates++;
        	
        	numbers.add(number);
        	//System.out.println(number);
        }
        
        int i = 0;
        for (String number : numbers) {
        	points.put(number, i);
        	i++;
        }
        
		System.out.println("Duplicates: " + duplicates);
        System.out.println(Integer.valueOf(findLargestK(points, E)));
	}
	
	private static int findLargestK(Map<String, Integer> points, int size) {
		int k = points.size();
		MinPQ<String> pq = new MinPQ<String>();
        for (String e : points.keySet()) {
            pq.insert(e);
        }

        UF uf = new UF(size);
        while (!pq.isEmpty()) {
            String e = pq.delMin();
            //System.out.println("Seed: " + e);
            ham(0, 2, e, e.length());
            ham(0, 1, e, e.length());
            for (String w : hammingList) {
            	if (points.containsKey(w) && !uf.connected(points.get(e), points.get(w))) {
            		uf.union(points.get(e), points.get(w));
            		k--;
            	}
            }
            hammingList.clear();
        }
		return k;
	}
	
	private static void ham(int start, int k, String bit, int length)
	{
	  if (k==0){
		  hammingList.add(bit);
	      return;
	  }
	  
	  if (k>1 && start==length-1)
	    return;
	  if (start>length-1)
		  return;
	    ham(start+1,k,bit,length);  //Called because no change occurs
	    
	    if(bit.charAt(start) == '0'){    
	        char [] charArray = bit.toCharArray();
	        charArray[start] ='1';
	        ham(start+1, k-1, String.valueOf(charArray), length);
	    } else if(bit.charAt(start)=='1'){   //Called when a change occurs from 1 to 0
	    	char [] charArray = bit.toCharArray();
	    	charArray[start] = '0';
	        ham(start+1,k-1,String.valueOf(charArray),length);
	    }
	}
}
