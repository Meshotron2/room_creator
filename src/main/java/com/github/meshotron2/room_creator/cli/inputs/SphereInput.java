package com.github.meshotron2.room_creator.cli.inputs;

import com.github.meshotron2.cli_utils.exceptions.MenuException;
import com.github.meshotron2.cli_utils.menu.input.Input;
import com.github.meshotron2.cli_utils.menu.input.InputSequence;
import com.github.meshotron2.room_creator.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SphereInput extends InputSequence<List<Integer>> {

    public SphereInput(Scanner scanner) {
        super(Main.PROMPT, scanner, Arrays.asList(
                new CoordinateInput(scanner, "Centre x"),
                new CoordinateInput(scanner, "Centre y"),
                new CoordinateInput(scanner, "Centre z"),
                new CoordinateInput(scanner, "Radious")
        ));
    }

    @Override
    protected List<Integer> get(String s) throws MenuException {
        return (List<Integer>) getInputs().stream().map(Input::validate).collect(Collectors.toList());
    }
}
