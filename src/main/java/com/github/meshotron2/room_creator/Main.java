package com.github.meshotron2.room_creator;

import com.github.meshotron2.room_creator.communication.TCPServer;
import com.github.meshotron2.room_creator.plugins.PluginManager;

import java.io.IOException;

public class Main {
    public static final String PROMPT = "> ";

    public static void main(String[] args) throws IOException {
        new Thread(new TCPServer(9999, new PluginManager())).start();

//        final GsonBuilder builder = new GsonBuilder();
//        final Gson gson = builder.create();
//
//        final String s = Files.readString(Path.of("config.json"));
//
//        final Config c = gson.fromJson(s, Config.class);
//        System.out.println(c.toString());

//        try {
//            PluginManager pluginManager = new PluginManager();
//            System.out.println(pluginManager.runListMaterials("./plugins/test_plugin.py", "output"));
//            System.out.println(pluginManager.runMapToDwm("./plugins/test_plugin.py", "output"));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        final CLI cli = new CLI();
//        final Scanner scanner = new Scanner(System.in);
//        final Menu m = new MainMenu(scanner, "Choose an option");
//
//        cli.addMenu(m);
//        cli.start();
    }
}
