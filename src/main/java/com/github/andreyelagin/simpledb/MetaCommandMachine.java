package com.github.andreyelagin.simpledb;

import static com.github.andreyelagin.simpledb.MetaCommandResult.META_COMMAND_SUCCESS;
import static com.github.andreyelagin.simpledb.MetaCommandResult.META_COMMAND_UNRECOGNIZED_COMMAND;

public class MetaCommandMachine {

  private final Table table;

  public MetaCommandMachine(Table table) {
    this.table = table;
  }

  public MetaCommandResult handle(String rawCommand) {
    if (".exit".equals(rawCommand)) {
      System.out.println("Goodbye bro...");
      System.exit(0);
    }

    if (".drop".equals(rawCommand)) {
      System.out.println("Dropping db bro...");
      table.clearTable();
      return META_COMMAND_SUCCESS;
    }

    return META_COMMAND_UNRECOGNIZED_COMMAND;
  }
}
