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

/**
 * @author fazer
 */
public class printGrids {

    /**
     * Prints your grid.
     *
     * @param coordinates1
     */
    public void grid1(String[][] coordinates1) {
        System.out.println("  A B C D E F G H I J");
        for (int loop = 0; loop < 10; loop++) {
            System.out.print(loop + " ");
            for (int loop2 = 0; loop2 < 10; loop2++) {
                System.out.print(coordinates1[loop][loop2]);
            }
            System.out.println();
        }
    }

    /**
     * Prints your opponents grid. Your target grid.
     *
     * @param coordinates2
     */
    public void grid2(String[][] coordinates2) {
        System.out.println("  A B C D E F G H I J");
        for (int loop = 0; loop < 10; loop++) {
            System.out.print(loop + " ");
            for (int loop2 = 0; loop2 < 10; loop2++) {
                System.out.print(coordinates2[loop][loop2]);
            }
            System.out.println();
        }
    }
}
