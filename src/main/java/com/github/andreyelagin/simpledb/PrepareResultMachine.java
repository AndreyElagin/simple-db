package com.github.andreyelagin.simpledb;

import java.util.regex.Pattern;

import static com.github.andreyelagin.simpledb.PrepareResult.PREPARE_SUCCESS;
import static com.github.andreyelagin.simpledb.PrepareResult.PREPARE_UNRECOGNIZED_STATEMENT;
import static com.github.andreyelagin.simpledb.StatementType.STATEMENT_INSERT;
import static com.github.andreyelagin.simpledb.StatementType.STATEMENT_SELECT;

public class PrepareResultMachine {

  private static final Pattern insert = Pattern.compile("insert (\\d+)\\s(\\w{1,32})\\s([\\w@.]{1,255})");

  public PrepareResult handle(String rawCommand, Statement.Builder builder) {
    PrepareResult result;
    switch (rawCommand.split(" ")[0]) {
      case "insert":
        builder.setType(STATEMENT_INSERT);
        builder.setRowToInsert(parseInsert(rawCommand));
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

  private Row parseInsert(String cmd) {
    var matcher = insert.matcher(cmd);
    if (matcher.find()) {
      return new Row(
          Integer.parseInt(matcher.group(1)),
          matcher.group(2),
          matcher.group(3)
      );
    } else {
      throw new RuntimeException("Can't parse insert statement: " + cmd);
    }
  }
}
