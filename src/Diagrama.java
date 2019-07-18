public class Diagrama {
	Graph g;
	int contVertice;
	Vertex ultimoVertice;
	Vertex stopVertice;
	private String rotulo;

	public Diagrama() {
		g = new Graph();
		contVertice = 0;
		ultimoVertice = null;
		stopVertice = null;
		rotulo = "";
	}

	public void setRotulo(String novoRotulo) {
		rotulo = novoRotulo;
	}

	public String acumularToken(Token t, String miolo) {
		if (t != null) rotulo += miolo + t.val;
		return rotulo;
	}

	public String acumularToken(Token t) {
		return acumularToken(t, " ");
	}

	public String getRotulo() {
		return rotulo.trim()
			.replaceAll("\"","'"); // substituir as aspas duplas por aspas simples
	}

	public void linkarUltimoEAtualizar(Vertex v) {
		if (ultimoVertice != null) {
			VertexInfo infoUltimo = (VertexInfo)ultimoVertice.info;
			if (infoUltimo.tipo != VertexInfo.STOP) {
				String rotuloAresta = infoUltimo.getRotuloArestaSucessor();
				g.addEdge(ultimoVertice, v, new EdgeInfo(rotuloAresta));
			}
		}
		setUltimoVertice(v);
	}

	public void setUltimoVertice(Vertex novoUltimo) {
		ultimoVertice = novoUltimo;
	}

	public void alcancarStop(Vertex v) {
		if (stopVertice == null) {
			setRotulo("stop");
			stopVertice = criarVertice(VertexInfo.STOP);
		} else {
			VertexInfo info = (VertexInfo)v.info;
			String rotuloAresta = info.getRotuloArestaSucessor();
			g.addEdge(v, stopVertice, new EdgeInfo(rotuloAresta));
		}

	}

	public Vertex criarVertice(int tipo) {
		contVertice++;

		String nome = Integer.toString(contVertice);
		String rotulo = getRotulo();

		if (contVertice == 2) tipo = VertexInfo.PRIMEIRA_INSTRUCAO;
		VertexInfo info = new VertexInfo(tipo, rotulo);
		Vertex novoVertice = g.addVertex(nome, info);

		linkarUltimoEAtualizar(novoVertice);
		setRotulo("");

		if (tipo == VertexInfo.RETURN) {
			alcancarStop(novoVertice);
		}

		return novoVertice;
	}

	public Vertex criarVertice() {
		return criarVertice(VertexInfo.OTHER);
	}


	public void mostrar()
	{
		String propBase = "[shape=ellipse, fillcolor=lightyellow, style=filled";

		System.out.println("digraph {");
		System.out.println("  outputorder=edgesfirst;");

		String[] properties = new String[8];
		properties[VertexInfo.START] = "[shape=ellipse, fillcolor=salmon, style=filled";
		properties[VertexInfo.STOP] = "[shape=ellipse, fillcolor=salmon, style=filled";
		properties[VertexInfo.PRIMEIRA_INSTRUCAO] = "[shape=ellipse, fillcolor=palegreen2, style=filled";
		properties[VertexInfo.CONECTOR] = "[shape=ellipse, fillcolor=orange, style=filled";
		properties[VertexInfo.THEN] = propBase;
		properties[VertexInfo.ELSE] = propBase;
		properties[VertexInfo.RETURN] = propBase;
		properties[VertexInfo.OTHER] = propBase;

		// listando os nós
		for (Vertex v : g.getVertices()) {
			String id = v.name;
			VertexInfo info = (VertexInfo)v.info;
			String nodot = "  " + id + properties[info.tipo] + ", label=\"" + info.rotulo + "\"" + "];";
			System.out.println(nodot);
		}

		// listando as arestas
		for (Vertex v : g.getVertices()) {
			for (Edge e : g.getEdges(v.name)) {
				Vertex w = e.dest;
				VertexInfo wInfo = (VertexInfo)w.info;
				EdgeInfo vwInfo = (EdgeInfo)e.info;
				if (vwInfo != null) {
					String arestadot = "  " + v.name + " -> " + w.name +
						"[style=\"\", label=\"" + vwInfo.rotulo + "\"" + "];";
					System.out.println(arestadot);
				}
			}
		}

		System.out.println("}");
	}
}
