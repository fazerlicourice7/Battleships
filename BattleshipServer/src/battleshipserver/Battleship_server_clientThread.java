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
package battleshipserver;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fazerlicourice7
 */
public class Battleship_server_clientThread implements Runnable {

    //ArrayList XY1 = new ArrayList();
    //ArrayList XY2 = new ArrayList();
    //player is a constructor that decides whether this thread deals with the player1 or player2 
    int player;
    //player1 and player2 hold the number of on target shots(hits) made by that player.
    int player1, player2;
    //player1m and player2m hold the number of misses made by that player
    int player1m, player2m;
    //hold the corresponding values of the x axis on the grid
    int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9;
    //hold the values of the locations of the battleships of the two players(in the format LetterNumber. eg A3, G7, J9 etc.
    String xy1[], xy2[];
    //used to hold the values of the x and y axes of te users input while targeting the opponents battleships
    int x, y;
    //counts the number of attempts the user made at the opponent's battleships
    int TRIES1, TRIES2;
    //used to check if the corresponding thread is ready to continue or not
    int READY1 = 0, READY2 = 0;
    //hold the grids for both the players- coordinates1 holds player1's battleships- ie. player1 is aiming at coordinates2
    String[][] coordinates1, coordinates2;
    //is the socket connection to the player
    Socket client;

    Battleship_server_clientThread(Socket client, int player) {
        //super("Battleship_server_clientThread");
        //client socket
        this.client = client;
        //variable that decides which player this hread deals with
        this.player = player;
    }

