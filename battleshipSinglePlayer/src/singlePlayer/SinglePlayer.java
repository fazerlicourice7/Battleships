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
    String[] locations;

    BufferedReader READ = new BufferedReader(new InputStreamReader(System.in));

    //static variables
    static ArrayList numbers;
    static ArrayList letters;

    //instance variables
    getBattleships GetBattleships = new getBattleships();
    Easy EasyMode = new Easy();
    Normal NormalMode = new Normal();
    Hard HardMode = new Hard();

    public SinglePlayer() {
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
    }

    /**
     *
     */
    private void initialInterface() throws IOException {

        //Chose difficulty level
        System.out.println("Enter the difficulty level you wish to play at: \nEasy \nNormal \nHard");
        String difficultyLevel = READ.readLine();
        difficultyLevel = difficultyLevel.toLowerCase();

        //calls the method to get the location of the first battleship
        String battleship1 = GetBattleships.battleship1();
        String location[] = battleship1.split(" ");
        locations[0] = location[0];
        locations[1] = location[1];
        location = null;

        //calls the method to get the location of the second battleship
        String battleship2 = GetBattleships.battleship23("second");
        String location2[] = battleship2.split(" ");
        locations[2] = location2[0];
        locations[3] = location2[1];
        locations[4] = location2[2];
        location2 = null;

        //calls the method to get the location of the third battleship
        String battleship3 = GetBattleships.battleship23("third");
        String location3[] = battleship3.split(" ");
        locations[5] = location3[0];
        locations[6] = location3[1];
        locations[7] = location3[2];
        location3 = null;

        //calls the method to get the location of the fourth battleship
        String battleship4 = GetBattleships.battleship4();
        String location4[] = battleship4.split(" ");
        locations[8] = location4[0];
        locations[9] = location4[1];
        locations[10] = location4[2];
        locations[11] = location4[3];
        location4 = null;

        //calls the method to get the location of the fifth battleship
        String battleship5 = GetBattleships.battleship5();
        String location5[] = battleship5.split(" ");
        locations[12] = location5[0];
        locations[13] = location5[1];
        locations[14] = location5[2];
        locations[15] = location5[3];
        locations[16] = location5[4];
        location5 = null;

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
    public static void main(String args[]) {

    }
}
