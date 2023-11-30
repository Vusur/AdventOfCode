package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1p1 
{	
	public static void main(String[] args) 
	{
		String fpath = "./src/inputs/day1.txt";
		try
		{
			File file = new File(fpath);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{
				String line = sc.nextLine();
				System.out.println(line);
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
}
