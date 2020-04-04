package com.github.andreyelagin.simpledb;

public class Statement {
  private StatementType type;

  public Statement() {
  }

  public Statement(StatementType type) {
    this.type = type;
  }

  public StatementType getType() {
    return type;
  }

  public static class Builder {
    private StatementType type;

    public void setType(StatementType type) {
      this.type = type;
    }

    public Statement build() {
      var statement = new Statement();

      if (type == null) throw new IllegalArgumentException("Statement type can't be null");
      statement.type = type;

      return statement;
    }
  }
}
