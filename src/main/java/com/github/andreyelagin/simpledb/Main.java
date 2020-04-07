package com.github.andreyelagin.simpledb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    var prepareResultMachine = new PrepareResultMachine();
    var table = new Table();
    var metaCommandMachine = new MetaCommandMachine(table);
    var statementExecutor = new StatementExecutor(table);

    while (true) {
      try {
        printPrompt();
        var cmd = reader.readLine();
        if (cmd != null && cmd.startsWith(".")) {
          switch (metaCommandMachine.handle(cmd)) {
            case META_COMMAND_SUCCESS:
              System.out.printf("Command '%s' successfully executed\n", cmd);
              break;
            case META_COMMAND_UNRECOGNIZED_COMMAND:
              System.out.printf("Unrecognized command '%s'\n", cmd);
              break;
          }
        } else {

          var statementBuilder = new Statement.Builder();

          if (cmd != null) {
            switch (prepareResultMachine.handle(cmd, statementBuilder)) {
              case PREPARE_SUCCESS:
                break;
              case PREPARE_UNRECOGNIZED_STATEMENT:
                System.out.printf("An unrecognized keyword at start of '%s'.\n", cmd);
            }
          }

          var statement = statementBuilder.build();

          statementExecutor.execute(statement);
          System.out.println("Executed");
        }
      } catch (Throwable e) {
        System.err.println(e.getMessage());
      }
    }
  }

  private static void printPrompt() {
    System.out.print("simple-db > ");
  }
}
