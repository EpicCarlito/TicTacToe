package com.epiccarlito.tictactoe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;

public class application extends Application {
    public final String blankIcon = Main.class.getResource("/images/blankIcon.png").toString();
    public final String iconO = Main.class.getResource("/images/iconO.png").toString();
    public final String iconX = Main.class.getResource("/images/iconX.png").toString();
    public String[] board = { "", "", "", "", "", "", "", "", "" };
    public ImageView[] images = new ImageView[9];
    public boolean gameStatus = true;
    public String currentPlayer = "X";
    public Group root = new Group();
    public Label text = new Label("Welcome!");

    @Override
    public void start(Stage stage) throws IOException {
        gameStatus = true;

        Text titleText = new Text(200, 65, "TicTacToe");
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFont(new Font(36));

        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(18));
        text.setLayoutX(235);
        text.setLayoutY(70);

        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(250);
        restartButton.setLayoutY(475);

        root.getChildren().addAll(titleText, text, restartButton);

        for (int i = 0; i < 2; i++) {
            Rectangle row = new Rectangle();
            row.setX(100);
            row.setY(200 + (i * 125));
            row.setWidth(350);
            row.setHeight(25);
            row.setArcWidth(10);
            row.setArcHeight(10);
            root.getChildren().add(row);

            Rectangle column = new Rectangle();
            column.setX(200 + (i * 125));
            column.setY(100);
            column.setWidth(25);
            column.setHeight(350);
            column.setArcWidth(10);
            column.setArcHeight(10);
            root.getChildren().add(column);
        }

        createTiles();

        Scene scene = new Scene(root, 550, 550);
        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();

        images[0].setOnMouseClicked(event -> handleTile(event));
        images[1].setOnMouseClicked(event -> handleTile(event));
        images[2].setOnMouseClicked(event -> handleTile(event));
        images[3].setOnMouseClicked(event -> handleTile(event));
        images[4].setOnMouseClicked(event -> handleTile(event));
        images[5].setOnMouseClicked(event -> handleTile(event));
        images[6].setOnMouseClicked(event -> handleTile(event));
        images[7].setOnMouseClicked(event -> handleTile(event));
        images[8].setOnMouseClicked(event -> handleTile(event));

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < 9; i++) {
                    Image blankImage = new Image(blankIcon);
                    images[i].setImage(blankImage);
                    board[i] = "";
                }
                currentPlayer = "X";
                gameStatus = true;
                text.setText("Welcome!");
                text.setLayoutX(235);
            }
        });
    }

    private void handleTile(MouseEvent event) {
        if (gameStatus) {
            Image imageX = new Image(iconX);
            Image imageO = new Image(iconO);
            ImageView source = (ImageView) event.getSource();
            String[] arrOfStr = source.getId().split("tile");
            int index = Integer.parseInt(arrOfStr[1]);
            if (currentPlayer == "X") {
                if (board[index] != "O") {
                    source.setImage(imageX);
                    board[index] = "X";
                    text.setText("Current Turn: Player 2");
                    text.setLayoutX(195);
                    declareWinner();
                    currentPlayer = "O";
                }
            }
            if (currentPlayer == "O") {
                if (board[index] != "X") {
                    source.setImage(imageO);
                    board[index] = "O";
                    text.setText("Current Turn: Player 1");
                    declareWinner();
                    currentPlayer = "X";
                }
            }
        }
    }

    public void createTiles() {
        for (int i = 0; i < 9; i++) {
            Image image = new Image(blankIcon);
            ImageView tile = new ImageView(image);
            tile.setCache(true);
            tile.setPickOnBounds(true);
            if (i <= 2) {
                tile.setY(100);
                tile.setX(100 + (i * 125));
            }
            if (i <= 5 && i > 2) {
                tile.setY(225);
                tile.setX(100 + ((i - 3) * 125));
            }
            if (i <= 8 && i > 5) {
                tile.setY(350);
                tile.setX(100 + ((i - 6) * 125));
            }
            board[i] = "";
            images[i] = tile;
            tile.setId("tile" + i);
            root.getChildren().add(tile);
        }
    }

    public void declareWinner() {
        for (int i = 0; i < 8; i++) {
            String line = null;

            switch (i) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }

            if (line.equals("XXX")) {
                gameStatus = false;
                text.setText("Player 1 Won!");
                text.setLayoutX(220);
            }
            if (line.equals("OOO")) {
                gameStatus = false;
                text.setText("Player 2 Won!");
                text.setLayoutX(220);
            }
        }
        for (int a = 0; a < 9; a++) {
            if (!(board[a] == "X" || board[a] == "O")) {
                break;
            } else if (a == 8) {
                text.setText("Game is tied!");
                text.setLayoutX(220);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}