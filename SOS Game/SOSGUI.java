package com.productionSOS;

import java.awt.event.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class SOSGUI extends JFrame implements ActionListener {
    private static int CELL_SIZE;
    public static int frameSize = 1000;
    public static int SYMBOL_STROKE_WIDTH = 50;
    JFormattedTextField textFieldBoardSize;
    private JButton buttonSubmitBoardSize;
    static JRadioButton simpleGameButton;
    static JRadioButton generalGameButton;
    static JRadioButton sRedRadioButton;
    static JRadioButton oRedRadioButton;
    static JRadioButton sBlueRadioButton;
    static JRadioButton oBlueRadioButton;
    static JRadioButton bluePlayerButton;
    static JRadioButton blueComputerButton;
    static JRadioButton redPlayerButton;
    static JRadioButton redComputerButton;
    private static JPanel bluePlayerMovePanel;
    private static JPanel redPlayerMovePanel;
    private JLabel labelBoardSize;

    private JPanel gameModePanel;
    private static JLabel gameStatusBar;

    CountDownLatch boardGridLatch = new CountDownLatch(1);

    private SOSGame game;

    public SOSGUI() throws InterruptedException {
        setupFrame();
        setupPlayersMoveButtons();
        setupGameModeButtons();
        setupBoardSize();
        boardGridLatch.await();
        if(this.simpleGameButton.isSelected()){
            if(redComputerButton.isSelected() && blueComputerButton.isSelected()) {
                game = new SOSSimpleGame(2);
            }
            else if(redComputerButton.isSelected() || blueComputerButton.isSelected()){
                game = new SOSSimpleGame(1);
            }
            else{
                game = new SOSSimpleGame();
            }
        }
        if(this.generalGameButton.isSelected()){
            if(redComputerButton.isSelected() && blueComputerButton.isSelected()) {
                game = new SOSGeneralGame(2);
            }
            else if(redComputerButton.isSelected() || blueComputerButton.isSelected()){
                game = new SOSGeneralGame(1);
            }
            else{
                game = new SOSGeneralGame();
            }
        }
        setupPlayingBoardGrid();
    }

    private void setupFrame() {
        this.setTitle("SOS GAME");
        this.setSize(frameSize, frameSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void setupPlayersMoveButtons() {
        bluePlayerMovePanel = new JPanel();
        bluePlayerMovePanel.setSize(250, 120);

        bluePlayerButton = new JRadioButton("Blue Player");
        bluePlayerButton.setFont(new Font("Consolas", Font.BOLD, 15));
        blueComputerButton = new JRadioButton("Blue Computer");
        blueComputerButton.setFont(new Font("Consolas", Font.BOLD, 15));
        var bluePlayerCompOrHumanButton = new ButtonGroup();
        bluePlayerCompOrHumanButton.add(bluePlayerButton);
        bluePlayerCompOrHumanButton.add(blueComputerButton);
        bluePlayerButton.setSelected(true);

        sBlueRadioButton = new JRadioButton("S");
        oBlueRadioButton = new JRadioButton("O");
        sBlueRadioButton.setSelected(true);
        var bluePlayerMoveButtonGroup = new ButtonGroup();
        bluePlayerMoveButtonGroup.add(sBlueRadioButton);
        bluePlayerMoveButtonGroup.add(oBlueRadioButton);

        bluePlayerButton.setBounds(0, 0, 200, 30);
        sBlueRadioButton.setBounds(0, 21, 50, 30);
        oBlueRadioButton.setBounds(0, 42, 50, 30);
        blueComputerButton.setBounds(0,0,200, 30);

        bluePlayerMovePanel.add(bluePlayerButton);
        bluePlayerMovePanel.add(sBlueRadioButton);
        bluePlayerMovePanel.add(oBlueRadioButton);
        bluePlayerMovePanel.add(blueComputerButton);

        redPlayerMovePanel = new JPanel();
        redPlayerMovePanel.setSize(110, 100);

        redPlayerButton = new JRadioButton("Red Player");
        redPlayerButton.setFont(new Font("Consolas", Font.BOLD, 15));
        redComputerButton = new JRadioButton("Red Computer");
        redComputerButton.setFont(new Font("Consolas", Font.BOLD, 15));
        var redPlayerCompOrHumanButton = new ButtonGroup();
        redPlayerCompOrHumanButton.add(redPlayerButton);
        redPlayerCompOrHumanButton.add(redComputerButton);
        redPlayerButton.setSelected(true);

        var redPlayerMoveButtonGroup = new ButtonGroup();
        sRedRadioButton = new JRadioButton("S");
        oRedRadioButton = new JRadioButton("O");
        sRedRadioButton.setSelected(true);
        redPlayerMoveButtonGroup.add(sRedRadioButton);
        redPlayerMoveButtonGroup.add(oRedRadioButton);

        redPlayerButton.setBounds(0, 0, 110, 30);
        sRedRadioButton.setBounds(0, 21, 50, 30);
        oRedRadioButton.setBounds(0, 42, 50, 30);
        blueComputerButton.setBounds(0,0,110, 30);

        redPlayerMovePanel.add(redPlayerButton);
        redPlayerMovePanel.add(sRedRadioButton);
        redPlayerMovePanel.add(oRedRadioButton);
        redPlayerMovePanel.add(redComputerButton);

        bluePlayerMovePanel.setBounds(50, 300, 150, 200);
        redPlayerMovePanel.setBounds(810, 300, 150, 150);
        this.add(bluePlayerMovePanel);
        this.add(redPlayerMovePanel);
    }

    private void setupGameModeButtons() {
        gameModePanel = new JPanel();
        gameModePanel.setSize(500, 30);

        var gameModeLabel = new JLabel("SOS");
        gameModeLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        simpleGameButton = new JRadioButton("Simple Game");
        simpleGameButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        generalGameButton = new JRadioButton("General Game");
        generalGameButton.setFont(new Font("Consolas", Font.PLAIN, 15));

        var gameModeButtonGroup = new ButtonGroup();
        gameModeButtonGroup.add(simpleGameButton);
        gameModeButtonGroup.add(generalGameButton);
        simpleGameButton.setSelected(true);
        gameModeLabel.setBounds(0, 0, 50, 30);
        generalGameButton.setBounds(60, 0, 100, 30);
        simpleGameButton.setBounds(170, 0, 100, 30);
        gameModePanel.add(gameModeLabel);
        gameModePanel.add(simpleGameButton);
        gameModePanel.add(generalGameButton);

        gameModePanel.setBounds(350, 30, 300, 30);
        this.add(gameModePanel);

        gameStatusBar = new JLabel(" ");
        gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
    }

    private void setupBoardSize() {
        //sets limits and constraints on the entry field box
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMaximum(9);
        formatter.setMinimum(3);

        textFieldBoardSize = new JFormattedTextField(formatter);
        labelBoardSize = new JLabel("Board Size (3 - 9)");
        labelBoardSize.setFont(new Font("Consolas", Font.PLAIN, 15));
        buttonSubmitBoardSize = new JButton("Submit");
        buttonSubmitBoardSize.addActionListener(this);
        labelBoardSize.setBounds(770, 40, 200, 15);
        this.add(labelBoardSize);
        textFieldBoardSize.setBounds(900, 30, 35, 35);
        this.add(textFieldBoardSize);
        buttonSubmitBoardSize.setBounds(880, 62, 80, 30);
        this.add(buttonSubmitBoardSize);
        //dummy label is here because of a bug in java swing was making my submit button take up the whole frame. This was the fix.
        var dummy = new JLabel();
        this.add(dummy);
        this.setVisible(true);
    }

    //lock waits here for board size input
    public void setupPlayingBoardGrid() {
        JLabel gameType = new JLabel("  ");
        gameType.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        if(simpleGameButton.isSelected())
            gameType.setText("SIMPLE GAME");
        else if (generalGameButton.isSelected())
            gameType.setText("GENERAL GAME");
        CELL_SIZE = 500 / game.TOTALROWSANDCOLUMNS;
        var boardGameGridPanel = new GUIPanel();
        gameType.setBounds(450, 80, 300, 30);
        boardGameGridPanel.add(gameType);
        bluePlayerMovePanel.setBounds(50, 300, 150, 200);
        boardGameGridPanel.add(bluePlayerMovePanel);
        redPlayerMovePanel.setBounds(810, 300, 150, 150);
        boardGameGridPanel.add(redPlayerMovePanel);
        gameStatusBar.setBounds(420, 650, 300, 30);
        boardGameGridPanel.add(gameStatusBar);
        this.add(boardGameGridPanel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonSubmitBoardSize) {
            try {
                game.TOTALROWSANDCOLUMNS = Integer.parseInt(textFieldBoardSize.getText());
                boardGridLatch.countDown();
            } catch (Exception E) {
                System.out.println("Enter an integer (3 - 9)");
            }
        }
    }

    public class GUIPanel extends JPanel {
        public int boardPanelOriginX = 250;
        public int boardPanelOriginY = 125;

        GUIPanel() {
            this.setSize(1000, 1000);
            setLayout(null);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (game.getGameState() == SOSGame.GameState.PLAYING) {
                        int rowSelected = (e.getY() - boardPanelOriginY) / CELL_SIZE;
                        int colSelected = (e.getX() - boardPanelOriginX) / CELL_SIZE;
                        game.makeMove(rowSelected, colSelected);
                    }

                    else {
                        repaint();
                        game.resetGame();
                    }
                    repaint();
                }
            });
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGridLines(g);
            drawBoard(g);
            printStatusBar();
        }

        private void drawGridLines(Graphics g) {
            for (int i = 0; i <= game.TOTALROWSANDCOLUMNS; i++) {
                g.drawLine(boardPanelOriginX, boardPanelOriginY + i * CELL_SIZE, boardPanelOriginX + game.TOTALROWSANDCOLUMNS * CELL_SIZE, boardPanelOriginY + i * CELL_SIZE);
            }
            for (int i = 0; i <= game.TOTALROWSANDCOLUMNS; i++) {
                g.drawLine(boardPanelOriginX + i * CELL_SIZE, boardPanelOriginY, boardPanelOriginX + i * CELL_SIZE, boardPanelOriginY + game.TOTALROWSANDCOLUMNS * CELL_SIZE);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int oOffset = (CELL_SIZE / 3);
            g2d.setFont((new Font("Consolas", Font.PLAIN, CELL_SIZE)));
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < game.getTotalRowsAndColumns(); ++row) {
                for (int col = 0; col < game.getTotalRowsAndColumns(); ++col) {
                    int x1 = col * CELL_SIZE + boardPanelOriginX;
                    int x2 = row * CELL_SIZE + boardPanelOriginY;
                    if (game.getCell(row, col) == SOSGame.Cell.BLUE_S) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawString("S", (x1 + (CELL_SIZE / 2)) - (CELL_SIZE / 4), (x2 + (CELL_SIZE / 2)) + (CELL_SIZE / 3));
                    } else if (game.getCell(row, col) == SOSGame.Cell.BLUE_O) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawString("O", (x1 + (CELL_SIZE / 2)) - oOffset, (x2 + (CELL_SIZE / 2)) + oOffset);
                    } else if (game.getCell(row, col) == SOSGame.Cell.RED_O) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("O", (x1 + (CELL_SIZE / 2)) - oOffset, (x2 + (CELL_SIZE / 2)) + oOffset);
                    } else if (game.getCell(row, col) == SOSGame.Cell.RED_S) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("S", (x1 + (CELL_SIZE / 2)) - (CELL_SIZE / 4), (x2 + (CELL_SIZE / 2)) + (CELL_SIZE / 3));
                    }
                }
            }
            for(int outerList = 0; outerList < game.completedSOSTokensList.size(); outerList++) {
                ArrayList<Integer> n = new ArrayList<>(5);
                n = (ArrayList<Integer>) game.completedSOSTokensList.get(outerList);
                g2d.setColor((n.get(0) == 1)? Color.BLUE : Color.RED);
                g2d.setStroke(new BasicStroke(CELL_SIZE / 6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(n.get(2) * CELL_SIZE + boardPanelOriginX + (CELL_SIZE / 2), n.get(1) * CELL_SIZE + boardPanelOriginY + (CELL_SIZE / 2),n.get(4)* CELL_SIZE + boardPanelOriginX + (CELL_SIZE / 2),n.get(3) * CELL_SIZE + boardPanelOriginY + (CELL_SIZE / 2));
            }
        }

        private void printStatusBar() {
            if (game.getGameState() == SOSGame.GameState.PLAYING) {
                if (game.getTurn() == "Blue") {
                    gameStatusBar.setText("Blue Players Turn");
                }
                else {
                    gameStatusBar.setText("Red Players Turn");
                }
            }
            else if (game.getGameState() == SOSGame.GameState.DRAW) {
                gameStatusBar.setText("It's a Draw! Click to play again.");
            }
            else if (game.getGameState() == SOSGame.GameState.BLUE_WON) {
                gameStatusBar.setText("Blue Player Won! Click to play again.");
            }
            else if (game.getGameState() == SOSGame.GameState.RED_WON) {
                gameStatusBar.setText("Red Player Won! Click to play again.");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new SOSGUI()
    ;}
}
