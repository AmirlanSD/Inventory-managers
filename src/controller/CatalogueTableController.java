package controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.StringConverter;
import model.DuplicatePrevent;
import model.IOReadWrite;
import model.objects.*;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;





public class CatalogueTableController implements Initializable {

    @FXML TableView<Catalogue> catalogueTableView;
    @FXML TableColumn catalogueIdTableColumn;
    @FXML TableColumn nameCatalogueTableColumn;
    @FXML TableColumn userUserIdTableColumn;
    @FXML TableColumn productsTableColumn;
    @FXML TableColumn startDateTableColumn;
    @FXML TableColumn endDateTableColumn;
    @FXML TableColumn descriptionTableColumn;

    @FXML TextField addCatalogueName;
    @FXML ComboBox<Product> addCatalogueProducts;
    @FXML DatePicker addCatalogueStartingDate;
    @FXML DatePicker addCatalogueEndingDate;
    @FXML TextArea addCatalogueDescription;

    @FXML TableView<Product> addCatalogueSelectedProductsTableView;
    @FXML TableColumn addCatalogueSelectedProductsNameTableColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //ADD DATA TO TABLEVIEW
        catalogueIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("catalogueId"));
        nameCatalogueTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userUserIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userUsername"));
        productsTableColumn.setCellValueFactory(new PropertyValueFactory<>("productsName"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        endDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        catalogueTableView.setItems(Catalogue.catalogues);

        //ADD DATA TO COMBOBOX
        addCatalogueProducts.setItems(Product.productList);
        addCatalogueProducts.setConverter(new StringConverter<>() {
            @Override
            public String toString(Product product) {
                try {
                    return product.getProductName();
                } catch(Exception exception){
                    return "";
                }
            }

            @Override
            public Product fromString(String name) {
                return addCatalogueProducts.getItems().stream().filter(product ->
                        product.getProductName().equals(name)).findFirst().orElse(null);
            }
        });

        // PASS NAMES
        addCatalogueSelectedProductsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void addCatalogueSelectedProductsAddButton_OnAction() {
        try {
            Product selectedProduct = addCatalogueProducts.getSelectionModel().getSelectedItem();
            if (addCatalogueSelectedProductsTableView.getItems().contains(selectedProduct)) {
                throw new DuplicatePrevent();
            }
            addCatalogueSelectedProductsTableView.getItems().add(selectedProduct);

        } catch (DuplicatePrevent exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Already selected");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void addCatalogueSubmitButton_OnAction () {
        List<Integer> nameData = new ArrayList<>();
        for (Product product : addCatalogueSelectedProductsTableView.getItems()) {
            nameData.add(product.getProductId());
        }

        Catalogue newCatalogue = new Catalogue(addCatalogueName.getText(), nameData,
                addCatalogueStartingDate.getValue(), addCatalogueEndingDate.getValue(), addCatalogueDescription.getText()
        );
        Catalogue.catalogues.add(newCatalogue);
        refreshTableView();
    }
    public void deleteCatalogueButton_OnAction() {
        try {
            Catalogue selectedCatalogue = catalogueTableView.getSelectionModel().getSelectedItem();
            if (selectedCatalogue == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopup = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the catalogue?",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopup.showAndWait();

            if (confirmationPopup.getResult() == ButtonType.YES) {
                Catalogue.catalogues.remove(selectedCatalogue);
            }
            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Catalogue must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void saveCatalogue_OnAction () throws IOException{
        IOReadWrite.toSave();
    }

    private void refreshTableView() {
        catalogueTableView.getColumns().get(0).setVisible(false);
        catalogueTableView.getColumns().get(0).setVisible(true);
    }
}
