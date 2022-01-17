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

    private DataInputStream reader;
    private DataOutputStream writer;
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

        this.reader = new DataInputStream(new FileInputStream(file));
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

        this.writer = new DataOutputStream(new FileOutputStream(file));

        this.writer.writeInt(Integer.reverseBytes(x));
        this.writer.writeInt(Integer.reverseBytes(y));
        this.writer.writeInt(Integer.reverseBytes(z));

        this.writer.writeLong(Long.reverseBytes(f));
    }

    public void endWrite() throws IOException {
        writer.close();
    }

    public void writeNode(char c) throws IOException {
        writer.write(new byte[]{(byte) c, (byte) (c >> 8)});
    }

    public void writeNode(char c, int x, int y, int z) throws IOException {
        final int n = 24 + x * y * z;

        byte[] bytes = {(byte) (c >> 8), (byte) c};
//        System.out.println(bytes.length);
//        writer.write(bytes, n, 2);

        randWriter = new RandomAccessFile(file, "rw");
        randWriter.seek(n);
        randWriter.write(bytes);
        randWriter.close();
    }

    public static Room fromFile(String file) throws IOException {
        final DataInputStream reader = new DataInputStream(new FileInputStream(file));

        final int x = Integer.reverseBytes(reader.readInt());
        final int y = Integer.reverseBytes(reader.readInt());
        final int z = Integer.reverseBytes(reader.readInt());

        final long f = Long.reverseBytes(reader.readLong());

        reader.close();
        return new Room(file, x, y, z, f);
    }

    public void doSphere(int x, int y, int z, int r, char n) throws IOException {
//        startWrite();
        int ix = x;
        for (int x2 = 0; x2 <= ix; x2++) {
            int iy = y;
            for (int y2 = 0; y2 <= iy; y2++) {
                int iz = z;
                for (int z2 = 0; z2 <= iz; z2++)
                    if (dist(ix, x2, iy, y2, iz, z2) <= r)
                        writeNode(n, x2, y2, z2); // nodes[x2][y2][z2] = n;
                    else
                        writeNode(' ', x2, y2, z2);
            }
        }
//        endWrite();
    }

    public void doCuboid(int x1, int x2, int y1, int y2, int z1, int z2, char n) throws IOException {
        for (int xi = x1; xi <= x2; x2++) {
            for (int yi = y1; yi <= y2; y2++) {
                for (int zi = z1; zi <= z2; z2++)
                    writeNode(n, xi, yi, zi); // nodes[x2][y2][z2] = n;
            }
        }
    }

    private double dist(int x1, int x2, int y1, int y2, int z1, int z2) {
        final int xDist = (x1 - x2) * (x1 - x2);
        final int yDist = (y1 - y2) * (y1 - y2);
        final int zDist = (z1 - z2) * (z1 - z2);

        return Math.sqrt(xDist + yDist + zDist);
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
