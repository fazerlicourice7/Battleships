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
 * This is the client side of an application called Battleships.
 *
 * @author fazerlicourice71256
 */
public class Battleship_client {

    BufferedReader READ = new BufferedReader(new InputStreamReader(System.in)); //defines and initializes an input stream reader
    String input; //used to take generic input from the player
    public static int PORT1 = 12345, PORT2 = 54321; //contains the value of the port number to which it should connect
    public static InetAddress host; //used as a constructor for the socket
    ObjectInputStream in; //defines an object input stream 'in'
    ObjectOutputStream out; //defines an object output stream 'out'
    String coordinates1[][] = new String[10][10]; //contains the grid for the player(player's target)
    String coordinates2[][] = new String[10][10]; //contains the grid for the opponent(opponent's target)
    String locations[] = new String[17]; //contains the coordinates of the players battleships
    int x1, x2, x3, x4, x5; //used to manipulate each individual coordinate to verify its integrity with respect to the other coordinates of that battleship
    int y1, y2, y3, y4, y5;
    int index1, index2, index3, index4, index5; //used along with arraylist to locate the corresponding numbers and letters
    static ArrayList numbers = new ArrayList(), letters = new ArrayList(); //used to manipulate the coordinates and find corresponding numbers and letters
    static List<InetAddress> IP = new ArrayList<>(); //contains all the various ip addresses on which servers are currently running
    Random random = new Random(); //used to randomly pick an ip address from the arraylist which contains ip addresses
    String target; //used to store the users target location

