package com.github.meshotron2.room_creator.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * with help from
 * <ul>
 * <li><a href=https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip>codejava.net</a></li>
 * <li><a href=https://www.geeksforgeeks.org/multithreading-in-java/>geeksforgeeks.com</a></li>
 * <li><a href=https://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread>stackoverflow.com</a></li>
 * </ul>
 */
public class TCPServer extends Thread {

    final int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void run() {
        try (final ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                final Socket socket = serverSocket.accept();

//                System.out.println("GUI connected");

//                final OutputStream output = socket.getOutputStream();
//                final PrintWriter writer = new PrintWriter(output, true);

                final InputStream input = socket.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                final String data = reader.readLine();

                final GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(RoomComponent.class, new ComponentDeserializer());
                builder.setPrettyPrinting();

                final Gson gson = builder.create();

                System.out.println(data);

                final JSONRoom jr = gson.fromJson(data, JSONRoom.class);

                final String jsonString = gson.toJson(jr);
                System.out.println(jsonString);
                System.out.println(jr);

                jr.write();
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
