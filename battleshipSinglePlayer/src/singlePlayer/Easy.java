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

import java.io.*;
import java.util.Random;
import static singlePlayer.SinglePlayer.coordinates;
import static singlePlayer.SinglePlayer.locations;

/**
 * @author fazer
 */
public class Easy {
    boolean[][] compCoordinates;
    setBattleships setThem;
    Random row, column;
    String[] compLocations;
    GameOver check;

    /**
     * Initialization Method
     */
    public void init() {
        compCoordinates = new boolean[10][10];
        compLocations = new String[17];
        check = new GameOver();
        setThem = new setBattleships();
        column = new Random();
        row = new Random();
    }

    /**
     *
     */
    public void run() {
        compLocations = setThem.allBattlships();
        while (true) {
            String Guess = guess();
            String coordinate[] = Guess.split(" ");
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            for (int i = 0; i < 17; i++) {
                if (locations[i].equals(String.valueOf(x) + "," + String.valueOf(y))) {
                    coordinates[x][y] = true;
                }
            }
            try {
                if (!coordinates[x][y]) {
                    coordinates[x][y] = false;
                }
            } catch (NullPointerException ex) {
                coordinates[x][y] = false;
            }
            //===================USER INPUT==================
            int inputX = 0, inputY = 0;
            for (int i = 0; i < 17; i++) {
                if (compLocations[i].equals(String.valueOf(inputX) + "," + String.valueOf(inputY))) {
                    compCoordinates[x][y] = true;
                }
            }
            try {
                if (!compCoordinates[x][y]) {
                    compCoordinates[x][y] = false;
                }
            } catch (NullPointerException ex) {
                compCoordinates[x][y] = false;
            }
            //Do some user interaction, print out new coordinates
            //check game over, who won?
            boolean computer = check.isGameOver(coordinates);
            boolean user = check.isGameOver(compCoordinates);
        }
    }

    public String guess() {
        int x, y;
        x = column.nextInt(10);
        y = row.nextInt(10);
        while (!coordinates[x][y]) {
            x = column.nextInt(10);
            y = row.nextInt(10);
        }
        coordinates[x][y] = false;
        String X = String.valueOf(x);
        String Y = String.valueOf(y);
        return X + " " + Y;
    }
}
