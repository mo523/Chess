//Including a main for now for testing purposes
//Will remove eventually and just run it from the driver
public class Display
{
	public static void main( String[] args )
	{
		
		//int pieces[][] = {{
		for (int i = 0; i < 7; i++)
		{
			System.out.println("________________________________________________________________");
			
			for (int j = 0; j < 7; j++)
			{
				for (int k = 0; k < 7; k ++)
				{
					if (k == 0)
						System.out.print("|");
					//System.out.print(piece(i, j, k));
					if (k == 7)
						System.out.print("|");				}
			}
			System.out.println("____________________________");
			System.out.println("\u23af");
		}
		
	}
	private String piece(int x, int y, int row)
	{
		
		
		return null;
	}

}
