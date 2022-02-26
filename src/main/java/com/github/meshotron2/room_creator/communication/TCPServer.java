package com.github.meshotron2.room_creator.communication;

import com.github.meshotron2.room_creator.plugins.ConfigEntry;
import com.github.meshotron2.room_creator.plugins.PluginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

/**
 * with help from
 * <ul>
 * <li><a href=https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip>codejava.net</a></li>
 * <li><a href=https://www.geeksforgeeks.org/multithreading-in-java/>geeksforgeeks.com</a></li>
 * <li><a href=https://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread>stackoverflow.com</a></li>
 * </ul>
 */
public class TCPServer extends Thread {

    private final int port;
    private final PluginManager pluginManager;

    public TCPServer(int port, PluginManager pluginManager) {
        this.port = port;
        this.pluginManager = pluginManager;
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
                builder.registerTypeAdapter(RoomComponent.class, new RequestDeserializer());
                builder.setPrettyPrinting();

                final Gson gson = builder.create();

                System.out.println(data);

//                final JSONRoom fromJson = gson.fromJson(data, JSONRoom.class);
                final Request<?> fromJson = gson.fromJson(data, Request.class);

                // plugin request
                if (fromJson.getData() instanceof String) {
                    final String[] split = ((String) fromJson.getData()).split("\\.");
                    final String extension = split[split.length - 1];

                    final String plugin = pluginManager.getConfig().getEntries().stream()
                            .filter(configEntry -> configEntry.getFileTypes().contains(extension))
                            .findFirst()
                            .map(ConfigEntry::getPluginFile)
                            .stream().collect(Collectors.toList()).get(0);

                    if (plugin == null)
                        return;

                    try {
                        pluginManager.runListMaterials(plugin, (String) fromJson.getData());
                        pluginManager.runMapToDwm(plugin, (String) fromJson.getData());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                final String jsonString = gson.toJson(fromJson);
                System.out.println(jsonString);
                System.out.println(fromJson);

//                fromJson.write();
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
