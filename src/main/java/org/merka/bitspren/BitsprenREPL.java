package org.merka.bitspren;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.merka.bitspren.util.BitsprenUtils;

public class BitsprenREPL
{

	public static void main(String[] args)
	{
		try
		{
			System.out.println("Bitspren REPL starting..." + System.lineSeparator());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			boolean looping = true;
			do
			{
				System.out.print(System.lineSeparator() + "|bitspren>");
				String line = reader.readLine();
				looping = !line.equals("stop");
				BitsprenErrorListener errorListener = new BitsprenErrorListener(); 
				BitsprenParser parser = BitsprenUtils.defaultParser(new StringReader(line), errorListener);
				@SuppressWarnings("unused")
				ProgramContext context = parser.program();
				if(!errorListener.isFail())
				{
					System.out.println("OK!");
				}
				else
				{
					System.out.println("Not cool man, not cool...");
				}
				
			} while (looping);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Impossible to continue due to unexpected error");
		}
	}

}
