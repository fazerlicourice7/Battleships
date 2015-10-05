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
public class getInput {
//hold the corresponding values of the x axis on the grid

    int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9;
    int x;

    /**
     * Finds the numerical value for the x-axis from the letter value.
     *
     * @param target the target location from which the x-axis letter value is
     * obtained
     * @return
     */
    public int getTargetX(String target) {

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
        return x;
    }
}
