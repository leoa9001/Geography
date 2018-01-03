import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
	
	
	public static void main(String[]args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("countries.txt"));
		List<String>countries = new ArrayList<String>();
		while(sc.hasNextLine()){
			countries.add(sc.nextLine().trim());
		}
		
		String[]countries2 = new String[countries.size()];
		for(int i = 0; i < countries.size();i++)countries2[i] = countries.get(i);
		
		System.out.println(Arrays.toString(countries2));
		Geograph graph = new Geograph(countries2);
		graph.localize("AA");
		System.out.println(graph.doesP0Win());
		
		
		sc.close();
	}

}
