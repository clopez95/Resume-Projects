package com.productionSOS;

import java.io.IOException;
import java.util.ArrayList;

public class SOSGeneralGame extends SOSGame{
    int blueSOSCompletions = 0;
    int redSOSCompletions = 0;
    boolean flagForAllCellsFilled;
    boolean twoComputers;

    public SOSGeneralGame() {
    }

    public SOSGeneralGame(int numberOfComputerOpponents)  {
        if(numberOfComputerOpponents == 1) {
            if (SOSGUI.blueComputerButton.isSelected()) {
                new SOSComputerOpponent("Blue", this);
            } else {
                new SOSComputerOpponent("Red", this);
            }
        }
        else{
            twoComputers = true;
            new SOSComputerOpponent("Red", this);
            new SOSComputerOpponent("Blue", this);
        }
    }

    public void updateGameState(String currentTurn, int row, int col) {
        if(isDraw()){
            blueSOSCompletions = 0;
            redSOSCompletions = 0;
            System.out.println("Game Drawn");
            currentGameState = GameState.DRAW;
            try {
                gameMoveFile.write(String.valueOf(currentGameState) + "\n");
                gameMoveFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flagForAllCellsFilled) {
            System.out.println("Blue Comp score: " + blueSOSCompletions);
            System.out.println("Red Comp score " + redSOSCompletions);
            if (blueSOSCompletions > redSOSCompletions) {
                blueSOSCompletions = 0;
                redSOSCompletions = 0;
                System.out.println("GameWon Blue");
                currentGameState = GameState.BLUE_WON;
                try {
                    gameMoveFile.write(String.valueOf(currentGameState) + "\n");
                    gameMoveFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (redSOSCompletions > blueSOSCompletions) {
                redSOSCompletions = 0;
                blueSOSCompletions = 0;
                System.out.println("GameWon red");
                currentGameState = GameState.RED_WON;
                try {
                    gameMoveFile.write(String.valueOf(currentGameState) + "\n");
                    gameMoveFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            checkSOSCompletion(currentTurn, row, col);

        if(twoComputers && currentGameState == GameState.PLAYING){
            System.out.println("Blue Comp score: " + blueSOSCompletions);
            System.out.println("Red Comp score " + redSOSCompletions);
            if(completionThisTurn){
                turn = (turn == "Blue")? "Red" : "Blue";
                for (SOSComputerOpponent comp: SOSComputerOpponent.compList) {
                    if (turn == comp.player && currentGameState == GameState.PLAYING) {
                        System.out.println("\nCurrent turn: " + turn);
                        comp.lookForMove();
                        break;
                    }
                }
            }
            if(!completionThisTurn) {
                turn = (turn == "Blue") ? "Red" : "Blue";
                for (SOSComputerOpponent comp: SOSComputerOpponent.compList) {
                    if (turn == comp.player && currentGameState == GameState.PLAYING) {
                        System.out.println("\nCurrent turn: " + turn);
                        comp.lookForMove();
                        break;
                    }

                }
            }
        }
    }

    public boolean checkSOSCompletion(String currentTurn, int row, int col){
        completionThisTurn = false;
        Cell token = getCell(row, col);
        //outer if statement deals with the last token played being an S
        if (token == Cell.BLUE_S || token == Cell.RED_S) {
            //second if statement is a constraint to handle index errors
            if (col > 1) {
                //third if statement looks for a completed SOS around the last played token.
                if ((grid[row][col - 1] == Cell.BLUE_O || grid[row][col - 1] == Cell.RED_O) &&
                        (grid[row][col - 2] == Cell.BLUE_S || grid[row][col - 2] == Cell.RED_S)) {
                    //If SOS is formed, adds the coordinates to an array of arrays in order for the GUI class to draw the lines on completed SOS
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row);
                    inner.add(col - 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if (col < TOTALROWSANDCOLUMNS - 2) {
                if ((grid[row][col + 1] == Cell.BLUE_O || grid[row][col + 1] == Cell.RED_O) &&
                        (grid[row][col + 2] == Cell.BLUE_S || grid[row][col + 2] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row);
                    inner.add(col + 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if (row > 1) {
                if ((grid[row - 1][col] == Cell.BLUE_O || grid[row - 1][col] == Cell.RED_O) &&
                        (grid[row - 2][col] == Cell.BLUE_S || grid[row - 2][col] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if(row < TOTALROWSANDCOLUMNS - 2){
                if ((grid[row + 1][col] == Cell.BLUE_O || grid[row + 1][col] == Cell.RED_O) &&
                        (grid[row + 2][col] == Cell.BLUE_S || grid[row + 2][col] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if((col < TOTALROWSANDCOLUMNS - 2) && (row < TOTALROWSANDCOLUMNS - 2)) {
                if ((grid[row + 1][col + 1] == Cell.BLUE_O || grid[row + 1][col + 1] == Cell.RED_O) &&
                        (grid[row + 2][col + 2] == Cell.BLUE_S || grid[row + 2][col + 2] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col + 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if(col > 1 && row > 1) {
                if ((grid[row - 1][col - 1] == Cell.BLUE_O || grid[row - 1][col - 1] == Cell.RED_O) &&
                        (grid[row - 2][col - 2] == Cell.BLUE_S || grid[row - 2][col - 2] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col - 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if(row < TOTALROWSANDCOLUMNS - 2 && col > 1) {
                if ((grid[row + 1][col - 1] == Cell.BLUE_O || grid[row + 1][col - 1] == Cell.RED_O) &&
                        (grid[row + 2][col - 2] == Cell.BLUE_S || grid[row + 2][col - 2] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col - 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if(row > 1 && col < TOTALROWSANDCOLUMNS - 2) {
                if ((grid[row - 1][col + 1] == Cell.BLUE_O || grid[row - 1][col + 1] == Cell.RED_O) &&
                        (grid[row - 2][col + 2] == Cell.BLUE_S || grid[row - 2][col + 2] == Cell.RED_S)) {
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col + 2);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
        }

        if(token == Cell.BLUE_O || token == Cell.RED_O){
            if(col > 0 && col < TOTALROWSANDCOLUMNS - 1){
                if((grid[row][col + 1] == Cell.BLUE_S || grid[row][col + 1] == Cell.RED_S) &&
                        (grid[row][col - 1] == Cell.BLUE_S || grid[row][col - 1] == Cell.RED_S)){
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row);
                    inner.add(col - 1);
                    inner.add(row);
                    inner.add(col + 1);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if(row > 0 && row < TOTALROWSANDCOLUMNS - 1){
                if((grid[row + 1][col] == Cell.BLUE_S || grid[row + 1][col] == Cell.RED_S) &&
                        (grid[row - 1][col] == Cell.BLUE_S || grid[row - 1][col] == Cell.RED_S)){
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row - 1);
                    inner.add(col);
                    inner.add(row + 1);
                    inner.add(col);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
            if((row < TOTALROWSANDCOLUMNS - 1 && col > 0) && (row > 0 && col < TOTALROWSANDCOLUMNS - 1)){
                if((grid[row - 1][col + 1] == Cell.BLUE_S || grid[row - 1][col + 1] == Cell.RED_S) &&
                        (grid[row + 1][col - 1] == Cell.BLUE_S || grid[row + 1][col - 1] == Cell.RED_S)){
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row - 1);
                    inner.add(col + 1);
                    inner.add(row + 1);
                    inner.add(col - 1);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }

            }
            if((row > 0 && col > 0) && (row < TOTALROWSANDCOLUMNS - 1 && col < TOTALROWSANDCOLUMNS - 1)){
                if((grid[row + 1][col + 1] == Cell.BLUE_S || grid[row + 1][col + 1] == Cell.RED_S) &&
                        (grid[row - 1][col - 1] == Cell.BLUE_S || grid[row - 1][col - 1] == Cell.RED_S)){
                    inner.add((currentTurn == "Blue")? 1: 2); //1 = blue completion, 2 = red completion
                    inner.add(row + 1);
                    inner.add(col + 1);
                    inner.add(row - 1);
                    inner.add(col - 1);
                    completedSOSTokensList.add(new ArrayList<Integer> (inner));
                    inner.clear();
                    if(currentTurn == "Blue") {
                        blueSOSCompletions += 1;
                    }else {
                        redSOSCompletions += 1;
                    }
                    completionThisTurn = true;
                }
            }
        }

        if(completionThisTurn){
            turn = (currentTurn == "Blue")? "Red" : "Blue";
            return true;
            }

        return false;
    }

    public boolean isDraw(){
        for (int row = 0; row < TOTALROWSANDCOLUMNS; row++) {
            for (int col = 0; col < TOTALROWSANDCOLUMNS; col++) {
                if (grid[row][col] == Cell.EMPTY) {
                    flagForAllCellsFilled = false;
                    return false;// an empty cell found, not draw
                }
                else
                    flagForAllCellsFilled = true;
            }
        }
        if(flagForAllCellsFilled && (blueSOSCompletions == redSOSCompletions))
            return true;
        else
            return false;
    }
}

