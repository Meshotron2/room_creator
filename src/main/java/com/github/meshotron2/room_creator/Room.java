package com.github.meshotron2.room_creator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Room {
    private final String file;

    private final int x;
    private final int y;
    private final int z;

    private final long f;

    private InputStream reader;
    private OutputStream writer;
    private RandomAccessFile randWriter;

    public Room(String file, int x, int y, int z, long f) {
        this.file = file;
        this.x = x;
        this.y = y;
        this.z = z;
        this.f = f;
    }

    public void startRead() throws IOException {
        if (file == null)
            throw new IllegalStateException("Room file name cannot be null");

        this.reader = new FileInputStream(file);
        reader.skip(24); // skip until first char
    }

    public void endRead() throws IOException {
        reader.close();
    }

    public char readNode() throws IOException {
        final byte[] bs = new byte[2];

        int status = reader.read(bs);
        if (status != 2) return '\n';

        return (char) (((char) bs[0]) << 8 | ((char) bs[1]));
    }

    public void startWrite() throws IOException {
        if (file == null)
            throw new IllegalStateException("Room file name cannot be null");

        final Path path = Path.of(file);
        if (Files.exists(path))
            Files.delete(path);

        Files.createFile(path);

        this.writer = new FileOutputStream(file);

        this.writer.write(x >> 24);
        this.writer.write(x >> 16);
        this.writer.write(x >> 8);
        this.writer.write(x);

        System.out.println("x: " + (byte) (x >> 24) + (byte) (x >> 16) + (byte) (x >> 8) + (byte) (x));

        this.writer.write(y >> 24);
        this.writer.write(y >> 16);
        this.writer.write(y >> 8);
        this.writer.write(y);

        System.out.println("y: " + (byte) (y >> 24) + (byte) (y >> 16) + (byte) (y >> 8) + (byte) (y));

        this.writer.write(z >> 24);
        this.writer.write(z >> 16);
        this.writer.write(z >> 8);
        this.writer.write(z);

        System.out.println("z: " + (byte) (z >> 24) + (byte) (z >> 16) + (byte) (x >> 8) + (byte) (z));

//        https://stackoverflow.com/questions/10686178/convert-long-to-two-int-and-vice-versa

        final int f1 = (int)(f >> 32);
        this.writer.write(f1 >> 24);
        this.writer.write(f1 >> 16);
        this.writer.write(f1 >> 8);
        this.writer.write(f1);

        final int f2 = (int)f;
        this.writer.write(f2 >> 24);
        this.writer.write(f2 >> 16);
        this.writer.write(f2 >> 8);
        this.writer.write(f2);

        System.out.println("f: " + ((long)f1 << 32 | f2 & 0xFFFFFFFFL));
    }

    public void endWrite() throws IOException {
        writer.close();
    }

    public void writeNode(char c) throws IOException {
        writer.write(c);
    }

    public void writeNode(char c, int x, int y, int z) throws IOException {
        final int n = 24 + x * y * z;

        byte[] bytes = {(byte) (c >> 8), (byte) c};
//        System.out.println(bytes.length);
//        writer.write(bytes, n, 2);

        randWriter = new RandomAccessFile(file, "rw");
        randWriter.seek(n);
        randWriter.write(bytes);
    }

    public static Room fromFile(String file) throws IOException {
        final InputStream reader = new FileInputStream(file);

        final byte[] bs = new byte[4];

        int status = reader.read(bs);
        if (status != 4) return null;
        final int x = ((int) bs[0]) << 24 | ((int) bs[1]) << 16 | ((int) bs[2]) << 8 | ((int) bs[3]);

        status = reader.read(bs);
        if (status != 4) return null;
        final int y = ((int) bs[0]) << 24 | ((int) bs[1]) << 16 | ((int) bs[2]) << 8 | ((int) bs[3]);

        status = reader.read(bs);
        if (status != 4) return null;
        final int z = ((int) bs[0]) << 24 | ((int) bs[1]) << 16 | ((int) bs[2]) << 8 | ((int) bs[3]);

        // TODO: 12/6/21 Simplify
        status = reader.read(bs);
        if (status != 4) return null;
        final int f1 = ((int) bs[0]) << 24 | ((int) bs[1]) << 16 | ((int) bs[2]) << 8 | ((int) bs[3]);
        status = reader.read(bs);
        if (status != 4) return null;
        final int f2 = ((int) bs[0]) << 24 | ((int) bs[1]) << 16 | ((int) bs[2]) << 8 | ((int) bs[3]);

        final long f = ((long) f2) << 32 | f1;

        System.out.println("f: " + f);
        reader.close();
        return new Room(file, x, y, z, f);
    }

    public String getFile() {
        return file;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public long getF() {
        return f;
    }
}