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

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author fazerlicourice71256
 */
/**
 * This is the client side of an application called Battleships.
 *
 */
public class Battleship_client {

    //defines and initializes an input stream reader
    BufferedReader READ = new BufferedReader(new InputStreamReader(System.in));
    //used to take generic input from the player
    String input;
    //contains the value of the port number to which it should connect
    public static int PORT1 = 12345, PORT2 = 54321;
    //used as a constructor for the socket
    public static InetAddress host;
    //defines an object input stream 'in'
    ObjectInputStream in;
    //defines an object output stream 'out'
    ObjectOutputStream out;
    //contains the grid for the player(player's target)
    String coordinates1[][] = new String[10][10];
    //contains the grid for the opponent(opponent's target)
    String coordinates2[][] = new String[10][10];
    //contains the coordinates of the players battleships
    String locations[] = new String[17];
    //used to manipulate each individual coordinate to verify its integrity with respect to the other coordinates of that battleship
    int x1, x2, x3, x4, x5;
    int y1, y2, y3, y4, y5;
    //used along with arraylist to locate the corresponding numbers and letters
    int index1, index2, index3, index4, index5;
    //used to manipulate the coordinates and find corresponding numbers and letters
    static ArrayList numbers = new ArrayList(), letters = new ArrayList();
    //contains all the various ip addresses on which servers are currently running
    static List<InetAddress> IP = new ArrayList<>();
    //used to randomly pick an ip address from the arraylist which contains ip addresses
    Random random = new Random();
    //used to store the users target location
    String target;

