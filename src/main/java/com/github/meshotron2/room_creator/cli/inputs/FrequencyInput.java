package com.github.meshotron2.room_creator.cli.inputs;

import com.github.meshotron2.cli_utils.exceptions.MenuException;
import com.github.meshotron2.cli_utils.menu.Menu;
import com.github.meshotron2.cli_utils.menu.input.Input;
import com.github.meshotron2.room_creator.Main;
import com.github.meshotron2.room_creator.cli.menus.FrequencyMenu;

import java.util.Scanner;

public class FrequencyInput extends Input<Long> {
    public FrequencyInput(Scanner scanner, String message) {
        super(Main.PROMPT, scanner, message);
    }

    @Override
    protected Long get(String s) throws MenuException {
        final Menu m = new FrequencyMenu(getScanner(), "Please select the sampling frequency");
        m.display();

        final int opt = m.getChoice();
        final long[] freqs = { 48000, 44100, 22050, 16000, 11025, 8000 };

        return freqs[opt];
    }

    @Override
    public Long validate() {
        while(true) {
            try {
                return this.get(null);
            } catch (MenuException e) {
                e.display();
            }
        }
    }
}
