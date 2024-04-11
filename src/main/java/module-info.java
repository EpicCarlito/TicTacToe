module com.epiccarlito.tictactoe {
    requires javafx.controls;
    requires jdk.xml.dom;

    opens com.epiccarlito.tictactoe;
    exports com.epiccarlito.tictactoe;
}