package com.github.meshotron2.room_creator.cli.inputs;

import com.github.meshotron2.cli_utils.exceptions.MenuException;
import com.github.meshotron2.cli_utils.menu.input.Input;
import com.github.meshotron2.room_creator.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileInput extends Input<String> {
    public FileInput(Scanner scanner, String message) {
        super(Main.PROMPT, scanner, message);
    }

    @Override
    protected String get(String s) throws MenuException {
        final Path path = Path.of(s);
        if (Files.exists(path))
            throw new MenuException("File already exists");

        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }
}
