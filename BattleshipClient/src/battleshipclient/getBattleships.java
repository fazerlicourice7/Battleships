/*
 * Copyright (C) 2015 fazerlicourice71256
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
package battleshipclient;

import static battleshipclient.Battleship_client.letters;
import static battleshipclient.Battleship_client.numbers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class contains filters for the locations of the different sized battleships.
 * Only if they pass all the filters are they acceptable locations and will then be returned to the main method.
 * @author fazer
 */
public class getBattleships {

    String input;
    BufferedReader READ = new BufferedReader(new InputStreamReader(System.in));
    //used to manipulate each individual coordinate to verify its integrity with respect to the other coordinates of that battleship
    int x1, x2, x3, x4, x5;
    int y1, y2, y3, y4, y5;
    //used along with arraylist to locate the corresponding numbers and letters
    int index1, index2, index3, index4, index5;

    /**
     * This method gets the location of the first battleship from the player.
     * First battleship has a length of two.
     *
     * @return
     * @throws IOException
     */
    public String battleship1() throws IOException {
        //this method gets the coordinates for the players first battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your first battleship (length of 2)");
            input = READ.readLine();
            if (input.charAt(input.length() - 1) == ' ') { //if the first or last char is a space, remove it
                input = input.substring(0, input.length() - 1);
            } else if (input.charAt(0) == ' ') {
                input = input.substring(1);
            }
            if (input.length() != 5) { //checks if the input has the correct length
                if (input.length() > 5) {
                    System.out.println("Too many characters input.");
                    System.out.println("Please Try again.");
                } else {
                    System.out.println("Not enough characters input");
                    System.out.println("Please Try again.");
                }
                System.out.println("Enter location in the format 'LetterNumber LetterNumber' as demonstrated earlier.");
                continue;
            }
            //if the length is correct, splits up the input to individual x and y coordinates to further manipulate them
            index1 = letters.indexOf(input.toUpperCase().charAt(0));
            index2 = letters.indexOf(input.toUpperCase().charAt(3));
            try {
                x1 = (int) numbers.get(index1);
                y1 = (int) input.charAt(1);
                x2 = (int) numbers.get(index2);
                y2 = (int) input.charAt(4);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Error! \nPlease retry");
                continue;
            }
            if (x1 == x2) { //checks if the battleship is either vertical or horizontal
                if (y1 - 1 != y2 && y1 + 1 != y2) { //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                    System.out.println("This Battleship does not occupy two immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2) {
                if (x1 - 1 != x2 && x1 + 1 != x2) { //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                    System.out.println("This Battleship does not occupy two immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (x1 == x2 && y1 == y2) { //checks if the same coordinate has been entered multiple times
                System.out.println("You have entered the same coordinate twice.");
                System.out.println("Please Try again.");
                continue;
            } else { //checks if the battleship is diagonal 
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            break; //exit the loop if it passes all filters
        }
        return input;
    }

    /**
     * Gets the location of the second and third battleships from the player,
     * both of which have lengths of 3.
     *
     * @param number - tells the method which battleship it's dealing with(2 or
     * 3) only used for text output.
     * @return
     * @throws IOException
     */
    public String battleship23(String number) throws IOException {
        //this method gets the coordinates for the players second/third battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your " + number + " battleship (length of 3)");
            input = READ.readLine();
            if (input.charAt(input.length() - 1) == ' ') { //if the first or last char is a space, remove it
                input = input.substring(0, input.length() - 1);
            } else if (input.charAt(0) == ' ') {
                input = input.substring(1);
            }
            if (input.length() != 8) { //checks if the input has the correct length
                if (input.length() > 8) {
                    System.out.println("Too many characters input.");
                    System.out.println("Please Try again.");
                } else {
                    System.out.println("Not enough characters input");
                    System.out.println("Please Try again.");
                }
                System.out.println("Enter location in the format 'LetterNumber LetterNumber LetterNumber' as demonstrated earlier.");
                continue;
            }
            //if the length is correct, splits up the input to individual x and y coordinates to further manipulate them
            index1 = letters.indexOf(input.toUpperCase().charAt(0));
            index2 = letters.indexOf(input.toUpperCase().charAt(3));
            index3 = letters.indexOf(input.toUpperCase().charAt(6));
            try {
                x1 = (int) numbers.get(index1);
                y1 = (int) input.charAt(1);
                x2 = (int) numbers.get(index2);
                y2 = (int) input.charAt(4);
                x3 = (int) numbers.get(index3);
                y3 = (int) input.charAt(7);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Error! \nPlease retry.");
                continue;
            }
            if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3)) { //checks if the same coordinate has been entered multiple times
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            } else if (x1 == x2 && x2 == x3) { //checks if the battleship is either vertical or horizontal 
                if ((y1 - 1 == y2 && y2 - 1 == y3) || (y1 + 1 == y2 && y2 + 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y3) || (y3 - 1 == y1 && y1 - 1 == y2) || (y1 - 1 == y3 && y3 - 1 == y2) || (y2 - 1 == y3 && y3 - 1 == y1)) ; //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3) {
                if ((x1 - 1 == x2 && x2 - 1 == x3) || (x1 + 1 == x2 && x2 + 1 == x3) || (x1 - 1 == x2 && x1 + 1 == x3) || (x1 + 1 == x2 && x1 - 1 == x3) || (x1 + 1 == x3 && x3 + 1 == x2) || (x1 - 1 == x3 && x3 - 1 == x2)) ; //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else { //checks if the battleship is diagonal 
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            break; //exit the loop if it passes all filters
        }
        return input;
    }

