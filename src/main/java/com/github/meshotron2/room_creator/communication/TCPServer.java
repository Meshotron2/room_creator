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
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Server to communicate with the GUI.
 *
 * This server receives the requests sent by the GUI. Those can be
 * <ul>
 *     <li>room descriptions</li>
 *     <li>room descriptions and plugin to use</li>
 * </ul>
 *
 * With help from
 * <ul>
 * <li><a href=https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip>codejava.net</a></li>
 * <li><a href=https://www.geeksforgeeks.org/multithreading-in-java/>geeksforgeeks.com</a></li>
 * <li><a href=https://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread>stackoverflow.com</a></li>
 * </ul>
 */
public class TCPServer extends Thread {

    /**
     * Port to bind the server to
     */
    private final int port;
    /**
     * The plugin manager to process the room
     */
    private final PluginManager pluginManager;

    public TCPServer(int port, PluginManager pluginManager) {
        this.port = port;
        this.pluginManager = pluginManager;
    }

    public void run() {
        try (final ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("Accept");

//                System.out.println("GUI connected");

//                final OutputStream output = socket.getOutputStream();
//                final PrintWriter writer = new PrintWriter(output, true);

                final InputStream input = socket.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                final String data = reader.readLine();

                System.out.println("Received " + data);

                final GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(RoomComponent.class, new ComponentDeserializer());
                builder.registerTypeAdapter(Request.class, new RequestDeserializer());
                builder.setPrettyPrinting();

                final Gson gson = builder.create();

//                final JSONRoom fromJson = gson.fromJson(data, JSONRoom.class);
                final Request<?> fromJson = gson.fromJson(data, Request.class);
                System.out.println("AFTER ALL: " + fromJson + fromJson.getData().getClass().getName());

//                if (fromJson.getData() instanceof String)
                switch (fromJson.getType()) {
                    // plugin request
                    case "plugin":
                        processPluginRequest(socket, fromJson);
                        break;

                    // plugin and room request
                    case "room_plugin":
                        processPluginRoomRequest(socket, fromJson);
                        break;

                    // room request
                    case "room_final":
                        final JSONRoom room = (JSONRoom) fromJson.getData();
                        System.out.println("Writing...");
                        room.write();
                        System.out.println("File written!");
                        SendFileClient.send(room.getFile());
                        break;
                }
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processPluginRoomRequest(Socket socket, Request<?> fromJson) throws IOException {
        System.out.println("REQUEST " + fromJson);
        final PluginAndRoom data = (PluginAndRoom) fromJson.getData();
        final String plugin = getPluginFromRequest(data.getPlugin());
        if (plugin == null) return;

        System.out.println("Chosen plugin: " + plugin);

        try {
            final String pluginResult = pluginManager.runMapToDwm(plugin, new Gson().toJson(data.getRoom()), ((PluginAndRoom) fromJson.getData()).getPlugin());
            System.out.println("DWM PLUGIN: " + pluginResult);
            socket.getOutputStream().write(
                    pluginResult.getBytes(StandardCharsets.UTF_8)
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processPluginRequest(Socket socket, Request<?> fromJson) throws IOException {
        final String plugin = getPluginFromRequest((String) fromJson.getData());
        if (plugin == null) return;

        System.out.println("Chosen plugin: " + plugin);
        System.out.println(fromJson.getData());

        try {
            final String pluginResult = pluginManager.runListMaterials(plugin, (String) fromJson.getData());
            System.out.println("LM PLUGIN: " + pluginResult);
            socket.getOutputStream().write(
                    pluginResult.getBytes(StandardCharsets.UTF_8)
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPluginFromRequest(String fileToProcess) {
        final String[] split = fileToProcess.split("\\.");
        final String extension = split[split.length - 1];

        System.out.println("Finding extension: " + extension);

        return pluginManager.getConfig().getEntries().stream()
                .filter(configEntry -> configEntry.getFileTypes().contains(extension))
                .findFirst()
                .map(ConfigEntry::getPluginFile)
                .stream().collect(Collectors.toList()).get(0);
    }
}
