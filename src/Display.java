//Including a main for now for testing purposes
//Will remove eventually and just run it from the driver
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Display
{
	public static void main( String[] args )
	{
		AnsiConsole.systemInstall();
	
		for (int i = 0; i < 48; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (j%2 == 0)
					if (i/6%2==0)
						System.out.print(ansi().bg(YELLOW).fgBright(WHITE).a(PieceSection(i,j)).reset());
					else
						System.out.print(ansi().bg(WHITE).fgBright(WHITE).a(PieceSection(i,j)).reset());
				else
					if (i/6%2==0)
						System.out.print(ansi().bg(WHITE).fg(BLACK).a(PieceSection(i,j)).reset());
					else
						System.out.print(ansi().bg(YELLOW).fg(BLACK).a(PieceSection(i,j)).reset());

			}
			System.out.println();
		}
		System.out.println("test");
		AnsiConsole.systemUninstall();

	}
	
	public static String PieceSection(int i, int j)
	{
		String fourteen;
		i %= 6;
		if (i == 0 || i == 5 )//|| j == 0 || j ==5)
		{
			fourteen = "              ";
		}
		else 
			fourteen = "  xxxxxxxxxx  ";
		return fourteen;
	}

}
