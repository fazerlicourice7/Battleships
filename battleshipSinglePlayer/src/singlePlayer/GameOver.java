/*
 * Copyright (C) 2015 18balanagav
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
package singlePlayer;

/**
 * Checks to see if game is over
 *
 * @author 18balanagav
 */
public class GameOver {

    /**
     *
     * @param coordinates
     * @return
     */
    public boolean isGameOver(boolean[][] coordinates) {
        int numHits = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (coordinates[i][j]) {
                    numHits++;
                }
            }
        }
        return numHits == 17;
    }
    
    /**
     * Game over interface
     * @param winner 
     */
    public void GameOver(String winner, int turns){
        //** call new activity, whatever. Tell User who won and in how many turns
    }
}
