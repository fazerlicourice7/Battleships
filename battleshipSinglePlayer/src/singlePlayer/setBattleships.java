/*
 * Copyright (C) 2015 18balanagav
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

import static singlePlayer.SinglePlayer.coordinates;
import java.util.Random;

/**
 *
 * @author 18balanagav
 */
public class setBattleships {

    String[] compLocations;
    Random random = new Random();

    /**
     * Constructor
     */
    public setBattleships() {
        compLocations = new String[17];
    }

    /**
     * Calls and sets the location of the computers respective battleships
     *
     * @return
     */
    public String[] allBattlships() {
        //calls the method to get the location of the first battleship
        String battleship1 = Destroyer();
        String location[] = battleship1.split(" ");
        compLocations[0] = location[0];
        compLocations[1] = location[1];
        location = null;

        //calls the method to get the location of the second battleship
        String battleship2 = SubmarineCruiser();
        String location2[] = battleship2.split(" ");
        compLocations[2] = location2[0];
        compLocations[3] = location2[1];
        compLocations[4] = location2[2];
        location2 = null;

        //calls the method to get the location of the third battleship
        String battleship3 = SubmarineCruiser();
        String location3[] = battleship3.split(" ");
        compLocations[5] = location3[0];
        compLocations[6] = location3[1];
        compLocations[7] = location3[2];
        location3 = null;

        //calls the method to get the location of the fourth battleship
        String battleship4 = Battleship();
        String location4[] = battleship4.split(" ");
        compLocations[8] = location4[0];
        compLocations[9] = location4[1];
        compLocations[10] = location4[2];
        compLocations[11] = location4[3];
        location4 = null;

        //calls the method to get the location of the fifth battleship
        String battleship5 = AircraftCarrier();
        String location5[] = battleship5.split(" ");
        compLocations[12] = location5[0];
        compLocations[13] = location5[1];
        compLocations[14] = location5[2];
        compLocations[15] = location5[3];
        compLocations[16] = location5[4];
        location5 = null;

        return compLocations;
    }

    /**
     * Length of 5
     *
     * @return
     */
    private String AircraftCarrier() {
        String locations = "";
        //first choose vertical / horizontal
        boolean vertical = random.nextBoolean();
        //then choose column / row
        int rowColumn = random.nextInt(10);
        //then choose locations
        int[] current = new int[5];
        for (int i = 0; i < 5; i++) {
            current[i] = random.nextInt(10);
        }
        //create a string with all the values
        if (vertical) {
            int column = rowColumn;
            for (int i = 0; i < 5; i++) {
                locations += String.valueOf(column) + "," + String.valueOf(current[i]) + " ";
            }
        } else {
            int row = rowColumn;
            for (int i = 0; i < 5; i++) {
                locations += String.valueOf(current[i]) + "," + String.valueOf(row) + " ";
            }
        }
        locations = locations.substring(0, locations.length() - 1);
        return locations;
    }

    /**
     * Length of 4
     *
     * @return
     */
    private String Battleship() {
        String locations = "";
        //first choose vertical / horizontal
        boolean vertical = random.nextBoolean();
        //then choose column / row
        int rowColumn = random.nextInt(10);
        //then choose locations
        int[] current = new int[5];
        for (int i = 0; i < 4; i++) {
            current[i] = random.nextInt(10);
        }
        //create a string with all the values
        if (vertical) {
            int column = rowColumn;
            for (int i = 0; i < 4; i++) {
                locations += String.valueOf(column) + "," + String.valueOf(current[i]) + " ";
            }
        } else {
            int row = rowColumn;
            for (int i = 0; i < 4; i++) {
                locations += String.valueOf(current[i]) + "," + String.valueOf(row) + " ";
            }
        }
        locations = locations.substring(0, locations.length() - 1);
        return locations;
    }

    /**
     * Length of three
     *
     * @return
     */
    private String SubmarineCruiser() {
        String locations = "";
        //first choose vertical / horizontal
        boolean vertical = random.nextBoolean();
        //then choose column / row
        int rowColumn = random.nextInt(10);
        //then choose locations
        int[] current = new int[5];
        for (int i = 0; i < 3; i++) {
            current[i] = random.nextInt(10);
        }
        //create a string with all the values
        if (vertical) {
            int column = rowColumn;
            for (int i = 0; i < 3; i++) {
                locations += String.valueOf(column) + "," + String.valueOf(current[i]) + " ";
            }
        } else {
            int row = rowColumn;
            for (int i = 0; i < 3; i++) {
                locations += String.valueOf(current[i]) + "," + String.valueOf(row) + " ";
            }
        }
        locations = locations.substring(0, locations.length() - 1);
        return locations;
    }

    /**
     * Length of 2
     *
     * @return
     */
    public String Destroyer() {
        String locations = "";
        //first choose vertical / horizontal
        boolean vertical = random.nextBoolean();
        //then choose column / row
        int rowColumn = random.nextInt(10);
        //then choose locations
        int[] current = new int[5];
        for (int i = 0; i < 2; i++) {
            current[i] = random.nextInt(10);
        }
        //create a string with all the values
        if (vertical) {
            int column = rowColumn;
            for (int i = 0; i < 2; i++) {
                locations += String.valueOf(column) + "," + String.valueOf(current[i]) + " ";
            }
        } else {
            int row = rowColumn;
            for (int i = 0; i < 2; i++) {
                locations += String.valueOf(current[i]) + "," + String.valueOf(row) + " ";
            }
        }
        locations = locations.substring(0, locations.length() - 1);
        return locations;
    }
}
