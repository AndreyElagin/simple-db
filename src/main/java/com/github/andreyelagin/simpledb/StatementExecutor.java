package com.github.andreyelagin.simpledb;

public class StatementExecutor {

  private final Table table;

  public StatementExecutor(Table table) {
    this.table = table;
  }

  public void execute(Statement statement) {
    switch (statement.getType()) {
      case STATEMENT_INSERT:
        table.addRow(statement.getRowToInsert());
        break;
      case STATEMENT_SELECT:
        table.getAllRows().forEach(System.out::println);
        break;
    }
  }
}
