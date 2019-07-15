class Ids {
  // não são instruções
  final static int START_OF_FUNCTION = 0x002;
  final static int END_OF_FUNCTION   = 0x004;
  final static int CONDITIONAL_ELSE  = 0x008;

  final static int NOOP              = 0x001; // não mostrar no gráfico
  final static int LOOP              = 0x020;
  final static int CONDITIONAL_IF    = 0x040;
  final static int FIRST_STATEMENT   = 0x080;
  final static int LEAVE_FUNCTION    = 0x100;
  final static int INSTRUCTION = NOOP | LOOP | CONDITIONAL_IF | FIRST_STATEMENT | LEAVE_FUNCTION;

  final static int CONDITIONAL = CONDITIONAL_ELSE | CONDITIONAL_IF;
}



public enum TipoStatement {
  // não são instruções
  START_OF_FUNCTION(Ids.START_OF_FUNCTION),
  END_OF_FUNCTION(Ids.END_OF_FUNCTION),
  CONDITIONAL_ELSE(Ids.CONDITIONAL_ELSE),

  NOOP(Ids.NOOP), // não mostrar se o vértice for desse tipo
  CONTROL(Ids.NOOP),
  LOOP(Ids.LOOP),
  CONDITIONAL_IF(Ids.CONDITIONAL_IF),
  FIRST_STATEMENT(Ids.FIRST_STATEMENT),
  LEAVE_FUNCTION(Ids.LEAVE_FUNCTION),
  INSTRUCTION(Ids.INSTRUCTION),

  CONDITIONAL(Ids.CONDITIONAL);

  private int id;

  public boolean isInstruction() {
    int v = ~this.id | Ids.INSTRUCTION;
    return v == -1;
  }

  public boolean isConditional() {
    int v = ~this.id | Ids.CONDITIONAL;
    return v == -1;
  }

  public boolean isNoop() {
    return this.id == Ids.NOOP;
  }

  private TipoStatement(int id) {
    this.id = id;
  }
}
