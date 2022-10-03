package com.productionSOS;

import java.util.Scanner;

public class SOSConsole {
    private SOSGame game;

    public SOSConsole(SOSGame board){
        this.game = board;
    }

    public void displayBoard(){
        System.out.println("-------");
        for (int row = 0; row < game.getTotalRowsAndColumns(); row++) {
            for (int col = 0; col < game.getTotalRowsAndColumns(); col++) {
                if(col == 0){
                    System.out.println();
                }
                System.out.print("|" + letter(game.getCell(row, col)));
                System.out.print("|");
            }
        }
        System.out.println("\n-------");

    }

    private char letter(SOSGame.Cell cell){
        if (cell == SOSGame.Cell.RED_S)
            return 'S';
        else if (cell == SOSGame.Cell.RED_O)
            return 'O';
        else if (cell == SOSGame.Cell.BLUE_O)
            return 'O';
        else if (cell == SOSGame.Cell.BLUE_S)
            return 'S';
        else
            return ' ';
    }

    public void play(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the SOS Game!");
        while (true){
            int row, column;
            System.out.println("Current player: " + game.getTurn());
            System.out.print("Move at row: ");
            row = in.nextInt();
            System.out.print("Move at column: ");
            column = in.nextInt();
            if(row < 0 || row > game.getTotalRowsAndColumns() || column < 0 || column > game.getTotalRowsAndColumns())
                System.out.println("Invalid move at (" + row + "," + column + ")");
            else{
                game.makeMove(row, column);
                displayBoard();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //var game = new SOSGame();
        //new SOSGUI(game);
        //new SOSConsole(game).play();
    }
}




