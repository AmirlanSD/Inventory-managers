package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.IOReadWrite;

import java.io.*;
import java.text.ParseException;

public class Main extends Application {
    @Override
    public void start(Stage loginStage) throws Exception{

        Parent loginFXML = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        loginStage.setTitle("angyeOng | Product Catalogue System");
        loginStage.setScene(new Scene(loginFXML));
        loginStage.setWidth(960);
        loginStage.setHeight(540);
        loginStage.setResizable(false);
        loginStage.show();
    }


    public static void main(String[] args) throws IOException, ParseException {
        IOReadWrite.onStartup();
        launch(args);
    }
}
