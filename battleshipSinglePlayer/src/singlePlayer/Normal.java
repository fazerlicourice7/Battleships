/*
 * Copyright (C) 2015 fazer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package singlePlayer;

import java.util.ArrayList;
import java.util.Random;
import static singlePlayer.SinglePlayer.compCoordinates;

/**
 * @author fazer
 */
public class Normal {

    Random random = new Random();
    ArrayList<String> targetList;

    /**
     * Initialization method
     */
    public void init() {
        targetList = new ArrayList<>();
    }

    /**
     *
     */
    public void run() {

    }

    /**
     * Method that guesses the coordinates in the target list until it is empty.
     * This method only gets called if the targetList has a size > 0.
     */
    public void target() {

    }

    /**
     * Method that randomly-using the parity method- guesses locations to fire
     * at
     *
     * Parity Method: only guesses squares that are located at indices that are
     * the same type(odd/even). Example: coordinates[0][4] coordinates[3][7]
     */
    public int[] hunt() {
        int x, y;
        int[] coordinates = new int[2];
        x = random.nextInt(10);
        y = random.nextInt(10);
        while (!compCoordinates[x][y]) {
            x = random.nextInt(10);
            y = random.nextInt(10);
            if (x % 2 == y % 2) { // something's wrong here - might be fixed
                continue;
            }
        }
        compCoordinates[x][y] = false;
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }
}
