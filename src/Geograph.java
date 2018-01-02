import java.util.Arrays;



public class Geograph {
	public static final char baseChar = 'A';
	
	private int[][]graph;
	private int gameState = baseChar;
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
//		for(int i=0; i < 26; i++)System.out.println(Arrays.toString(graph[i]));
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
		if(graph[(int)c1 - (int)'A'][(int)c2 - (int)'A']==0)return false;
		
		graph[(int)c1 - (int)'A'][(int)c2 - (int)'A']--;
		gameState = c2 - 'A';
		playerTurn = (playerTurn+1)%2;
		
		//now find connected component:
		
		marked = new boolean[26];
		localize(gameState);
		System.out.println(Arrays.toString(marked));
		
		int sum = 0;
		for(int i = 0; i < 26; i++)for(int j = 0;j < 26;j++){
			if(marked[i])sum+=graph[i][j];
		}
		System.out.println(sum);
		return true;
	}
	
	private void localize(int state){
		if(marked[state])return;
		marked[state] = true;
		for(int i = 0;i < 26; i++){
			if(graph[state][i] > 0)localize(i);
		}
	}
	
	
	
	
	
	
	
}
