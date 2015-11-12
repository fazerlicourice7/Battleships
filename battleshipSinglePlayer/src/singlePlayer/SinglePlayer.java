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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author fazer
 */
public class SinglePlayer {

    //variables
    static String[] locations;
    static String[] compLocations;

    BufferedReader READ = new BufferedReader(new InputStreamReader(System.in));

    //static variables
    static ArrayList numbers;
    static ArrayList letters;
    //grid that holds the grid that is displayed to the user
    static boolean displayCoordinates[][] = new boolean[10][10];
    //grid that holds the information about locations that have already been shot at.(Computer side)
    static boolean[][] compCoordinates = new boolean[10][10];
    //grid that holds the grid that is displayed to the user. (Computer Side)
    static boolean displayCompCoordinates[][] = new boolean[10][10];

    //instance variables
    getBattleships GetBattleships = new getBattleships();
    Easy EasyMode = new Easy();
    Normal NormalMode = new Normal();
    Hard HardMode = new Hard();
    setBattleships setThem = new setBattleships();

    public SinglePlayer() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                compCoordinates[i][j] = true;
            }
        }

        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");

        locations = new String[17];
        compLocations = new String[17];
    }

    /**
     * The initial interface between the user and the program
     */
    private void initialInterface() throws IOException {

        //Chose difficulty level
        System.out.println("Enter the difficulty level you wish to play at: \nEasy \nNormal \nHard");
        String difficultyLevel = READ.readLine();
        difficultyLevel = difficultyLevel.toLowerCase();

        locations = GetBattleships.allBattleships();
        compLocations = setThem.allBattlships();

        switch (difficultyLevel) {
            case "easy":
                EasyMode.init();
                EasyMode.run();
                break;
            case "normal":
                NormalMode.init();
                NormalMode.run();
                break;
            case "hard":
                HardMode.init();
                HardMode.run();
                break;
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String args[]) throws IOException {
        SinglePlayer obj = new SinglePlayer();
        obj.initialInterface();
    }
}
