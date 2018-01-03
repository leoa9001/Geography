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
	
	
	
	
	//removes country country from the graph
	//this simulates setting first move = country and then puts gamestate into last char of country 
	//fields to only be used by localize :p
	
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
		
		return true;
	}

	
		
}
