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

import static battleshipserver.Battleship_serverThread.TRIES1;
import static battleshipserver.Battleship_serverThread.TRIES2;
import static battleshipserver.Battleship_serverThread.lock;
import static battleshipserver.Battleship_serverThread.readyLock;
import static battleshipserver.Battleship_serverThread.xy1;
import static battleshipserver.Battleship_serverThread.xy2;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main server thread that does all the computation for the game and
 * interfaces with the client.
 *
 * @author fazerlicourice7
 */
public class Battleship_server_clientThread implements Runnable {

    int player; //player is a constructor that decides whether this thread deals with the player1 or player2 
    int player1, player2; //player1 and player2 hold the number of on target shots(hits) made by that player.
    int player1m, player2m; //player1m and player2m hold the number of misses made by that player
    int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9; //hold the corresponding values of the x axis on the grid
    int x, y;//used to hold the values of the x and y axes of the users input while targeting the opponents battleships
    private volatile String[][] coordinates1 = new String[10][10], coordinates2 = new String[10][10]; //hold the grids for both the players- coordinates1 holds player1's battleships- ie. player1 is aiming at coordinates2
    Socket client; //is the socket connection to the player

    //instance variables
    Grid grid1 = new Grid();
    Grid grid2 = new Grid();
    getInput getIt = new getInput();

    /**
     * The constructor.
     *
     * @param client
     * @param player
     */
    Battleship_server_clientThread(Socket client, int player) {
        this.client = client; //client socket
        this.player = player; //variable that decides which player this thread deals with
    }

