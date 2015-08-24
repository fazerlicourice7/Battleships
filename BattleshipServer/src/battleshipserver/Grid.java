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

/**
 *
 * @author fazer
 */
public class Grid {

    String[][] coordinates;

    /**
     * Initializes the 2 dimensional array with blanks (~).
     *
     * @return
     */
    public synchronized String[][] init() {
        //outer loop is for the rows
        for (int loop = 0; loop < 10; loop++) {
            //the inner loop is for the columns
            for (int loop2 = 0; loop2 < 10; loop2++) {
                coordinates[loop][loop2] = "~ ";
            }
        }
        return coordinates;
    }

}
