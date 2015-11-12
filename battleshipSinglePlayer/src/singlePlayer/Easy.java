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
import static singlePlayer.SinglePlayer.compCoordinates;
import static singlePlayer.SinglePlayer.displayCompCoordinates;
import static singlePlayer.SinglePlayer.displayCoordinates;
import static singlePlayer.SinglePlayer.locations;
import static singlePlayer.SinglePlayer.compLocations;

/**
 * @author fazer
 */
public class Easy {

    
    setBattleships setThem;
    Random row, column;
    
    GameOver check;
    int moves;

    /**
     * Initialization Method
     */
    public void init() {
       
        
        check = new GameOver();
        setThem = new setBattleships();
        column = new Random();
        row = new Random();
        moves = 0;
    }

    /**
     *
     */
    public void run() {
        while (true) {
            moves++;
            String Guess = guess();
            String coordinate[] = Guess.split(" ");
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            for (int i = 0; i < 17; i++) {
                if (locations[i].equals(String.valueOf(x) + "," + String.valueOf(y))) {
                    displayCoordinates[x][y] = true;
                }
            }
            try {
                if (!displayCoordinates[x][y]) {
                    displayCoordinates[x][y] = false;
                }
            } catch (NullPointerException ex) {
                displayCoordinates[x][y] = false;
            }
            
            //===================USER INPUT==================
            int inputX = 0, inputY = 0;
            for (int i = 0; i < 17; i++) {
                if (compLocations[i].equals(String.valueOf(inputX) + "," + String.valueOf(inputY))) {
                    displayCompCoordinates[x][y] = true;
                }
            }
            try {
                if (!displayCompCoordinates[x][y]) {
                    displayCompCoordinates[x][y] = false;
                }
            } catch (NullPointerException ex) {
                displayCompCoordinates[x][y] = false;
            }
            //Do some user interaction, print out new coordinates
            //check game over, who won?
            boolean computer = check.isGameOver(displayCoordinates);
            boolean user = check.isGameOver(displayCompCoordinates);
            if (computer && user) {
                check.GameOver("tie",moves);
                break;
            } else if (user) {
                check.GameOver("user",moves);
                break;
            } else if (computer) {
                check.GameOver("comp",moves);
                break;
            }
        }
    }

    public String guess() {
        int x, y;
        x = column.nextInt(10);
        y = row.nextInt(10);
        while (!compCoordinates[x][y]) {
            x = column.nextInt(10);
            y = row.nextInt(10);
        }
        compCoordinates[x][y] = false;
        String X = String.valueOf(x);
        String Y = String.valueOf(y);
        return X + " " + Y;
    }
}
