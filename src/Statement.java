public class Statement {
  int id;
  String name;
  TipoStatement kind;

  public Statement(int id, String name, TipoStatement kind) {
    this.id = id;
    this.kind = kind;
    this.name = name;
  }

  public void setKind(TipoStatement kind) {
    this.kind = kind;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
