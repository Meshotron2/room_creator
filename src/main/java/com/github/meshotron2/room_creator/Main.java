package com.github.meshotron2.room_creator;

import com.github.meshotron2.cli_utils.CLI;
import com.github.meshotron2.cli_utils.menu.Menu;
import com.github.meshotron2.room_creator.cli.menus.MainMenu;

import java.util.Scanner;

public class Main {
    public static final String PROMPT = "> ";

    public static void main(String[] args) {
        final CLI cli = new CLI();
        final Scanner scanner = new Scanner(System.in);
        final Menu m = new MainMenu(scanner, "Choose an option");

        cli.addMenu(m);
        cli.start();
    }
}
