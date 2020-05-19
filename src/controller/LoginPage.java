package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.Event;
import model.objects.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import model.Authentication;


import java.io.IOException;


public class LoginPage {
    @FXML TextField userTextField;
    @FXML TextField passwordTextField;

    private static User instance;

    public static User getInstance() {
        return instance;
    }


    @FXML
    public void signInButton_OnAction(Event event) throws IOException {
        instance = Authentication.login(userTextField.getText(), passwordTextField.getText());
        try {
            if (LoginPage.getInstance().getClass() == Administrator.class) {
                Stage administratorStage = new Stage();
                administratorStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/administratorPage.fxml"))));
                administratorStage.setWidth(1440);
                administratorStage.setHeight(810);
                administratorStage.setResizable(false);
                administratorStage.setTitle("angyeOng | Product Catalogue System | Administrator Page");
                administratorStage.show();
                Log.loginLogs.add(new Log("Logged in"));
                ((Node) event.getSource()).getScene().getWindow().hide();
        }   else if (LoginPage.getInstance().getClass() == ProductManager.class) {
                Stage productManagerStage = new Stage();
                productManagerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/productManagerPage.fxml"))));
                productManagerStage.setWidth(1440);
                productManagerStage.setHeight(810);
                productManagerStage.setResizable(false);
                productManagerStage.setTitle("angyeOng | Product Catalogue System | Product Manager Page");
                productManagerStage.show();
                Log.loginLogs.add(new Log("Logged in"));
                ((Node) event.getSource()).getScene().getWindow().hide();
        }
        } catch (Exception exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Try again");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }
}
