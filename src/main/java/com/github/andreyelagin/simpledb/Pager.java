package com.github.andreyelagin.simpledb;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Set;

import static com.github.andreyelagin.simpledb.Table.*;
import static java.nio.file.StandardOpenOption.*;

public class Pager {

  private final FileChannel storage;
  private final ByteBuffer[] pages;

  public Pager(String path) throws IOException {
    storage = FileChannel.open(Path.of(path), Set.of(CREATE, READ, WRITE));
    pages = new ByteBuffer[TABLE_MAX_PAGES];
  }

  public ByteBuffer getPage(int pageNumber) {
    var page = pages[pageNumber];
    if (page == null) {
      page = ByteBuffer.allocateDirect(PAGE_SIZE);
      pages[pageNumber] = page;
    }

    return pages[pageNumber];
  }
}