    @Override
    public void run() {
        //initializes the 10x10 grids for the player
        synchronized (this) {
            //outer loop is for the rows
            for (int loop = 0; loop < 10; loop++) {
                //the inner loop is for the columns
                for (int loop2 = 0; loop2 < 10; loop2++) {
                    coordinates1[loop][loop2] = "~ ";
                    coordinates2[loop][loop2] = "~ ";
                }
            }
        }
        if (player == 1) {
            //deals with the client assuming it is player1
            //declares Object i/o streams
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                //initializes i/o streams
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                //writes the two 10x10 grids to the client
                out.writeObject(coordinates2);
                out.writeObject(coordinates1);
                synchronized (this) {
                    //reads the location of all of the player's battleships, makes them available to the corresponding thread and stores them
                    xy1 = (String[]) in.readObject();
                }
                //game loop
                while (true) {
                    //writes the updated grids once each iteration
                    out.writeObject(coordinates2);
                    out.writeObject(coordinates1);
                    //reads the target location from the client
                    String target = in.readUTF();
                    //increments the counter which keeps track of number of missiles fired
                    synchronized (this) {
                        TRIES1++;
                    }
                    //stores the y-axis value of the target coordinate
                    y = ((int) (target.charAt(1))) - 1;
                    //determines and then stores the x-axis value of the coordinate
                    if (target.charAt(0) == 'A') {
                        x = A;
                    } else if (target.charAt(0) == 'B') {
                        x = B;
                    } else if (target.charAt(0) == 'C') {
                        x = C;
                    } else if (target.charAt(0) == 'D') {
                        x = D;
                    } else if (target.charAt(0) == 'E') {
                        x = E;
                    } else if (target.charAt(0) == 'F') {
                        x = F;
                    } else if (target.charAt(0) == 'G') {
                        x = G;
                    } else if (target.charAt(0) == 'H') {
                        x = H;
                    } else if (target.charAt(0) == 'I') {
                        x = I;
                    } else if (target.charAt(0) == 'J') {
                        x = J;
                    }
                    //checks if the player's target corresponds with any of the opponents battleship's locations
                    for (int loop = 1; loop <= xy1.length; loop++) {
                        //if yes then mark that spot with an 'X' to indicate a hit
                        if (target.equals(xy2[loop])) {
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (this) {
                                            coordinates2[row][column] = "X ";
                                        }
                                    } else if ("X ".equals(coordinates2[row][column]) || "O ".equals(coordinates2[row][column])) {

                                    } else {
                                        synchronized (this) {
                                            coordinates2[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            //counts number of hits the player made
                            player1++;
                        }//if no then mark that spot with an 'O' to indicate a miss 
                        else {
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (this) {
                                            coordinates2[row][column] = "O ";
                                        }
                                    } else if ("X ".equals(coordinates2[row][column]) || "O ".equals(coordinates2[row][column])) {

                                    } else {
                                        synchronized (this) {
                                            coordinates2[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            //counts number of misses that player made
                            player1m++;
                        }
                    }
                    //sets this thread's status to ready
                    synchronized (this) {
                        READY1 = 1;
                    }
                    //checks if the other thread is ready, waits until it is ready
                    while (READY2 != 1) {

                    }
                    //checks if the game is over
                    if (player1 == 17 && player2 == 17) {
                        //in the case of a draw:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("It was a draw!");
                        out.writeUTF("Both you and your opponent destroyed each other's battleships in " + TRIES1 + " shots.");
                        break;
                    }
                    if (player1 == 17) {
                        //in the case of a win:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("You won in " + TRIES1 + " shots.");
                        break;
                    } else if (player2 == 17) {
                        //in the case of a loss:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("You lost in " + TRIES2 + " shots.");
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (player == 2) {
            //deals with the client assuming it is player2
            //declares Object i/o streams
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                //initializes i/o streams
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                //writes the two 10x10 grids to the client
                out.writeObject(coordinates2);
                out.writeObject(coordinates1);
                synchronized (this) {
                    //reads the location of all of the player's battleships, makes them available to the corresponding thread and stores them
                    xy2 = (String[]) in.readObject();
                }
                //game loop
                while (true) {
                    //writes the updated grids once each iteration
                    out.writeObject(coordinates1);
                    out.writeObject(coordinates2);
                    //reads the target location from the client
                    String target = in.readUTF();
                    //increments the counter which keeps track of number of missiles fired
                    synchronized (this) {
                        TRIES2++;
                    }
                    //stores the y-axis value of the target coordinate
                    y = ((int) (target.charAt(1))) - 1;
                    //determines and then stores the x-axis value of the coordinate
                    if (target.charAt(0) == 'A') {
                        x = A;
                    } else if (target.charAt(0) == 'B') {
                        x = B;
                    } else if (target.charAt(0) == 'C') {
                        x = C;
                    } else if (target.charAt(0) == 'D') {
                        x = D;
                    } else if (target.charAt(0) == 'E') {
                        x = E;
                    } else if (target.charAt(0) == 'F') {
                        x = F;
                    } else if (target.charAt(0) == 'G') {
                        x = G;
                    } else if (target.charAt(0) == 'H') {
                        x = H;
                    } else if (target.charAt(0) == 'I') {
                        x = I;
                    } else if (target.charAt(0) == 'J') {
                        x = J;
                    }
                    //checks if the target coordinate corresponds with any of the opponents battleship coordinates
                    for (int loop = 0; loop < xy2.length; loop++) {
                        //if it does then set that coordinate to 'X' to indicate a hit
                        if (target.equals(xy2[loop])) {
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (this) {
                                            coordinates1[row][column] = "X ";
                                        }
                                    } else if ("X ".equals(coordinates1[row][column]) || "O ".equals(coordinates1[row][column])) {

                                    } else {
                                        synchronized (this) {
                                            coordinates1[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            //counts number of on target shots(ie-hits) fired
                            player2++;
                        } // if it doesn't then mark a '0' to indicate a miss 
                        else {
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (this) {
                                            coordinates1[row][column] = "O ";
                                        }
                                    } else if ("X ".equals(coordinates1[row][column]) || "O ".equals(coordinates1[row][column])) {

                                    } else {
                                        synchronized (this) {
                                            coordinates1[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            //counts number of off target shots(ie-misses) fired
                            player2m++;
                        }

                    }
                    //sets this thread's status to ready
                    synchronized (this) {
                        READY2 = 1;
                    }
                    //checks if the other thread is ready, waits until it is ready
                    while (READY1 != 1) {

                    }
                    //checks if game is over
                    if (player1 == 17 && player2 == 17) {
                        //in the case of a draw:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("It was a draw!");
                        out.writeUTF("Both you and your opponent destroyed each other's battleships in " + TRIES1 + " shots.");
                        break;
                    }
                    if (player2 == 17) {
                        //in the case of a win:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("You won in " + TRIES2 + " shots.");
                        break;
                    } else if (player1 == 17) {
                        //in the case of a loss:
                        out.writeUTF("GAME OVER!");
                        out.writeUTF("You lost in " + TRIES1 + " shots.");
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