    public String battleship1() throws IOException {
        //this method gets the coordinates for the players first battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your first battleship (length of 2)");
            input = READ.readLine();
            //checks if the input has the correct length
            if (input.length() != 5) {
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
            index1 = letters.indexOf(input.charAt(0));
            index2 = letters.indexOf(input.charAt(3));
            x1 = (int) numbers.get(index1);
            y1 = (int) input.charAt(1);
            x2 = (int) numbers.get(index2);
            y2 = (int) input.charAt(4);
            //checks if the battleship is either vertical or horizontal
            if (x1 == x2) {
                //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                if (y1 - 1 != y2 && y1 + 1 != y2) {
                    System.out.println("This Battleship does not occupy two immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2) {
                //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                if (x1 - 1 != x2 && x1 + 1 != x2) {
                    System.out.println("This Battleship does not occupy two immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } //checks if the same coordinate has been entered multiple times
            else if (x1 == x2 && y1 == y2) {
                System.out.println("You have entered the same coordinate twice.");
                System.out.println("Please Try again.");
                continue;
            } //checks if the battleship is diagonal 
            else {
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            //exit the loop if it passes all filters
            break;
        }
        return input;
    }

    public String battleship23(String number) throws IOException {
        //this method gets the coordinates for the players second/third battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your " + number + " battleship (length of 3)");
            input = READ.readLine();
            //checks if the input has the correct length
            if (input.length() != 8) {
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
            index1 = letters.indexOf(input.charAt(0));
            index2 = letters.indexOf(input.charAt(3));
            index3 = letters.indexOf(input.charAt(6));
            x1 = (int) numbers.get(index1);
            y1 = (int) input.charAt(1);
            x2 = (int) numbers.get(index2);
            y2 = (int) input.charAt(4);
            x3 = (int) numbers.get(index3);
            y3 = (int) input.charAt(7);
            //checks if the same coordinate has been entered multiple times
            if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3)) {
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            }//checks if the battleship is either vertical or horizontal 
            else if (x1 == x2 && x2 == x3) {
                //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                if ((y1 - 1 == y2 && y2 - 1 == y3) || (y1 + 1 == y2 && y2 + 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y3) || (y3 - 1 == y1 && y1 - 1 == y2) || (y1 - 1 == y3 && y3 - 1 == y2) || (y2 - 1 == y3 && y3 - 1 == y1)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3) {
                //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                if ((x1 - 1 == x2 && x2 - 1 == x3) || (x1 + 1 == x2 && x2 + 1 == x3) || (x1 - 1 == x2 && x1 + 1 == x3) || (x1 + 1 == x2 && x1 - 1 == x3) || (x1 + 1 == x3 && x3 + 1 == x2) || (x1 - 1 == x3 && x3 - 1 == x2)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } //checks if the battleship is diagonal 
            else {
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            //exit the loop if it passes all filters
            break;
        }
        return input;
    }

    public String battleship4() throws IOException {
        //this method gets the coordinates for the players fourth battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your fourth battleship (length of 4)");
            input = READ.readLine();
            //checks if the input has the correct length
            if (input.length() != 11) {
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
            index1 = letters.indexOf(input.charAt(0));
            index2 = letters.indexOf(input.charAt(3));
            index3 = letters.indexOf(input.charAt(6));
            index4 = letters.indexOf(input.charAt(9));
            x1 = (int) numbers.get(index1);
            y1 = (int) input.charAt(1);
            x2 = (int) numbers.get(index2);
            y2 = (int) input.charAt(4);
            x3 = (int) numbers.get(index3);
            y3 = (int) input.charAt(7);
            x4 = (int) numbers.get(index4);
            y4 = (int) input.charAt(10);
            //checks if the battleship is either vertical or horizontal
            if (x1 == x2 && x2 == x3 && x3 == x4) {
                //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                if ((y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y1 + 1 == y2 && y2 + 1 == y3 && y3 + 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3 && y3 == y4) {
                //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                if ((x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x1 + 1 == x2 && x2 + 1 == x3 && x3 + 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x4 && y4 - 1 == y1) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } //checks if the same coordinate has been entered multiple times
            else if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3) || (x4 == x1 && y4 == y1) || (x2 == x4 && y2 == y4) || (x3 == x4 && y3 == y4)) {
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            } //checks if the battleship is diagonal 
            else {
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            //exit the loop if it passes all filters
            break;
        }
        return input;
    }

    public String battleship5() throws IOException {
        //this method gets the coordinates for the players fifth battleship, sends it through a group of filters and if it passes returns the input to the calling method
        while (true) {//continues until the input passes all the filters
            System.out.println("Enter the location of your fifth battleship (length of 5)");
            input = READ.readLine();
            //checks if the input has the correct length
            if (input.length() != 14) {
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
            index1 = letters.indexOf(input.charAt(0));
            index2 = letters.indexOf(input.charAt(3));
            index3 = letters.indexOf(input.charAt(6));
            index4 = letters.indexOf(input.charAt(9));
            index5 = letters.indexOf(input.charAt(12));
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
            //checks if the battleship is either vertical or horizontal
            if (x1 == x2 && x2 == x3 && x3 == x4 && x4 == x5) {
                //if it's vertical makes sure that the battleship occupies consecutive coordinates and is not split up all over the column
                if ((y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5) || (y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5) || (y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3) || (y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4) || (y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5) || (y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5) || (y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2) || (y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5) || (y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5) || (y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2) || (y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3) || (y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2) || (y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5) || (y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5) || (y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3) || (y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5) || (y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5) || (y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1) || (y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5) || (y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5) || (y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3) || (y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3) || (y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1) || (y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5) || (y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5) || (y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y5) || (y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5) || (y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1) || (y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5) || (y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5) || (y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y4 && y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1) || (y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y3 - 1 == y5 && y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5) || (y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5) || (y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2) || (y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y5) || (y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y3) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5) || (y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1) || (y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y5) || (y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y5 && y5 - 1 == y2) || (y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y5) || (y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y5 && y5 - 1 == y1) || (y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2) || (y4 - 1 == y3 && y3 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1) || (y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y4 - 1 == y5 && y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y4 - 1 == y5 && y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2) || (y4 - 1 == y5 && y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1) || (y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4) || (y5 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3) || (y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4) || (y5 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2) || (y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3) || (y5 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2) || (y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y4) || (y5 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y3) || (y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4) || (y5 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1) || (y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3) || (y5 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1) || (y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y4) || (y5 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y4 && y4 - 1 == y2) || (y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y4) || (y5 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y4 && y4 - 1 == y1) || (y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2) || (y5 - 1 == y3 && y3 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1) || (y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y2 && y2 - 1 == y3) || (y5 - 1 == y4 && y4 - 1 == y1 && y1 - 1 == y3 && y3 - 1 == y2) || (y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y1 && y1 - 1 == y3) || (y5 - 1 == y4 && y4 - 1 == y2 && y2 - 1 == y3 && y3 - 1 == y1) || (y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y1 && y1 - 1 == y2) || (y5 - 1 == y4 && y4 - 1 == y3 && y3 - 1 == y2 && y2 - 1 == y1)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } else if (y1 == y2 && y2 == y3 && y3 == y4 && y4 == y5) {
                //if it's horizontal makes sure that the battleship occupies consecutive coordinates and is not split up all over the row
                if ((x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5) || (x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5) || (x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3) || (x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4) || (x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5) || (x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5) || (x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2) || (x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5) || (x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5) || (x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2) || (x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3) || (x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2) || (x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5) || (x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5) || (x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3) || (x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5) || (x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5) || (x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1) || (x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5) || (x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5) || (x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3) || (x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3) || (x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1) || (x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1) || (x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5) || (x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5) || (x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x5) || (x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5) || (x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1) || (x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5) || (x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5) || (x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x4 && x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1) || (x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x3 - 1 == x5 && x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5) || (x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5) || (x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2) || (x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x5) || (x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x3) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5) || (x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1) || (x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x5) || (x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x5 && x5 - 1 == x2) || (x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x5) || (x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x5 && x5 - 1 == x1) || (x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2) || (x4 - 1 == x3 && x3 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1) || (x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x4 - 1 == x5 && x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x4 - 1 == x5 && x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2) || (x4 - 1 == x5 && x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1) || (x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4) || (x5 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3) || (x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4) || (x5 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2) || (x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3) || (x5 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2) || (x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x4) || (x5 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x3) || (x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4) || (x5 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1) || (x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3) || (x5 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1) || (x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x4) || (x5 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x4 && x4 - 1 == x2) || (x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x4) || (x5 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x4 && x4 - 1 == x1) || (x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2) || (x5 - 1 == x3 && x3 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1) || (x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x2 && x2 - 1 == x3) || (x5 - 1 == x4 && x4 - 1 == x1 && x1 - 1 == x3 && x3 - 1 == x2) || (x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x1 && x1 - 1 == x3) || (x5 - 1 == x4 && x4 - 1 == x2 && x2 - 1 == x3 && x3 - 1 == x1) || (x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x1 && x1 - 1 == x2) || (x5 - 1 == x4 && x4 - 1 == x3 && x3 - 1 == x2 && x2 - 1 == x1)) {

                } else {
                    System.out.println("This Battleship does not occupy immediate spaces and is split.");
                    System.out.println("Please Try again.");
                    continue;
                }
            } //checks if the same coordinate has been entered multiple times
            else if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) || (x1 == x3 && y1 == y3) || (x4 == x1 && y4 == y1) || (x2 == x4 && y2 == y4) || (x3 == x4 && y3 == y4) || (x1 == x5 && y1 == y5) || (x2 == x5 && y2 == y5) || (x3 == x5 || y3 == y5) || (x4 == x5 && y4 == y5)) {
                System.out.println("You have entered the same coordinate multiple times.");
                System.out.println("Please Try again.");
                continue;
            } //checks if the battleship is diagonal 
            else {
                System.out.println("This battleship is neither horizontal not vertical.");
                System.out.println("Please Try again.");
                continue;
            }
            //exit the loop if it passes all filters
            break;
        }
        return input;
    }

    public void doStuff() throws IOException, ClassNotFoundException {
        Battleship_client methodCaller = new Battleship_client();
        //initializes the arraylist numbers and letters with the first ten of their respective characters for use while the player sets the locations
        for (int loop = 0; loop < 10; loop++) {
            numbers.add(loop, loop);
        }
        letters.add(0, 'A');
        letters.add(1, 'B');
        letters.add(2, 'C');
        letters.add(3, 'D');
        letters.add(4, 'E');
        letters.add(5, 'F');
        letters.add(6, 'G');
        letters.add(7, 'H');
        letters.add(8, 'I');
        letters.add(9, 'J');
        //chooses a random IP address from the array list of ip addresses.
        host = IP.get(random.nextInt(IP.size()));
        String HOST = "localhost";
        //attempts to initialize the socket
        try (Socket connection = new Socket(HOST, PORT2)) {
            System.out.println("Connected to player 1. Starting game.");
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            //reads the initial grid for both players from the server
            coordinates1 = (String[][]) in.readObject();
            coordinates2 = (String[][]) in.readObject();
            //prints out the grid and starts taking in user input
            System.out.println("  A B C D E F G H I J");
            for (int loop = 0; loop < 10; loop++) {
                System.out.print(loop + " ");
                for (int loop2 = 0; loop2 < 10; loop2++) {
                    System.out.print(coordinates1[loop][loop2]);
                }
                System.out.println();
            }
            System.out.println("Enter the location of your battleships. THEY CAN BE HORIZONTAL AND VERTICAL ONLY! " + "\n" + "In the format LetterNumber LetterNumber LetterNumber....");
            System.out.println("Example: A9 A8 A7, A1 B1 C1");
            //initializes the String array locations allowing us to append to it later
            for (int randomloop = 0; randomloop < 17; randomloop++) {
                locations[randomloop] = "";
            }
            //calls the method to get the location of the first battleship
            String battleship1 = methodCaller.battleship1();
            String location[] = battleship1.split(" ");
            locations[0] = location[0];
            locations[1] = location[1];

            //calls the method to get the location of the second battleship
            String battleship2 = methodCaller.battleship23("second");
            String location2[] = battleship2.split(" ");
            locations[2] = location2[0];
            locations[3] = location2[1];
            locations[4] = location2[2];

            //calls the method to get the location of the third battleship
            String battleship3 = methodCaller.battleship23("third");
            String location3[] = battleship3.split(" ");
            locations[5] = location3[0];
            locations[6] = location3[1];
            locations[7] = location3[2];

            //calls the method to get the location of the fourth battleship
            String battleship4 = methodCaller.battleship4();
            String location4[] = battleship4.split(" ");
            locations[8] = location4[0];
            locations[9] = location4[1];
            locations[10] = location4[2];
            locations[11] = location4[3];

            //calls the method to get the location of the fifth battleship
            String battleship5 = methodCaller.battleship5();
            String location5[] = battleship5.split(" ");
            locations[12] = location5[0];
            locations[13] = location5[1];
            locations[14] = location5[2];
            locations[15] = location5[3];
            locations[16] = location5[4];

            //writes the string array of coordinates to the server
            out.writeObject(locations);
            out.flush();
            //starts the game loop
            while (true) {
                //reads the two grids
                coordinates1 = (String[][]) in.readObject();
                coordinates2 = (String[][]) in.readObject();
                //prints the two grids
                for (int loop = 0; loop < 10; loop++) {
                    System.out.println("  A B C D E F G H I J");
                    System.out.print(loop + " ");
                    for (int loop2 = 0; loop2 < 10; loop2++) {
                        System.out.print(coordinates1[loop][loop2]);
                    }
                    System.out.println();
                }
                for (int loop = 0; loop < 10; loop++) {
                    System.out.println("  A B C D E F G H I J");
                    System.out.print(loop + " ");
                    for (int loop2 = 0; loop2 < 10; loop2++) {
                        System.out.print(coordinates2[loop][loop2]);
                    }
                    System.out.println();
                }
                //gets the target coordinate from the player and validates it
                while (true) {
                    System.out.println("Enter the target coordinate. eg: A7, H4 etc.");
                    target = READ.readLine();
                    char[] validationxy = target.toCharArray();
                    if (letters.contains(validationxy[0]) == true && numbers.contains(validationxy[1]) == true) {
                        break;
                    }

                }
                //after validating, writes the target location to the server
                out.writeUTF(target);
                //server checks if it's a hit or a miss and updates the grid

                //checks if the someone has won yet
                String GameOver;
                GameOver = in.readUTF();
                if (GameOver.equals("1")) {
                    System.out.println("Game Over!!");
                    System.out.println(in.readUTF());
                    break;
                } else if (GameOver.equals("0")) {
                }

            }
        } catch (ConnectException ex) {
            try (Socket connection = new Socket(HOST, PORT1)) {
                System.out.println("Connected to server. Waiting for player 2.");
                //initializes the input and output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                in = new ObjectInputStream(connection.getInputStream());
                //reads the initial grid for both players from the server
                coordinates1 = (String[][]) in.readObject();
                coordinates2 = (String[][]) in.readObject();
                //prints grid and starts taking user input
                System.out.println("  A B C D E F G H I J");
                for (int loop = 0; loop < 10; loop++) {
                    System.out.print(loop + " ");
                    for (int loop2 = 0; loop2 < 10; loop2++) {
                        System.out.print(coordinates1[loop][loop2]);
                    }
                    System.out.println();
                }
                System.out.println("Enter the location of your battleships. THEY CAN BE HORIZONTAL AND VERTICAL ONLY! " + "\n" + "In the format LetterNumber LetterNumber LetterNumber....");
                System.out.println("Example: A9 A8 A7, A1 B1 C1");
                //initializes the String array locations allowing us to append to it later
                for (int randomloop = 0; randomloop < 17; randomloop++) {
                    locations[randomloop] = "";
                }
                //calls the method to get the location of the first battleship
                String battleship1 = methodCaller.battleship1();
                String location[] = battleship1.split(" ");
                locations[0] = location[0];
                locations[1] = location[1];

                //calls the method to get the location of the second battleship
                String battleship2 = methodCaller.battleship23("second");
                String location2[] = battleship2.split(" ");
                locations[2] = location2[0];
                locations[3] = location2[1];
                locations[4] = location2[2];

                //calls the method to get the location of the third battleship
                String battleship3 = methodCaller.battleship23("third");
                String location3[] = battleship3.split(" ");
                locations[5] = location3[0];
                locations[6] = location3[1];
                locations[7] = location3[2];

                //calls the method to get the location of the fourth battleship
                String battleship4 = methodCaller.battleship4();
                String location4[] = battleship4.split(" ");
                locations[8] = location4[0];
                locations[9] = location4[1];
                locations[10] = location4[2];
                locations[11] = location4[3];

                //calls the method to get the location of the fifth battleship
                String battleship5 = methodCaller.battleship5();
                String location5[] = battleship5.split(" ");
                locations[12] = location5[0];
                locations[13] = location5[1];
                locations[14] = location5[2];
                locations[15] = location5[3];
                locations[16] = location5[4];

                //writes the string array of coordinates to the server
                out.writeObject(locations);
                out.flush();
                //starts the game loop
                while (true) {
                    //reads the two grids
                    coordinates1 = (String[][]) in.readObject();
                    coordinates2 = (String[][]) in.readObject();
                    //prints the two grids
                    for (int loop = 0; loop < 10; loop++) {
                        System.out.println("  A B C D E F G H I J");
                        System.out.print(loop + " ");
                        for (int loop2 = 0; loop2 < 10; loop2++) {
                            System.out.print(coordinates1[loop][loop2]);
                        }
                        System.out.println();
                    }
                    for (int loop = 0; loop < 10; loop++) {
                        System.out.println("  A B C D E F G H I J");
                        System.out.print(loop + " ");
                        for (int loop2 = 0; loop2 < 10; loop2++) {
                            System.out.print(coordinates2[loop][loop2]);
                        }
                        System.out.println();
                    }
                    //gets the target coordinate from the player and validates it
                    while (true) {
                        System.out.println("Enter the target coordinate. eg: A7, H4 etc.");
                        target = READ.readLine();
                        char[] validationxy = target.toCharArray();
                        if (letters.contains(validationxy[0]) == true && numbers.contains(validationxy[1]) == true) {
                            break;
                        }

                    }
                    //after validating, writes the target location to the server
                    out.writeUTF(target);
                    //server checks if it's a hit or a miss and updates the grid

                    //checks if the someone has won yet
                    String GameOver;
                    GameOver = in.readUTF();
                    if (GameOver.equals("1")) {
                        System.out.println("Game Over!!");
                        System.out.println(in.readUTF());
                        break;
                    } else if (GameOver.equals("0")) {
                    }

                }
            }
        }

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Battleship_client obj = new Battleship_client();
        //adds ip addresses to the arraylist containing ip addresses
        InetAddress ip1 = InetAddress.getByName("68.50.78.57");
        IP.add(ip1);
        obj.doStuff();
    }
}
