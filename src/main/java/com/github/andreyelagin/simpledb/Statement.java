package com.github.andreyelagin.simpledb;

public class Statement {
  private StatementType type;
  private Row rowToInsert;

  public Statement() {
  }

  public Statement(StatementType type, Row rowToInsert) {
    this.type = type;
    this.rowToInsert = rowToInsert;
  }

  public Statement(StatementType type) {
    this.type = type;
  }

  public StatementType getType() {
    return type;
  }

  public Row getRowToInsert() {
    return rowToInsert;
  }

  public static class Builder {
    private StatementType type;
    private Row rowToInsert;

    public void setType(StatementType type) {
      this.type = type;
    }

    public void setRowToInsert(Row rowToInsert) {
      this.rowToInsert = rowToInsert;
    }

    public Statement build() {
      var statement = new Statement();

      if (type == null) throw new IllegalArgumentException("Statement type can't be null");
      statement.type = type;

      statement.rowToInsert = rowToInsert;

      return statement;
    }
  }
}
