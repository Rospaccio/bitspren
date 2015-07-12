package org.merka.bitspren;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.merka.bitspren.util.BitsprenUtils;

public class BitsprenREPL
{

	public static void main(String[] args)
	{
		try
		{
			System.out.println("Bitspren REPL starting..." + System.lineSeparator());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			BitsprenParser parser = BitsprenUtils.defaultParser(reader);
			boolean looping = true;
			do
			{
				System.out.print(System.lineSeparator() + "|bitspren>");
				String line = reader.readLine();
				looping = !line.equals("stop");
				
			} while (looping);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Impossible to continue due to unexpected error");
		}
	}

}
