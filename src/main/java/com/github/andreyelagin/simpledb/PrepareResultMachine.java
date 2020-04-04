package com.github.andreyelagin.simpledb;

import static com.github.andreyelagin.simpledb.PrepareResult.PREPARE_SUCCESS;
import static com.github.andreyelagin.simpledb.PrepareResult.PREPARE_UNRECOGNIZED_STATEMENT;
import static com.github.andreyelagin.simpledb.StatementType.STATEMENT_INSERT;
import static com.github.andreyelagin.simpledb.StatementType.STATEMENT_SELECT;

public class PrepareResultMachine {

  public PrepareResult handle(String rawCommand, Statement.Builder builder) {
    PrepareResult result;
    switch (rawCommand) {
      case "insert":
        builder.setType(STATEMENT_INSERT);
        result = PREPARE_SUCCESS;
        break;
      case "select":
        builder.setType(STATEMENT_SELECT);
        result = PREPARE_SUCCESS;
        break;
      default:
        result = PREPARE_UNRECOGNIZED_STATEMENT;
    }

    return result;
  }
}
