package com.github.meshotron2.room_creator.communication;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public interface SendFileClient {

    static void main(String[] args) {
        send("final_test.dwm");
    }

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

    private static void sendFile(String path, DataOutputStream dataOutputStream) throws Exception {
        int bytes;
        final File file = new File(path);
        final FileInputStream fileInputStream = new FileInputStream(file);


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
