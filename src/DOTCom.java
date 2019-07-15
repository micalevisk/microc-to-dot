import java.util.*;

class EdgeMurphy {
  int sourceId, destId;
  // Atributos pro DOT
	String _style;
	String _label;

  public EdgeMurphy(int sourceId, int destId, String label) {
    this.sourceId = sourceId;
    this.destId = destId;

    this._label = label;
    this._style = "";
  }

  @Override
  public String toString() {
    if (!_label.isEmpty()) {
      return String.format(
        "  %d -> %d" +
        "[" +
        "style=\"%s\", " +
        "label=\"%s\"" +
        "];\n"
        ,sourceId, destId
        ,_style
        ,_label
      );
    }

    return String.format(
      "  %d -> %d" +
      "[" +
      "style=\"%s\"" +
      "];\n"
      ,sourceId, destId
      ,_style
    );
  }
}

class Cortex {
  private int id;
  // Atributos pro DOT
  public String _fillcolor;
  public String _style;
  public String _label;

  public Cortex(int id, String fillcolor, String label) {
    this.id = id;
    this._fillcolor = fillcolor;
    this._style = "filled";
    this._label = label;
  }

  public String getName() {
    return _label;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.format(
      "  %d" +
      "[" +
      "shape=ellipse, " +
      "fillcolor=%s, " +
      "style=%s, " +
      "label=\"%s\""+
      "];\n"
      ,id
      ,_fillcolor
      ,_style
      ,_label
    );
  }
}


public class DOTCom {
  private List< Pair<String,Cortex> > cortexesInfos; // cada elemento é o `name` do vertex e o cortex relativo
  private List<EdgeMurphy> edges;

  public static DOTCom createFromGraph(Graph g) {
    if (g == null) return null;
    DOTCom dot = new DOTCom();

    g.getVertices().forEach(v -> {
      String fillcolor = "lightyellow";

      Statement st = (Statement) v.info;
      TipoStatement kind = st.kind;
      if (kind == TipoStatement.START_OF_FUNCTION || kind == TipoStatement.END_OF_FUNCTION) {
        fillcolor = "salmon";
      } else if (kind == TipoStatement.FIRST_STATEMENT) {
        fillcolor = "palegreen2";
      } else if (kind == TipoStatement.CONTROL) {
        fillcolor = "orange";
      }

      dot.addVertex(st.id, v.name, fillcolor, st.name);
    });

    dot.cortexesInfos.stream().forEach(cortexInfo -> {
      String vertexName = cortexInfo.getKey();
      Cortex sourceCortex = cortexInfo.getValue();
      Vertex v = g.getVertex(vertexName);

      for (Iterator<Edge> itr = v.adj.iterator(); itr.hasNext();) {
        Edge e = itr.next();
        Vertex w = e.dest;
        Statement wStatement = (Statement) w.info;
        Cortex destCortex = dot.findFirstById(wStatement.id); // a chave de busca deve ser a que identifica o vértice

        dot.addEdge(sourceCortex, destCortex, (String)e.info);
      }
    });

    return dot;
  }

  public DOTCom() {
    this.cortexesInfos = new ArrayList<>();
    this.edges = new ArrayList<>();
  }

  public Cortex addVertex(int vertexId, String vertexName, String fillcolor, String label) {
    Cortex newCortex = new Cortex(vertexId, fillcolor, label);
    this.cortexesInfos.add( Pair.of(vertexName, newCortex) );
    return newCortex;
  }

  public EdgeMurphy addEdge(Cortex source, Cortex dest, String label) {
    EdgeMurphy newEdge = new EdgeMurphy(source.getId(), dest.getId(), label);
    this.edges.add(newEdge);
    return newEdge;
  }

  public Cortex findFirstById(int id) {
    for (Pair<String, Cortex> cortexInfo : cortexesInfos) {
      Cortex currCortex = cortexInfo.getValue();
      if (currCortex.getId() == id) return currCortex;
    }
    return null;
  }

	public void print() {
		System.out.println(
  		"digraph {\n" +
  		"  outputorder=edgesfirst;"
		);

    cortexesInfos.forEach(cortexInfo -> {
      Cortex cortex = cortexInfo.getValue();
      System.out.print(cortex.toString());
    });

    edges.forEach(vertex -> {
      System.out.print(vertex.toString());
    });

    System.out.println(
      "}"
    );
	}
}
