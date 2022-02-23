package com.github.meshotron2.room_creator.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class PluginManager {
    public static final String PLUGINS_FOLDER = "./plugins/";

    /**
     * Fetches all plugins declared on the configuration
     * @return all the locations for the plugin executables
     */
    public Set<String> getPlugins() {
        return new HashSet<>();
    }

    /**
     * Passes the file to the plugin
     * @param plugin The plugin to run
     * @param file The file to be passed to the plugin
     * @throws IOException From {@link ProcessBuilder#start()} or {@link BufferedReader#readLine()}
     * @throws InterruptedException From {@link Process#waitFor()}
     * @return The plugin's exit code
     */
    public int runPlugin(String plugin, String file) throws IOException, InterruptedException {
        // TODO: 1/12/22 Add command to configuration
        final ProcessBuilder pb = new ProcessBuilder().command("python3", "-u", plugin, file);
        pb.redirectErrorStream(true);

        final Process p = pb.start();
        final BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

//        final StringBuilder buffer = new StringBuilder();
//        String line;
//        while ((line = in.readLine()) != null) {
//            buffer.append(line);
//            buffer.append('\n');
////            prevLine = line;
//        }

        final int exitCode = p.waitFor();
//        System.out.println(buffer);

        return exitCode;
    }
}
