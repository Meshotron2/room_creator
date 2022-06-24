package com.github.meshotron2.room_creator.plugins;

import com.github.meshotron2.room_creator.communication.SendFileClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class PluginManager {
    public static final String PLUGIN_CONFIG = "./plugins.json";

    private final Config config;

    public PluginManager() throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        final String s = Files.readString(Path.of(PLUGIN_CONFIG));

        config = gson.fromJson(s, Config.class);
        System.out.println(config);
    }

    /**
     * Fetches all plugins declared on the configuration
     *
     * @return all the locations for the plugin executables
     */
    public Set<String> getPlugins() {
        return config.getEntries().stream()
                .map(ConfigEntry::getPluginFile)
                .collect(Collectors.toSet());
    }

    /**
     * Runs the list materials phase
     *
     * @param plugin The plugin to run
     * @param file   The file to be processed by the plugin
     * @return The plugin's result. It should be in the format TODO
     * @throws IOException          From {@link ProcessBuilder#start()} or {@link BufferedReader#readLine()}
     * @throws InterruptedException From {@link Process#waitFor()}
     */
    public String runListMaterials(String plugin, String file) throws IOException, InterruptedException {

        if (!getPlugins().contains(plugin))
            return "Not found";

        // TODO: 2/23/22 Allow for different order of parameters
//        final String command = String.format(config.getPlugin(plugin).getListMaterials(), plugin, file);
        final String command = config.getPlugin(plugin).getListMaterials()
                .replaceAll("%script_file%", plugin)
                .replaceAll("%file%", file);
        System.out.println("RUN LM: " + command);
//        final ProcessBuilder pb = new ProcessBuilder().command(command.split(" "));
        final ProcessBuilder pb = new ProcessBuilder().command("/bin/sh", "-c", command);
        pb.redirectErrorStream(true);

        final Process p = pb.start();
        final BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        final StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
            buffer.append('\n');
//            prevLine = line;
        }

        return buffer.toString();
    }

    /**
     * Runs the Map to DWM phase
     *
     * @param plugin The plugin to run
     * @param data   The data to be passed to the plugin
     * @return The plugin's result. The plugin shall create the dwm file
     * @throws IOException          From {@link ProcessBuilder#start()} or {@link BufferedReader#readLine()}
     * @throws InterruptedException From {@link Process#waitFor()}
     */
    public String runMapToDwm(String plugin, String data, String file) throws IOException, InterruptedException {

        if (!getPlugins().contains(plugin))
            return "";

//        final String command = String.format(config.getPlugin(plugin).getMapToDwm(), plugin, data);
        final String command = config.getPlugin(plugin).getMapToDwm()
                .replaceAll("%script_file%", plugin)
                .replaceAll("%data%", Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8)))
                .replaceAll("%file%", file);

        System.out.println("RUN DWM: " + command);

//        final ProcessBuilder pb = new ProcessBuilder().command(command.split(" "));
        final ProcessBuilder pb = new ProcessBuilder().command("/bin/sh", "-c",
//                command.replaceAll("\"", "\\\"")
                command
        );
//        pb.redirectErrorStream(true);

        final Process p = pb.start();
        final BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        final StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
            buffer.append('\n');
//            prevLine = line;
        }

        final String folder = plugin.substring(0, plugin.lastIndexOf('/') + 1);
        SendFileClient.send(folder+"result.dwm");

        return buffer.toString();
    }

    public Config getConfig() {
        return config;
    }
}
