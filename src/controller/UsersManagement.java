package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IOReadWrite;
import model.objects.Administrator;
import model.objects.ProductManager;
import model.objects.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersManagement implements Initializable {

    @FXML
    TableView<User> usersTableView;
    @FXML TableColumn usersIdTableColumn;
    @FXML TableColumn usersUNameTableColumn;
    @FXML TableColumn usersRoleTableColumn;
    @FXML TableColumn usersNameTableColumn;
    @FXML TableColumn usersAddressTableColumn;
    @FXML TableColumn usersContactNumberTableColumn;
    @FXML TableColumn usersEmailAddressTableColumn;
    @FXML TableColumn usersStatusTableColumn;


    @FXML TextField addUsersUsername;
    @FXML TextField addUsersPassword;
    @FXML ComboBox addUsersRole;
    @FXML TextField addUsersName;
    @FXML TextField addUsersAddress;
    @FXML TextField addUsersContactNumber;
    @FXML TextField addUsersEmailAddress;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //COMBOBOX DATA POPULATION
        addUsersRole.getItems().addAll("Product Manager", "Administrator");
        addUsersRole.setValue("Product Manager");

        //TABLEVIEW DATA POPULATION
        usersIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usersUNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usersRoleTableColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usersNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        usersContactNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        usersEmailAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        usersStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));
        usersTableView.setItems(User.users);
    }

    public void addUsersButton_OnAction () throws IOException {
        if (addUsersRole.getValue().toString().equals("Product Manager")) {
            User newUser = new ProductManager (addUsersUsername.getText(), addUsersPassword.getText(),
                    addUsersName.getText(), addUsersAddress.getText(),
                    addUsersContactNumber.getText(), usersEmailAddressTableColumn.getText());
            User.users.add(newUser);
        } else if (addUsersRole.getValue().toString().equals("Administrator")) {
            User newUser = new Administrator (addUsersUsername.getText(), addUsersPassword.getText(),
                    addUsersName.getText(), addUsersAddress.getText(),
                    addUsersContactNumber.getText(), addUsersEmailAddress.getText());
            User.users.add(newUser);
        }
        refreshTableView();
    }

    public void deleteUsersButton_OnAction(Event event) {
        try {
            User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the user permanently?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                User.users.remove(selectedUser);
            }
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("User must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }
    public void addUsersSaveButton_OnAction() throws IOException{
        IOReadWrite.toSave();
    }


    private void refreshTableView() {
        usersTableView.getColumns().get(0).setVisible(false);
        usersTableView.getColumns().get(0).setVisible(true);
    }

}
