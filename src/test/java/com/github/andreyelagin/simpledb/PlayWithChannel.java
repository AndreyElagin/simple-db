package com.github.andreyelagin.simpledb;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

import static java.nio.file.StandardOpenOption.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayWithChannel {

  @Test
  void test() throws IOException {

    var channel = FileChannel.open(
        Path.of("storage.db"),
        Set.of(CREATE, READ, WRITE)
    );

    ByteBuffer buff = ByteBuffer.wrap("Hello world\n".getBytes(StandardCharsets.UTF_8));
    System.out.println(channel.position());
    System.out.println(channel.size());
    channel.write(buff);
    System.out.println(channel.size());
    System.out.println(channel.position());
    buff.flip();
    channel.write(buff);
    System.out.println(channel.size());
    System.out.println(channel.position());

    RandomAccessFile reader = new RandomAccessFile("storage.db", "r");
    assertEquals("Hello world", reader.readLine());
    reader.close();
  }
}
