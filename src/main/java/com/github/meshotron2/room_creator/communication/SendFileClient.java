package com.github.meshotron2.room_creator.communication;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class SendFileClient {

    public static void main(String[] args) {
        new SendFileClient().send();
    }

    public void send() {
        try (Socket socket = new Socket("localhost", 5000)) {
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendFile("yeet", dataOutputStream); // colocar caminho do ficheiro

            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFile(String path, DataOutputStream dataOutputStream) throws Exception {
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
    }
}
