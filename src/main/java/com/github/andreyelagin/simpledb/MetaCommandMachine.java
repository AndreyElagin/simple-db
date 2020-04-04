package com.github.andreyelagin.simpledb;

import static com.github.andreyelagin.simpledb.MetaCommandResult.META_COMMAND_UNRECOGNIZED_COMMAND;

public class MetaCommandMachine {

  public MetaCommandResult handle(String rawCommand) {
    if (".exit".equals(rawCommand)) {
      System.out.println("Goodbye bro...");
      System.exit(0);
    }

    System.out.printf("Unrecognized command '%s'\n", rawCommand);
    return META_COMMAND_UNRECOGNIZED_COMMAND;
  }
}
