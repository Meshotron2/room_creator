package com.github.meshotron2.room_creator.cli.menus;

import com.github.meshotron2.cli_utils.menu.Menu;
import com.github.meshotron2.room_creator.Main;
import com.github.meshotron2.room_creator.Room;
import com.github.meshotron2.room_creator.cli.inputs.NodeInput;
import com.github.meshotron2.room_creator.cli.inputs.SphereInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomizerMenu extends Menu {
    private final Room room;

    public CustomizerMenu(Scanner scanner, String message, Room room) {
        super(Arrays.asList(
                "0) Sphere: 'S'",
                "1) Cuboid: 'C'",
                "2) Source: 's'",
                "3) Receiver: 'r'",
                "4) QUIT: 'q'"
        ), Main.PROMPT, scanner, message);

        this.room = room;
    }

    @Override
    protected void choose(int i) {
        switch (i) {
            case 0:
                final List<Integer> sphere = new SphereInput(getScanner()).validate();
                final char n = new NodeInput(getScanner(), "Choose node code").validate();

                try {
                    room.startWrite();
                    int x = sphere.get(0);
                    for (int x2 = 0; x2 <= x; x2++) {
                        int y = sphere.get(1);
                        for (int y2 = 0; y2 <= y; y2++) {
                            int z = sphere.get(2);
                            for (int z2 = 0; z2 <= z; z2++)
                                if (dist(x, x2, y, y2, z, z2) <= sphere.get(3))
                                    room.writeNode(n, x2, y2, z2); // nodes[x2][y2][z2] = n;
                        }
                    }
                    room.endWrite();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                getScanner().close();
                System.exit(0);
        }
    }

    private double dist(int x1, int x2, int y1, int y2, int z1, int z2) {
        int xDist = (x1 - x2) * (x1 - x2);
        int yDist = (y1 - y2) * (y1 - y2);
        int zDist = (z1 - z2) * (z1 - z2);

        return Math.sqrt(xDist + yDist + zDist);
    }
}
