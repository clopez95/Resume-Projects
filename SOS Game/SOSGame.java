package com.productionSOS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SOSGame {
    public static int TOTALROWSANDCOLUMNS;

    FileWriter gameMoveFile;

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public enum Cell{
        EMPTY, BLUE_S, BLUE_O, RED_S, RED_O
    }
    public Cell[][] grid;
    protected String turn;

    boolean completionThisTurn;
    boolean twoComputers;

    public enum GameState{
        PLAYING, DRAW, BLUE_WON, RED_WON
    }
    protected GameState currentGameState;

    ArrayList<ArrayList<Integer>> completedSOSTokensList = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> inner = new ArrayList<Integer>();

    public SOSGame()  {
        grid = new Cell[TOTALROWSANDCOLUMNS][TOTALROWSANDCOLUMNS];
        initGame();
    }

    public SOSGame(int boardSize) {
        TOTALROWSANDCOLUMNS = boardSize;
        grid = new Cell[TOTALROWSANDCOLUMNS][TOTALROWSANDCOLUMNS];
        initGame();
    }

    private void initGame()  {
        try {
            gameMoveFile = new FileWriter("SOSGameMoveFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int row = 0; row < TOTALROWSANDCOLUMNS; ++row) {
            for (int col = 0; col < TOTALROWSANDCOLUMNS; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        currentGameState = GameState.PLAYING;
        turn = "Blue";
        completedSOSTokensList.clear();
        inner.clear();
        if(!SOSComputerOpponent.compList.isEmpty() && getGameState() == GameState.PLAYING){
            for (SOSComputerOpponent comp: SOSComputerOpponent.compList) {
                if (comp.player == turn) {
                    comp.lookForMove();
                    turn = (turn == "Blue") ? "Red" : "Blue";
                    break;
                }
            }
        }
    }

    public void resetGame() {
        initGame();
    }

    public void makeMove(int row, int col)  {
        if (row >= 0 && row < TOTALROWSANDCOLUMNS && col >= 0 && col < TOTALROWSANDCOLUMNS && grid[row][col] == Cell.EMPTY) {
            if (turn == "Red") {
                grid[row][col] = (SOSGUI.sRedRadioButton.isSelected()) ? Cell.RED_S : Cell.RED_O;
            } else if (turn == "Blue") {
                grid[row][col] = (SOSGUI.sBlueRadioButton.isSelected()) ? Cell.BLUE_S : Cell.BLUE_O;
            }
            recordGameMoves(getCell(row, col), row, col);
            updateGameState(turn, row, col);
            turn = (turn == "Blue") ? "Red" : "Blue";
            if(!SOSComputerOpponent.compList.isEmpty() && getGameState() == GameState.PLAYING){
                for (SOSComputerOpponent comp: SOSComputerOpponent.compList){
                    if(comp.player == turn){
                        comp.lookForMove();
                        if(SOSGUI.simpleGameButton.isSelected() || !completionThisTurn) {
                            turn = (turn == "Blue") ? "Red" : "Blue";
                        }
                        else{
                            while (completionThisTurn) {
                                if (currentGameState != GameState.PLAYING)
                                    break;
                                comp.lookForMove();
                            }

                        }
                        break;
                    }
                }
            }
        }
        else {
            System.out.println("makeMove out of bounds error or cell occupied.");
        }
    }
    public void updateGameState(String turn, int row, int col) {
        //Overwritten
    }

    public boolean isDraw(){
        //overwritten
        return false;
    }

    public boolean checkSOSCompletion(String turn, int row, int col){
        //overwritten
        return false;
    }

    public void recordGameMoves(Cell placedToken, int row, int col)  {
        try {
            gameMoveFile.write(placedToken + ": (" + row + "," + col + ")" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGrid(int row, int col, Cell token){
        if(token == Cell.BLUE_S){
                grid[row][col] = Cell.BLUE_S;
        }
        if(token == Cell.BLUE_O) {
            grid[row][col] = Cell.BLUE_O;
        }
        if(token == Cell.RED_S) {
            grid[row][col] = Cell.RED_S;
        }
        if(token == Cell.RED_O) {
            grid[row][col] = Cell.RED_O;
        }
    }

    public  Cell getCell(int row, int col) {
        if (row >= 0 && row < TOTALROWSANDCOLUMNS && col >= 0 && col < TOTALROWSANDCOLUMNS) {
            return grid[row][col];
        } else {
            return null;
        }
    }
    public String getTurn(){
        return turn;
    }

    public void setTurn(String player){
        this.turn = player;
    }

    public int getTotalRowsAndColumns(){
        return TOTALROWSANDCOLUMNS;
    }

    public GameState getGameState(){
        return currentGameState;
    }
}