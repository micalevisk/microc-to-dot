public class VertexInfo {
	public static final int START = 0;
	public static final int STOP = 1;
	public static final int CONECTOR = 2;
	public static final int THEN = 3;
	public static final int ELSE = 4;
	public static final int PRIMEIRA_INSTRUCAO = 5;
	public static final int RETURN = 6;
	public static final int OTHER = 7;

	public int tipo;
	String rotulo;

	public VertexInfo(int tipo, String rotulo){
		this.tipo = tipo;
		this.rotulo = rotulo;
	}

	public String getRotuloArestaSucessor() {
		if (tipo == THEN) return "T";
		if (tipo == ELSE) return "F";
		return "";
	}
}