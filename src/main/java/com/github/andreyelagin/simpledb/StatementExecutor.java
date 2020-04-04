package com.github.andreyelagin.simpledb;

public class StatementExecutor {

  public void execute(Statement statement) {
    switch (statement.getType()) {
      case STATEMENT_INSERT:
        System.out.println("This is where we would do an insert.");
        break;
      case STATEMENT_SELECT:
        System.out.println("This is where we would do a select.");
        break;
    }
  }
}