    /**
     * The interface between the player and the server.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void doStuff() throws IOException, ClassNotFoundException {
        getBattleships GetBattleships = new getBattleships();
        printGrids printDem = new printGrids();
        for (int loop = 0; loop < 10; loop++) { //initializes the arraylist numbers and letters with the first ten of their respective characters for use while the player sets the locations
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
        host = IP.get(random.nextInt(IP.size())); //chooses a random IP address from the array list of ip addresses.
        String HOST = "localhost";
        try (Socket connection = new Socket(HOST, PORT2)) { //attempts to initialize the socket
            System.out.println("Connected to player 1. Starting game.");
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            coordinates1 = (String[][]) in.readObject(); //reads the initial grid for both players from the server
            coordinates2 = (String[][]) in.readObject();
            printDem.grid1(coordinates1); //prints out the grid and starts taking in user input
            System.out.println("Enter the location of your battleships. THEY CAN BE HORIZONTAL AND VERTICAL ONLY! " + "\n" + "In the format LetterNumber LetterNumber LetterNumber....");
            System.out.println("Example: A9 A8 A7, A1 B1 C1");
            for (int randomloop = 0; randomloop < 17; randomloop++) { //initializes the String array locations allowing us to append to it later
                locations[randomloop] = "";
            }

            //calls the method to get the location of the first battleship
            String battleship1 = GetBattleships.battleship1();
            String location[] = battleship1.split(" ");
            locations[0] = location[0];
            locations[1] = location[1];

            //calls the method to get the location of the second battleship
            String battleship2 = GetBattleships.battleship23("second");
            String location2[] = battleship2.split(" ");
            locations[2] = location2[0];
            locations[3] = location2[1];
            locations[4] = location2[2];

            //calls the method to get the location of the third battleship
            String battleship3 = GetBattleships.battleship23("third");
            String location3[] = battleship3.split(" ");
            locations[5] = location3[0];
            locations[6] = location3[1];
            locations[7] = location3[2];

            //calls the method to get the location of the fourth battleship
            String battleship4 = GetBattleships.battleship4();
            String location4[] = battleship4.split(" ");
            locations[8] = location4[0];
            locations[9] = location4[1];
            locations[10] = location4[2];
            locations[11] = location4[3];

            //calls the method to get the location of the fifth battleship
            String battleship5 = GetBattleships.battleship5();
            String location5[] = battleship5.split(" ");
            locations[12] = location5[0];
            locations[13] = location5[1];
            locations[14] = location5[2];
            locations[15] = location5[3];
            locations[16] = location5[4];

            out.writeObject(locations); //writes the string array of coordinates to the server
            out.flush();
            while (true) { //starts the game loop

                coordinates1 = (String[][]) in.readObject(); //reads the two grids
                coordinates2 = (String[][]) in.readObject();

                printDem.grid1(coordinates1); //prints the two grids
                printDem.grid2(coordinates2);

                while (true) {  //gets the target coordinate from the player and validates it
                    System.out.println("Enter the target coordinate. eg: A7, H4 etc.");
                    target = READ.readLine();

                    int x = Character.getNumericValue((target.charAt(1)));
                    if (letters.contains(target.toUpperCase().charAt(0)) && numbers.contains(x)) {
                        break;
                    }
                    System.out.println("Error! \nPlease re-try.");
                }

                out.writeUTF(target);  //after validating, writes the target location to the server
                System.out.println("writes target");
                //server checks if it's a hit or a miss and updates the grid
                //checks if the someone has won yet
                String GameOver;
                GameOver = in.readUTF();
                System.out.println("read game over");
                if (GameOver.equals("1")) {
                    System.out.println("Game Over!!");
                    System.out.println(in.readUTF());
                    break;
                } else if (GameOver.equals("0")) ;
            }
        } catch (ConnectException ex) {
            try (Socket connection = new Socket(HOST, PORT1)) {
                System.out.println("Connected to server. Waiting for player 2.");
                out = new ObjectOutputStream(connection.getOutputStream()); //initializes the input and output streams
                in = new ObjectInputStream(connection.getInputStream());
                coordinates1 = (String[][]) in.readObject(); //reads the initial grid for both players from the server
                coordinates2 = (String[][]) in.readObject();
                printDem.grid1(coordinates1); //prints grid and starts taking user input
                System.out.println("Enter the location of your battleships. THEY CAN BE HORIZONTAL AND VERTICAL ONLY! " + "\n" + "In the format LetterNumber LetterNumber LetterNumber....");
                System.out.println("Example: A9 A8 A7, A1 B1 C1");
                for (int randomloop = 0; randomloop < 17; randomloop++) { //initializes the String array locations allowing us to append to it later
                    locations[randomloop] = "";
                }

                //calls the method to get the location of the first battleship
                String battleship1 = GetBattleships.battleship1();
                String location[] = battleship1.split(" ");
                locations[0] = location[0];
                locations[1] = location[1];

                //calls the method to get the location of the second battleship
                String battleship2 = GetBattleships.battleship23("second");
                String location2[] = battleship2.split(" ");
                locations[2] = location2[0];
                locations[3] = location2[1];
                locations[4] = location2[2];

                //calls the method to get the location of the third battleship
                String battleship3 = GetBattleships.battleship23("third");
                String location3[] = battleship3.split(" ");
                locations[5] = location3[0];
                locations[6] = location3[1];
                locations[7] = location3[2];

                //calls the method to get the location of the fourth battleship
                String battleship4 = GetBattleships.battleship4();
                String location4[] = battleship4.split(" ");
                locations[8] = location4[0];
                locations[9] = location4[1];
                locations[10] = location4[2];
                locations[11] = location4[3];

                //calls the method to get the location of the fifth battleship
                String battleship5 = GetBattleships.battleship5();
                String location5[] = battleship5.split(" ");
                locations[12] = location5[0];
                locations[13] = location5[1];
                locations[14] = location5[2];
                locations[15] = location5[3];
                locations[16] = location5[4];

                out.writeObject(locations); //writes the string array of coordinates to the server
                out.flush();
                while (true) { //starts the game loop
                    coordinates1 = (String[][]) in.readObject(); //reads the two grids
                    coordinates2 = (String[][]) in.readObject();
                    printDem.grid1(coordinates1); //prints the two grids
                    printDem.grid2(coordinates2);
                    while (true) { //gets the target coordinate from the player and validates it
                        System.out.println("Enter the target coordinate. eg: A7, H4 etc.");
                        target = READ.readLine();
                        /*for (int i = 0; i < 10; i++) {
                         System.out.println(numbers.get(i));
                         }
                         System.out.println(target.toUpperCase().charAt(0));
                         System.out.println(target.toUpperCase().charAt(1));
                         System.out.println(letters.contains(target.toUpperCase().charAt(0)));*/

                        int x = Character.getNumericValue(target.charAt(1));
                        if (letters.contains(target.toUpperCase().charAt(0)) && numbers.contains(x)) {
                            break;
                        }
                        System.out.println("Error! \nPlease re-try.");
                    }
                    out.writeUTF(target); //after validating, writes the target location to the server
                    System.out.println("writes target");
                    //server checks if it's a hit or a miss and updates the grid
                    //checks if the someone has won yet
                    String GameOver;
                    GameOver = in.readUTF();
                    System.out.println("read gameover");
                    if (GameOver.equals("1")) {
                        System.out.println("Game Over!!");
                        System.out.println(in.readUTF());
                        break;
                    } else if (GameOver.equals("0")) ;
                }
            }
        }
    }

    /**
     * Adds IP Addresses of all the current server to a list. Starts doStuff()
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Battleship_client obj = new Battleship_client();
        //adds ip addresses to the arraylist containing ip addresses
        InetAddress ip1 = InetAddress.getByName("");
        IP.add(ip1);
        obj.doStuff();
    }
}
