package com.github.meshotron2.room_creator.cli.inputs;

import com.github.meshotron2.cli_utils.exceptions.MenuException;
import com.github.meshotron2.cli_utils.menu.input.Input;
import com.github.meshotron2.cli_utils.menu.input.InputSequence;
import com.github.meshotron2.room_creator.Main;
import com.github.meshotron2.room_creator.Room;

import java.util.Arrays;
import java.util.Scanner;

public class RoomInput extends InputSequence<Room> {
    public RoomInput(Scanner scanner) {
        super(Main.PROMPT, scanner, Arrays.asList(
                new FileInput(scanner, "File to store the room"),
                new CoordinateInput(scanner, "width (x)"),
                new CoordinateInput(scanner, "length (y)"),
                new CoordinateInput(scanner, "height (z)"),
                new FrequencyInput(scanner, "frequency (f)")
        ));
    }

    @Override
    protected Room get(String s) throws MenuException {
        return new Room(
                (String) getInputs().get(0).validate(),
                (int) getInputs().get(1).validate(),
                (int) getInputs().get(2).validate(),
                (int) getInputs().get(3).validate(),
                (long) getInputs().get(4).validate()
        );
    }
}
