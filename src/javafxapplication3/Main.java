package javafxapplication3;

import Model.InitController;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) throws Exception{
        InitController init = new InitController();
        Stage stage = new Stage();
        init.start(stage);
    }
}
