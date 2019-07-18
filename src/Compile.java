/**
 * COMO USAR:
 * Após executar o `../build.sh` para gerar o JAR executável,
 * $ java -jar ./Compilador.jar <arquivo_alvo> [arquivo_saida]
 */

import java.io.*;
import java.util.*;

public class Compile
{

	public static void main(String argv[])
	{
		Scanner s = new Scanner(argv[0]);
		Parser p = new Parser(s);

		try {
			PrintStream outfile = new PrintStream(new FileOutputStream(argv[1]), true, "UTF-8");
			System.setOut(outfile);
		} catch(Exception ex) {
			// PrintStream out = new PrintStream(System.out, true, "UTF-8");
			// System.setOut(out);
			try {
				System.setOut(new PrintStream(System.out, true, "UTF-8"));
			} catch (Exception ex2) {}
		} finally {
			p.Parse();
			if (p.errors.count > 0) {
				System.out.println("\n" + "~> Quantidade de erros: " + p.errors.count);
			} else {
				p.d.mostrar();
			}
		}
	}

}