    /**
     * This method gets the location of the fourth battleship from the player.
     * The fourth battleship has a length of 4.
     *
     * @return
     * @throws IOException
     */
    public String battleship4() throws IOException {
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your fourth battleship (length of 4)");
            input = READ.readLine();
            if (input.charAt(input.length() - 1) == ' ') { //if the first or last char is a space, remove it
                input = input.substring(0, input.length() - 1);
            } else if (input.charAt(0) == ' ') {
                input = input.substring(1);
            }
            if (input.length() != 11) { //checks if the input has the correct length
                if (input.length() > 11) {
                    System.out.println("Too many characters input.");
                    System.out.println("Please Try again.");
                } else {
                    System.out.println("Not enough characters input");
                    System.out.println("Please Try again.");
                }
                System.out.println("Enter location in the format 'LetterNumber LetterNumber LetterNumber' as demonstrated earlier.");
                continue;
            }
            //if the length is correct, splits up the input to individual x and y coordinates to further manipulate them
            index1 = letters.indexOf(input.toUpperCase().charAt(0));
            index2 = letters.indexOf(input.toUpperCase().charAt(3));
            index3 = letters.indexOf(input.toUpperCase().charAt(6));
            index4 = letters.indexOf(input.toUpperCase().charAt(9));
            try {
                x1 = (int) numbers.get(index1);
                y1 = (int) input.charAt(1);
                x2 = (int) numbers.get(index2);
                y2 = (int) input.charAt(4);
                x3 = (int) numbers.get(index3);
                y3 = (int) input.charAt(7);
                x4 = (int) numbers.get(index4);
                y4 = (int) input.charAt(10);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Error! \nPlease retry.");
                continue;
            }
            if (x1 == x2 && x2 == x3 && x3 == x4) { //checks if the battleship is either vertical or horizontal
                if ((y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y1 + 1 == y2 && y2 + 1 == y3 && y3 + 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2)) ; //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3 && y3 == y4) {
                if ((x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x1 + 1 == x2 && x2 + 1 == x3 && x3 + 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x4 && y4 - 1 == y1) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2)) ; //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3) || (x4 == x1 && y4 == y1) || (x2 == x4 && y2 == y4) || (x3 == x4 && y3 == y4)) { //checks if the same coordinate has been entered multiple times
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            } else { //checks if the battleship is diagonal
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            break; //exit the loop if it passes all filters
        }
        return input;
    }

    /**
     * This method gets the location of the fifth battleship from the player.
     * The fifth battleship has a length of 5.
     *
     * @return
     * @throws IOException
     */
    public String battleship5() throws IOException {
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your fifth battleship (length of 5)");
            input = READ.readLine();
            if (input.charAt(input.length() - 1) == ' ') { //if the first or last char is a space, remove it
                input = input.substring(0, input.length() - 1);
            } else if (input.charAt(0) == ' ') {
                input = input.substring(1);
            }
            if (input.length() != 14) { //checks if the input has the correct length
                if (input.length() > 14) {
                    System.out.println("Too many characters input.");
                    System.out.println("Please Try again.");
                } else {
                    System.out.println("Not enough characters input");
                    System.out.println("Please Try again.");
                }
                System.out.println("Enter location in the format 'LetterNumber LetterNumber LetterNumber' as demonstrated earlier.");
                continue;
            }
            //if the length is correct, splits up the input to individual x and y coordinates to further manipulate them
            index1 = letters.indexOf(input.toUpperCase().charAt(0));
            index2 = letters.indexOf(input.toUpperCase().charAt(3));
            index3 = letters.indexOf(input.toUpperCase().charAt(6));
            index4 = letters.indexOf(input.toUpperCase().charAt(9));
            index5 = letters.indexOf(input.toUpperCase().charAt(12));
            try {
                x1 = (int) numbers.get(index1);
                y1 = (int) input.charAt(1);
                x2 = (int) numbers.get(index2);
                y2 = (int) input.charAt(4);
                x3 = (int) numbers.get(index3);
                y3 = (int) input.charAt(7);
                x4 = (int) numbers.get(index4);
                y4 = (int) input.charAt(10);
                x5 = (int) numbers.get(index5);
                y5 = (int) input.charAt(13);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Error! \nPlease retry.");
                continue;
            }
            if (x1 == x2 && x2 == x3 && x3 == x4 && x4 == x5) { //checks if the battleship is either vertical or horizontal
                if ((y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5) || (y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3) || (y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2) || (y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3) || (y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2) || (y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1) || (y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3) || (y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3) || (y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1) || (y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1) || (y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1) || (y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2) || (y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1) || (y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2) || (y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5) || (y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1) || (y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2) || (y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2) || (y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1) || (y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2) || (y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1)); //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3 && y3 == y4 && y4 == y5) {
                if ((x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5) || (x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3) || (x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2) || (x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3) || (x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2) || (x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3) || (x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5) || (x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1) || (x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3) || (x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3) || (x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1) || (x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1) || (x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1) || (x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1) || (x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2) || (x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1) || (x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2) || (x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5) || (x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1) || (x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2) || (x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2) || (x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1) || (x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1) || (x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2) || (x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1)) ; //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3) || (x4 == x1 && y4 == y1) || (x2 == x4 && y2 == y4) || (x3 == x4 && y3 == y4) || (x1 == x5 && y1 == y5) || (x2 == x5 && y2 == y5) || (x3 == x5 || y3 == y5) || (x4 == x5 && y4 == y5)) { //checks if the same coordinate has been entered multiple times
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            } else { //checks if the battleship is diagonal 
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            break; //exit the loop if it passes all filters
        }
        return input;
    }
}
