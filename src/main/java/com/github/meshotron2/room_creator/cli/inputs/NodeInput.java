package com.github.meshotron2.room_creator.cli.inputs;

import com.github.meshotron2.cli_utils.exceptions.MenuException;
import com.github.meshotron2.cli_utils.menu.input.Input;
import com.github.meshotron2.room_creator.Main;

import java.util.Scanner;

public class NodeInput extends Input<Character> {
    public static final char[] POSSIBLE_NODES = {
            ' ', 'S', 'R',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Z'};

    public NodeInput(Scanner scanner, String message) {
        super(Main.PROMPT, scanner, message);
    }

    @Override
    protected Character get(String s) throws MenuException {
        // TODO: 12/8/21 Validate air/surface node type codes
        char c = s.charAt(0);

        for (char c1: POSSIBLE_NODES)
            if (c == c1)
                return c;

        throw new MenuException("Not a valid node code");
    }
}
