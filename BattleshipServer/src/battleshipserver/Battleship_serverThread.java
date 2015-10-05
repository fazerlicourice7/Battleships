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
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fazerlicourice71256
 */
public class Battleship_serverThread extends Thread {
    static volatile AtomicInteger TRIES1, TRIES2; //counts the number of attempts the user made at the opponent's battleships
    private volatile String[][] coordinates1 = new String[10][10], coordinates2 = new String[10][10]; //hold the grids for both the players- coordinates1 holds player1's battleships- ie. player1 is aiming at coordinates2
    public final Socket client1;
    public Socket client2;
    public int PORT = 54321;
    static final Object lock = new Object(); //object that is used as the intrisic lock for the synchronized statements
    static final Object readyLock = new Object(); //intrinsic lock for the two client threads. This lock is used only for the notify(), wait(), and notifyAll() methods
    static volatile String[] xy1, xy2;

    /**
     * Constructor
     * @param client1 
     */
    Battleship_serverThread(Socket client1) {
        super("Battleship_serverThread");
        this.client1 = client1;
    }

    /**
     * Creates two threads. One for each client.
     *
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    @Override
    public synchronized void run() throws SecurityException, IllegalArgumentException {
        try (
                ServerSocket serversocket = new ServerSocket(PORT)) {
            client2 = serversocket.accept();
            int player = 2;
            Runnable CLIENT2 = new battleshipserver.Battleship_server_clientThread(client2, player);
            new Thread(CLIENT2).start();
        } catch (IOException ex) {
            Logger.getLogger(Battleship_serverThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            int player = 1;
            Runnable CLIENT1;
            CLIENT1 = new battleshipserver.Battleship_server_clientThread(client1, player);
            new Thread(CLIENT1).start();
        }
    }
}
