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
import java.net.Socket;

/**
 * @author fazerlicourice71256
 */
/**
 * This is the client side of an application called Battleships.
 *
 */
public class Battleship_client {

    public static int PORT;
    public static String host;
    ObjectInputStream in;
    ObjectOutputStream out;
    String coordinates1[][];
    String coordinates2[][];
    //public static Socket connection;

    public void doStuff() throws IOException, ClassNotFoundException {
        try (Socket connection = new Socket(host, PORT)) {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
        }
        coordinates1 = (String[][]) in.readObject();
        coordinates2 = (String[][]) in.readObject();

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        Battleship_client obj = new Battleship_client();
        obj.doStuff();
    }
}
