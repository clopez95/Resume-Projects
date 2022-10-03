package com.productionSOS;

import java.io.IOException;

public class SOSSimpleGame extends SOSGame{

    public SOSSimpleGame()  {
    }

    public SOSSimpleGame(int numberOfComputerOpponents) {
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

    public void updateGameState(String turn, int row, int col)  {
        if (checkSOSCompletion(turn, row, col)){
            currentGameState = (getTurn() == "Blue") ? GameState.BLUE_WON : GameState.RED_WON;
            try {
                gameMoveFile.write(String.valueOf(currentGameState) + "\n");
                gameMoveFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (isDraw()){
            currentGameState = GameState.DRAW;
            try {
                gameMoveFile.write(String.valueOf(currentGameState) + "\n");
                gameMoveFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            if(twoComputers){
                setTurn((turn == "Blue")? "Red" : "Blue");
                for (SOSComputerOpponent comp: SOSComputerOpponent.compList) {
                    if (comp.player == getTurn()) {
                        comp.lookForMove();
                        break;
                    }
                }
            }
        }
    }

    public boolean checkSOSCompletion(String turn, int row, int col) {
        Cell token = getCell(row, col);
        if (token == Cell.BLUE_S || token == Cell.RED_S) {
            //Checking SOS on the same row, to the left of placed token
            if (col > 1) {
                if ((grid[row][col - 1] == Cell.BLUE_O || grid[row][col - 1] == Cell.RED_O) &&
                        (grid[row][col - 2] == Cell.BLUE_S || grid[row][col - 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row);
                    inner.add(col - 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if (col < TOTALROWSANDCOLUMNS - 2) {
                if ((grid[row][col + 1] == Cell.BLUE_O || grid[row][col + 1] == Cell.RED_O) &&
                        (grid[row][col + 2] == Cell.BLUE_S || grid[row][col + 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row);
                    inner.add(col + 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if (row > 1) {
                if ((grid[row - 1][col] == Cell.BLUE_O || grid[row - 1][col] == Cell.RED_O) &&
                       (grid[row - 2][col] == Cell.BLUE_S || grid[row - 2][col] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if(row < TOTALROWSANDCOLUMNS - 2){
                if ((grid[row + 1][col] == Cell.BLUE_O || grid[row + 1][col] == Cell.RED_O) &&
                        (grid[row + 2][col] == Cell.BLUE_S || grid[row + 2][col] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if((col < TOTALROWSANDCOLUMNS - 2) && (row < TOTALROWSANDCOLUMNS - 2)) {
                if ((grid[row + 1][col + 1] == Cell.BLUE_O || grid[row + 1][col + 1] == Cell.RED_O) &&
                        (grid[row + 2][col + 2] == Cell.BLUE_S || grid[row + 2][col + 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col + 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if(col > 1 && row > 1) {
                if ((grid[row - 1][col - 1] == Cell.BLUE_O || grid[row - 1][col - 1] == Cell.RED_O) &&
                        (grid[row - 2][col - 2] == Cell.BLUE_S || grid[row - 2][col - 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col - 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if(row < TOTALROWSANDCOLUMNS - 2 && col > 1) {
                if ((grid[row + 1][col - 1] == Cell.BLUE_O || grid[row + 1][col - 1] == Cell.RED_O) &&
                        (grid[row + 2][col - 2] == Cell.BLUE_S || grid[row + 2][col - 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row + 2);
                    inner.add(col - 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            if(row > 1 && col < TOTALROWSANDCOLUMNS - 2) {
                if ((grid[row - 1][col + 1] == Cell.BLUE_O || grid[row - 1][col + 1] == Cell.RED_O) &&
                        (grid[row - 2][col + 2] == Cell.BLUE_S || grid[row - 2][col + 2] == Cell.RED_S)) {
                    inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                    inner.add(row);
                    inner.add(col);
                    inner.add(row - 2);
                    inner.add(col + 2);
                    completedSOSTokensList.add(inner);
                    return true;
                }
            }
            else
                return false;

        }

    if(token == Cell.BLUE_O || token == Cell.RED_O){
        if(col > 0 && col < TOTALROWSANDCOLUMNS - 1){
            if((grid[row][col + 1] == Cell.BLUE_S || grid[row][col + 1] == Cell.RED_S) &&
                    (grid[row][col - 1] == Cell.BLUE_S || grid[row][col - 1] == Cell.RED_S)){
                inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                inner.add(row);
                inner.add(col - 1);
                inner.add(row);
                inner.add(col + 1);
                completedSOSTokensList.add(inner);
                return true;
            }
        }
        if(row > 0 && row < TOTALROWSANDCOLUMNS - 1){
            if((grid[row + 1][col] == Cell.BLUE_S || grid[row + 1][col] == Cell.RED_S) &&
                    (grid[row - 1][col] == Cell.BLUE_S || grid[row - 1][col] == Cell.RED_S)){
                inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                inner.add(row - 1);
                inner.add(col);
                inner.add(row + 1);
                inner.add(col);
                completedSOSTokensList.add(inner);
                return true;
            }
        }
        if((row < TOTALROWSANDCOLUMNS - 1 && col > 0) && (row > 0 && col < TOTALROWSANDCOLUMNS - 1)){
            if((grid[row - 1][col + 1] == Cell.BLUE_S || grid[row - 1][col + 1] == Cell.RED_S) &&
                    (grid[row + 1][col - 1] == Cell.BLUE_S || grid[row + 1][col - 1] == Cell.RED_S)){
                inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                inner.add(row - 1);
                inner.add(col + 1);
                inner.add(row + 1);
                inner.add(col - 1);
                completedSOSTokensList.add(inner);
                return true;
            }
        }
        if((row > 0 && col > 0) && (row < TOTALROWSANDCOLUMNS - 1 && col < TOTALROWSANDCOLUMNS - 1)){
            if((grid[row + 1][col + 1] == Cell.BLUE_S || grid[row + 1][col + 1] == Cell.RED_S) &&
                    (grid[row - 1][col - 1] == Cell.BLUE_S || grid[row - 1][col - 1] == Cell.RED_S)){
                inner.add((turn == "Blue")? 1: 2); //1 = blue win, 2 = red win
                inner.add(row + 1);
                inner.add(col + 1);
                inner.add(row - 1);
                inner.add(col - 1);
                completedSOSTokensList.add(inner);
                return true;
            }
        }
        else{
            return false;
        }
    }

    return false;
}

    public boolean isDraw(){
        for (int row = 0; row < TOTALROWSANDCOLUMNS; row++) {
            for (int col = 0; col < TOTALROWSANDCOLUMNS; col++) {
                if (grid[row][col] == Cell.EMPTY) {
                    return false; // an empty cell found, not draw
                }
            }
        }
        return true;
    }
}
