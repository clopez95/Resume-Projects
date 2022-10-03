package com.productionSOS;

import java.util.ArrayList;
import java.util.Random;

import static com.productionSOS.SOSGame.Cell;
import static com.productionSOS.SOSGame.TOTALROWSANDCOLUMNS;

public class SOSComputerOpponent {
    public static ArrayList<SOSComputerOpponent> compList = new ArrayList<SOSComputerOpponent>();
    String player;
    SOSGame game;
    Random rand;
    int randomRow;
    int randomCol;
    boolean needRandomMove;

    SOSComputerOpponent(String player, SOSGame game) {
        this.player = player;
        this.game = game;
        compList.add(this);
        for (SOSComputerOpponent comp: SOSComputerOpponent.compList) {
            if (comp.player == game.getTurn()) {
                System.out.println("Current turn: " + game.turn);
                comp.lookForMove();
                game.setTurn("Red");
                }
            }
        }

    public void lookForMove() {
        needRandomMove = true;
        outerloop:
        for (int row = 0; row < TOTALROWSANDCOLUMNS; row++) {
            for (int col = 0; col < TOTALROWSANDCOLUMNS; col++) {
                if (game.grid[row][col] == SOSGame.Cell.BLUE_S || game.grid[row][col] == SOSGame.Cell.RED_S) {
                    //looks for a move on a horizontal plane, to the right
                    if (col > 1) {
                        if ((game.grid[row][col - 1] == SOSGame.Cell.BLUE_O || game.grid[row][col - 1] == SOSGame.Cell.RED_O) &&
                                (game.grid[row][col - 2] == SOSGame.Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row, col - 2, 'S');
                            break outerloop;
                        }
                    }
                    //looks for a move on a horizontal plane, to the left
                    if (col < TOTALROWSANDCOLUMNS - 2) {
                        if ((game.grid[row][col + 1] == SOSGame.Cell.BLUE_O || game.grid[row][col + 1] == SOSGame.Cell.RED_O) &&
                                (game.grid[row][col + 2] == SOSGame.Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row, col + 2, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on vertical plane, looking up
                    if (row > 1) {
                        if ((game.grid[row - 1][col] == Cell.BLUE_O || game.grid[row - 1][col] == Cell.RED_O) &&
                                (game.grid[row - 2][col] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row - 2, col, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on vertical plane, looking down
                    if(row < TOTALROWSANDCOLUMNS - 2){
                        if ((game.grid[row + 1][col] == Cell.BLUE_O || game.grid[row + 1][col] == Cell.RED_O) &&
                                (game.grid[row + 2][col] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row + 2, col, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on diagonal plane, looking down and to the right
                    if((col < TOTALROWSANDCOLUMNS - 2) && (row < TOTALROWSANDCOLUMNS - 2)) {
                        if ((game.grid[row + 1][col + 1] == Cell.BLUE_O || game.grid[row + 1][col + 1] == Cell.RED_O) &&
                                (game.grid[row + 2][col + 2] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row + 2, col + 2, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on diagonal plane, looking up and to the left
                    if(col > 1 && row > 1) {
                        if ((game.grid[row - 1][col - 1] == Cell.BLUE_O || game.grid[row - 1][col - 1] == Cell.RED_O) &&
                                (game.grid[row - 2][col - 2] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row - 2, col - 2, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on diagonal plane, looking down and to the left
                    if(row < TOTALROWSANDCOLUMNS - 2 && col > 1) {
                        if ((game.grid[row + 1][col - 1] == Cell.BLUE_O || game.grid[row + 1][col - 1] == Cell.RED_O) &&
                                (game.grid[row + 2][col - 2] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row + 2, col - 2, 'S');
                            break outerloop;
                        }
                    }
                    //looking for move on diagonal plane, looking down and to the right
                    if(row > 1 && col < TOTALROWSANDCOLUMNS - 2) {
                        if ((game.grid[row - 1][col + 1] == Cell.BLUE_O || game.grid[row - 1][col + 1] == Cell.RED_O) &&
                                (game.grid[row - 2][col + 2] == Cell.EMPTY)) {
                            needRandomMove = false;
                            makeMove(row - 2, col + 2, 'S');
                            break outerloop;
                        }
                    }
                }

                if (game.grid[row][col] == SOSGame.Cell.EMPTY) {
                    //looking for move on the horizontal plane
                    if(col > 0 && col < TOTALROWSANDCOLUMNS - 1){
                        if((game.grid[row][col + 1] == Cell.BLUE_S || game.grid[row][col + 1] == Cell.RED_S) &&
                                (game.grid[row][col - 1] == Cell.BLUE_S || game.grid[row][col - 1] == Cell.RED_S)){
                            needRandomMove = false;
                            makeMove(row, col, 'O');
                            break outerloop;
                        }
                    }
                    //looking for move on the Vertical plane
                    if(row > 0 && row < TOTALROWSANDCOLUMNS - 1){
                        if((game.grid[row + 1][col] == Cell.BLUE_S || game.grid[row + 1][col] == Cell.RED_S) &&
                                (game.grid[row - 1][col] == Cell.BLUE_S || game.grid[row - 1][col] == Cell.RED_S)){
                            needRandomMove = false;
                            makeMove(row, col, 'O');
                            break outerloop;
                        }
                    }
                    //looking for move on the diagonal / plane
                    if((row < TOTALROWSANDCOLUMNS - 1 && col > 0) && (row > 0 && col < TOTALROWSANDCOLUMNS - 1)){
                        if((game.grid[row - 1][col + 1] == Cell.BLUE_S || game.grid[row - 1][col + 1] == Cell.RED_S) &&
                                (game.grid[row + 1][col - 1] == Cell.BLUE_S || game.grid[row + 1][col - 1] == Cell.RED_S)){
                            needRandomMove = false;
                            makeMove(row, col, 'O');
                            break outerloop;
                        }
                    }
                    //looking for move on the diagonal \ plane
                    if((row > 0 && col > 0) && (row < TOTALROWSANDCOLUMNS - 1 && col < TOTALROWSANDCOLUMNS - 1)){
                        if((game.grid[row + 1][col + 1] == Cell.BLUE_S || game.grid[row + 1][col + 1] == Cell.RED_S) &&
                                (game.grid[row - 1][col - 1] == Cell.BLUE_S || game.grid[row - 1][col - 1] == Cell.RED_S)){
                            needRandomMove = false;
                            makeMove(row, col, 'O');
                            break outerloop;
                        }
                    }
                }
            }
        }

        if(needRandomMove){
            rand = new Random();
            while(true) {
                randomRow = rand.nextInt(TOTALROWSANDCOLUMNS);
                randomCol = rand.nextInt(TOTALROWSANDCOLUMNS);
                if(game.grid[randomRow][randomCol] == SOSGame.Cell.EMPTY){
                    makeMove(randomRow, randomCol, 'R');
                    break;
                }
            }
        }
    }

    public void makeMove(int row, int col, char token){
        if (token == 'S'){
            game.grid[row][col] = (this.player == "Blue")? Cell.BLUE_S : Cell.RED_S;
        }
        else if (token == 'O'){
            game.grid[row][col] = (this.player == "Blue")? Cell.BLUE_O : Cell.RED_O;
        }
        else if (token == 'R'){
            int randBetweenOneAndTwo = rand.nextInt(2);
            if(this.player == "Blue"){
                game.grid[row][col] = (randBetweenOneAndTwo == 0)? Cell.BLUE_O : Cell.BLUE_S;
            }
            else if(this.player == "Red"){
                game.grid[row][col] = (randBetweenOneAndTwo == 0)? Cell.RED_O : Cell.RED_S;
            }
        }
        game.recordGameMoves(game.getCell(row, col), row, col);
        game.updateGameState(this.player, row, col);

    }

}
