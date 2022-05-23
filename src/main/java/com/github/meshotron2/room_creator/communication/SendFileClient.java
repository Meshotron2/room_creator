package com.github.meshotron2.room_creator.communication;

import java.io.*;
import java.net.Socket;

/**
 * Set of methods to send files.
 * <p>
 * The protocol implemented sends 0x0 (signifying a file transfer),
 * followed by 64 bits (long) encoding the size and then the file's bytes.
 */
public interface SendFileClient {

//    static void main(String[] args) {
//        send("final_test.dwm");
//    }

    /**
     * Sends a file
     *
     * @param fileName the name of the file to send
     */
    static void send(String fileName) {
        System.out.println("SEND");
        try (final Socket socket = new Socket("127.0.0.1", 5000)) {
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendFile(fileName, dataOutputStream); // colocar caminho do ficheiro

            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a file to a specific output stream.
     *
     * @param path the path were the file is located.
     * @param dataOutputStream the output stream to send the file to
     * @throws IOException when errors happen on file/output stream handling
     */
    private static void sendFile(String path, DataOutputStream dataOutputStream) throws IOException {
        int bytes;
        final File file = new File(path);
        final FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeByte(0x0);
        dataOutputStream.writeLong(file.length());

        final byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
        System.out.println("Finished transfer");
    }
}
