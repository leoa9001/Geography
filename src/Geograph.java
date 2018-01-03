import java.util.Arrays;



public class Geograph {
	public static final char baseState = 0;
	
	private int[][]graph;
	private int gameState = 0;
	private int playerTurn = 0;
	
	
	public Geograph(String[]countries){
		graph = new int[26][26];//alphabet
		
		//fill in multigraph
		for(int i = 0;i < countries.length; i++){
			String s = countries[i];
			s = s.trim();
			s = s.toUpperCase();
			char c1 = s.charAt(0), c2 = s.charAt(s.length()-1);
			graph[(int)c1 - (int)'A'][(int)c2 - (int)'A']++;
		}
	}
	
	
	//On current gameboard: can p0 force a win?
	public boolean doesP0Win(){
		int p = playerTurn, gs = gameState;//recursive stack storage
		System.out.println(gs);
		//base case is when outdegree==0 which is had if all graph[gs][j]==0 and we get the return is whichever current player is on loses: i.e. p==1.
		for(int j = 0;j < 26;j++){
			if(graph[gs][j]==0)continue;//nothing to traverse
			//traverse edge gs -> j
			playerTurn = (playerTurn + 1) %2;
			gameState = j;
			graph[gs][j]--;
			//recurse
			boolean l = doesP0Win();
			//detraverse
			gameState = gs;
			playerTurn = p;
			graph[gs][j]++;
			//check if known win already
			if(l&&p==0)return true;
			if(!l&&p==1)return false;
		}
		
		
		return p==1;
	}
	
	
	
	
	//removes country country from the graph and finds the connected component at the end of it: 
	//this simulates setting first move = country and then puts gamestate into last char of country 
	//fields to only be used by localize :p
	private boolean[]marked;
	
	public boolean localize(String s){
		//does player 1's move
		s.trim();
		s.toUpperCase();
		char c1 = s.charAt(0), c2 = s.charAt(s.length()-1);
		if(graph[c1 - 'A'][c2 - 'A']==0)return false;
		
		//only part which alters game variables
		graph[c1 - 'A'][c2 - 'A']--;
		gameState = c2 - 'A';
		playerTurn = (playerTurn+1)%2;
		
		//now find connected component:
		
		marked = new boolean[26];
		localize(gameState);
//		System.out.println(Arrays.toString(marked));
		
		
//		//calculating statistics to see how long it may take and damn it'll take long ;0;
//		int sum = 0;
//		int prod = 1;
//		int m = 0;
//		for(int i = 0; i < 26; i++){
//			int sum2 = 0;
//			int sum3 = 0;
//			if(marked[i])for(int j = 0; j < 26; j++){//calculates a degree of a marked node
//				sum2 += graph[i][j];
//				if(graph[i][j]>=1)sum3++;
//			}
//			sum+=sum2;
//			if(sum2 >= 1)prod*= sum2;
//			m = Math.max(m, sum3);
//			System.out.println(sum3);
//		}
//		System.out.println(sum);
//		System.out.println(prod);
//		System.out.println(m);
//		System.out.println(Math.pow(m, 164));
		return true;
	}
	
	private void localize(int state){
		if(marked[state])return;
		marked[state] = true;
		for(int i = 0;i < 26; i++){
			if(graph[state][i] > 0)localize(i);
		}
	}
	
	public long longestWalk(){
		int[]degrees = new int[26];
		long prod = 1;
		for(int i = 0; i < 26;i++){
			if(!marked[i])continue;
			for(int j = 0; j < 26; j++){
				if(graph[i][j]>=1)degrees[i]++;
			}
		}
		Arrays.sort(degrees);
		System.out.println(Arrays.toString(degrees));
		
		while(degrees[25]>0){
			prod*= degrees[25];
			degrees[25]--;
			Arrays.sort(degrees);
		}
		
		return prod;
	}
	
	
	
	
	
}
