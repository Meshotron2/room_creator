package com.github.meshotron2.room_creator.cli.menus;

import com.github.meshotron2.cli_utils.menu.Menu;
import com.github.meshotron2.room_creator.Main;
import com.github.meshotron2.room_creator.Room;
import com.github.meshotron2.room_creator.cli.inputs.RoomInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu extends Menu {

    private Room room = null;

    public MainMenu(Scanner scanner, String message) {
        super(Arrays.asList(
                "0) Specify room dimensions, frequency and file",
                "1) Customize room",
                "2) Exit"
        ), Main.PROMPT, scanner, message);
    }

    @Override
    protected void choose(int i) {
        switch (i) {
            case 0:
                room = new RoomInput(getScanner()).validate();

                try {
                    room.startWrite();

                    int j = 0;
                    int totalNodes = room.getX() * room.getY() * room.getZ();
                    while (j++ < totalNodes)
                        room.writeNode(' ');

                    room.endWrite();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                new CustomizerMenu(getScanner(), "Room customizer", room).display();
                break;
            case 2:
                getScanner().close();
                System.exit(0);
        }
    }
}
