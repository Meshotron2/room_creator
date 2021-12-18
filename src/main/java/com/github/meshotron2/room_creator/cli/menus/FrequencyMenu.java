package com.github.meshotron2.room_creator.cli.menus;

import com.github.meshotron2.cli_utils.menu.Menu;
import com.github.meshotron2.room_creator.Main;

import java.util.Arrays;
import java.util.Scanner;

public class FrequencyMenu extends Menu {
    public FrequencyMenu(Scanner scanner, String message) {
        super(Arrays.asList(
                "0) 48000",
                "1) 44100",
                "2) 22050",
                "3) 16000",
                "4) 11025",
                "5) 8000"
        ), Main.PROMPT, scanner, message);
    }

    @Override
    protected void choose(int i) {}
}