    /**
     * Does the main computation for the game.
     */
    @Override
    public void run() {

        coordinates1 = grid1.init(); //initializes the 10x10 grids for the player
        coordinates2 = grid2.init();
        if (player == 1) { //deals with the client assuming it is player1
            ObjectInputStream in = null; //declares Object i/o streams
            ObjectOutputStream out = null;
            try { //initializes i/o streams
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(coordinates2);//writes the two 10x10 grids to the client
                out.writeObject(coordinates1);
                out.flush();
                synchronized (Battleship_serverThread.lock) { //reads the location of all of the player's battleships, makes them available to the corresponding thread and stores them
                    //System.out.println("synchronized lock works");
                    xy1 = (String[]) in.readObject();
                }
                /*synchronized (readyLock) { //sets this thread's status to ready
                 Battleship_serverThread.READY1 = true;
                 readyLock.notifyAll();
                 System.out.println("notify works");
                 try {
                 while (!Battleship_serverThread.READY2) {
                 readyLock.wait();
                 }
                 System.out.println("woke up from sleep");
                 } catch (InterruptedException ex) {
                 Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 readyLock.notifyAll();
                 } */
                Battleship_serverThread.shipsSet.countDown();
                try {
                    Battleship_serverThread.shipsSet.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {//game loop
                    Battleship_serverThread.shotFired = new CountDownLatch(2);
                    //System.out.println("started game loop");
                    out.writeObject(coordinates2); //writes the updated grids once each iteration
                    out.writeObject(coordinates1);
                    out.flush();
                    String target = in.readUTF(); //reads the target location from the client
                    //Battleship_serverThread.READY1 = false;
                    System.out.println("read target");
                    synchronized (lock) { //increments the counter which keeps track of number of missiles fired
                        System.out.println("incremented tries");
                        TRIES1.addAndGet(1);
                    }
                    y = ((int) (target.charAt(1)));  //stores the y-axis value of the target coordinate
                    x = getIt.getTargetX(target); //determines and then stores the x-axis value of the coordinate
                    for (int loop = 0; loop < xy1.length; loop++) { //checks if the player's target corresponds with any of the opponents battleship's locations
                        if (target.equals(xy2[loop])) { //if yes then mark that spot with an 'X' to indicate a hit
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates2[row][column] = "X ";
                                        }
                                    } else if ("X ".equals(coordinates2[row][column]) || "O ".equals(coordinates2[row][column])) ; else {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates2[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            player1++;  //keeps track of the number of hits the player made
                        } else { //if no then mark that spot with an 'O' to indicate a miss
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (Battleship_serverThread.lock) {
                                            System.out.println("edited grid");
                                            coordinates2[row][column] = "O ";
                                        }
                                    } else if ("X ".equals(coordinates2[row][column]) || "O ".equals(coordinates2[row][column])) ; else {
                                        synchronized (Battleship_serverThread.lock) {
                                            System.out.println("edited grid");
                                            coordinates2[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            player1m++; //keeps track of the number of misses that player made
                        }
                    }
                    
                    /*synchronized (readyLock) { //sets this thread's status to ready
                     Battleship_serverThread.READY1 = true;
                     readyLock.notifyAll();
                     System.out.println("notify works");
                     try {
                     while (!Battleship_serverThread.READY2) {
                     readyLock.wait();
                     }
                     System.out.println("woke up from sleep");
                     } catch (InterruptedException ex) {
                     Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     readyLock.notifyAll();
                     }
                     Battleship_serverThread.READY1 = false; */
                    System.out.println("ready to set ready");
                    Battleship_serverThread.shotFired.countDown();
                    System.out.println("set ready");
                    try {
                        Battleship_serverThread.shotFired.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("continued");
                    //checks if the game is over
                    if (player1 == 17 && player2 == 17) { //in the case of a draw:
                        out.writeUTF("1");
                        //out.writeUTF("GAME OVER!");
                        out.writeUTF("It was a draw! Both you and your opponent destroyed each other's battleships in " + TRIES1 + " shots.");
                        out.flush();
                        break;
                    } else if (player1 == 17) { //in the case of a win:
                        out.writeUTF("1");
                        //out.writeUTF("GAME OVER!");
                        out.writeUTF("You won in " + TRIES1 + " shots.");
                        out.flush();
                        break;
                    } else if (player2 == 17) { //in the case of a loss:
                        out.writeUTF("1");
                        //out.writeUTF("GAME OVER!");
                        out.writeUTF("You lost in " + TRIES2 + " shots.");
                        out.flush();
                        break;
                    } else {
                        out.writeUTF("0");
                        out.flush();
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException exe) {
                System.err.println(exe);
            } catch (IOException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (player == 2) { //deals with the client assuming it is player2
            ObjectInputStream in = null; //declares Object i/o streams
            ObjectOutputStream out = null;
            try {
                in = new ObjectInputStream(client.getInputStream()); //initializes i/o streams
                out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(coordinates2); //writes the two 10x10 grids to the client
                out.writeObject(coordinates1);
                synchronized (lock) { //reads the location of all of the player's battleships, makes them available to the corresponding thread and stores them
                    //System.out.println("synchronized lock works");
                    xy2 = (String[]) in.readObject();
                }
               
                /*synchronized (readyLock) { //sets this thread's status to ready
                 Battleship_serverThread.READY2 = true;
                 readyLock.notifyAll();
                 System.out.println("notify works");
                 try {
                 while (!Battleship_serverThread.READY1) {
                 readyLock.wait();
                 }
                 System.out.println("woke up from sleep");
                 } catch (InterruptedException ex) {
                 Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 readyLock.notifyAll();
                 }*/
                Battleship_serverThread.shipsSet.countDown();
                try {
                    Battleship_serverThread.shipsSet.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (true) { //game loop
                    Battleship_serverThread.shotFired = new CountDownLatch(2);
                    //System.out.println("started game loop");
                    out.writeObject(coordinates1); //writes the updated grids once each iteration
                    out.writeObject(coordinates2);
                    String target = in.readUTF();  //reads the target location from the client
                    //Battleship_serverThread.READY2 = false;
                    System.out.println("read target");
                    synchronized (lock) { //increments the counter which keeps track of number of missiles fired
                        System.out.println("incremented tries");
                        TRIES2.addAndGet(1);
                    }
                    y = ((int) (target.charAt(1))); //stores the y-axis value of the target coordinate
                    x = getIt.getTargetX(target); //determines and then stores the x-axis value of the coordinate
                    for (int loop = 0; loop < xy2.length; loop++) { //checks if the target coordinate corresponds with any of the opponents battleship coordinates
                        if (target.equals(xy2[loop])) { //if it does then set that coordinate to 'X' to indicate a hit
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates1[row][column] = "X ";
                                        }
                                    } else if ("X ".equals(coordinates1[row][column]) || "O ".equals(coordinates1[row][column])) {
                                    } else {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates1[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            player2++; //keeps track of the number of on target shots(ie-hits) fired
                        } else { // if it doesn't then mark a '0' to indicate a miss 
                            for (int row = 0; row <= 10; row++) {
                                for (int column = 0; column <= 10; column++) {
                                    if (column == x && row == y) {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates1[row][column] = "O ";
                                        }
                                    } else if ("X ".equals(coordinates1[row][column]) || "O ".equals(coordinates1[row][column])) ; else {
                                        synchronized (lock) {
                                            System.out.println("edited grid");
                                            coordinates1[row][column] = "~ ";
                                        }
                                    }
                                }
                            }
                            player2m++; //keeps track of the number of off target shots(ie-misses) fired
                        }
                    }
                   
                    /*synchronized (readyLock) { //sets this thread's status to ready
                     Battleship_serverThread.READY2 = true;
                     readyLock.notifyAll();
                     System.out.println("notify works");
                     try {
                     while (!Battleship_serverThread.READY1) {
                     readyLock.wait();
                     }
                     System.out.println("woke up from sleep");
                     } catch (InterruptedException ex) {
                     Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     readyLock.notifyAll();
                     }
                     Battleship_serverThread.READY2 = false;*/
                    System.out.println("ready to set ready");
                    Battleship_serverThread.shotFired.countDown();
                    System.out.println("set ready");
                    try {
                        Battleship_serverThread.shotFired.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("continued");
                    //checks if game is over
                    if (player1 == 17 && player2 == 17) { //in the case of a draw:
                        out.writeUTF("1");
                        out.writeUTF("Both you and your opponent destroyed each other's battleships in " + TRIES1 + " shots.");
                        break;
                    }
                    if (player2 == 17) { //in the case of a win:
                        out.writeUTF("1");
                        out.writeUTF("You won in " + TRIES2 + " shots.");
                        break;
                    } else if (player1 == 17) { //in the case of a loss:
                        out.writeUTF("1");
                        out.writeUTF("You lost in " + TRIES1 + " shots.");
                        break;
                    } else {
                        out.writeUTF("0");
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException exe) {
                System.err.println(exe);
            } catch (IOException ex) {
                Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    client.close();
                } catch (IOException ex) {
                    Logger.getLogger(Battleship_server_clientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
