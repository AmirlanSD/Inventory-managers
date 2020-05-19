package controller;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.util.StringConverter;
import model.IOReadWrite;
import model.objects.Category;
import model.objects.Product;

import model.objects.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdministratorPage implements Initializable{

    @FXML TableView<Product> productTableView;
    @FXML TableColumn productIdTableColumn;
    @FXML TableColumn productNameTableColumn;
    @FXML TableColumn categoryIdTableColumn;
    @FXML TableColumn supplierIdTableColumn;
    @FXML TableColumn quantityTableColumn;
    @FXML TableColumn priceTableColumn;

    @FXML TextField addProductProductName;
    @FXML ComboBox<Category> addProductCategoryId;
    @FXML ComboBox<Supplier> addProductSupplierId;
    @FXML TextField addProductQuantity;
    @FXML TextField addProductPrice;
    @FXML Button addProductClearButton;

    @FXML TableView<Category> categoryTableView;
    @FXML TableColumn idCategoryTableColumn;
    @FXML TableColumn nameCategoryTableColumn;
    @FXML TextField categoryNameTextField;



    @FXML ComboBox searchComboBox;
    @FXML TextField searchTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        supplierIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.setItems(Product.productList);


        // Configure category table columns and items
        idCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        nameCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryTableView.setItems(Category.categories);

        // Populate combo box and set default value
        searchComboBox.getItems().addAll("Product ID", "Product Name", "Category Name", "Supplier Name",
                "Quantity", "Price");
        searchComboBox.setValue(null);

        // Initialize categories in add and edit product combobox
        addProductCategoryId.setItems(Category.categories);
        addProductCategoryId.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                try {
                    return category.getCategoryName();
                } catch (Exception exception) {
                    return "";
                }
            }

            @Override
            public Category fromString(String categoryName) {
                return addProductCategoryId.getItems().stream().filter(category ->
                        category.getCategoryName().equals(categoryName)).findFirst().orElse(null);
            }
        });

        // Initialize suppliers in add and edit product combobox
        addProductSupplierId.setItems(Supplier.suppliers);
        addProductSupplierId.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                try {
                    return supplier.getName();
                } catch (Exception exception) {
                    return "";
                }
            }

            @Override
            public Supplier fromString(String supplierName) {
                return addProductSupplierId.getItems().stream().filter(supplier ->
                        supplier.getName().equals(supplierName)).findFirst().orElse(null);
            }
        });
    }

    public void addProductAddButton_OnAction () throws IOException {
        Product newProduct = new Product(addProductProductName.getText(), Integer.parseInt(addProductCategoryId.getValue().toString().split("\\|")[0]),
                Integer.parseInt(addProductSupplierId.getValue().toString().split("\\|")[0]), Integer.parseInt(addProductQuantity.getText()),
                Double.parseDouble(addProductPrice.getText()));
        Product.productList.add(newProduct);
        addProductClearButton.fire();
        refreshTableView();
    }

    private void refreshCategoryTableView() {
        categoryTableView.refresh();
    }

    private void refreshTableView() {
        productTableView.getColumns().get(0).setVisible(false);
        productTableView.getColumns().get(0).setVisible(true);
    }

    public void addProductClearButton_OnAction () {
        addProductProductName.setText(null);
        addProductCategoryId.setValue(null);
        addProductSupplierId.setValue(null);
        addProductQuantity.setText(null);
        addProductPrice.setText(null);
    }

    public void deleteProductButton_OnAction() {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new NullPointerException();
            }

            Alert confirmationPopUp = new Alert(Alert.AlertType.WARNING, "Selected product will be deleted.",
                    ButtonType.YES, ButtonType.NO);
            confirmationPopUp.showAndWait();

            if (confirmationPopUp.getResult() == ButtonType.YES) {
                Product.productList.remove(selectedProduct);
            }

            refreshTableView();
        } catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Please select a product item.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void updateModeProductButton_OnAction() {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new NullPointerException();
            }

            addProductProductName.setText(selectedProduct.getProductName());
            Predicate<Category> categoryPredicate = category -> category.getCategoryId() == selectedProduct.getCategoryId();
            addProductCategoryId.setValue(Category.categories.filtered(categoryPredicate).get(0));
            Predicate<Supplier> supplierPredicate = supplier -> supplier.getSupplierId() == selectedProduct.getSupplierId();
            addProductSupplierId.setValue(Supplier.suppliers.filtered(supplierPredicate).get(0));
            addProductQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
            addProductPrice.setText(String.valueOf(selectedProduct.getPrice()));

        }  catch (NullPointerException exception) {
            Dialog dialog = new Dialog();
            dialog.setContentText("Product must be selected.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        }
    }

    public void updateProductButton_OnAction () {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        selectedProduct.setProductName(addProductProductName.getText());
        selectedProduct.setCategoryId(Integer.parseInt(addProductCategoryId.getValue().toString().split("\\|")[0]));
        selectedProduct.setSupplierId(Integer.parseInt(addProductSupplierId.getValue().toString().split("\\|")[0]));
        selectedProduct.setQuantity(Integer.parseInt(addProductQuantity.getText()));
        selectedProduct.setPrice(Double.parseDouble(addProductPrice.getText()));
        refreshTableView();
    }

    //CATEGORY MANAGEMENT
    public void categoriesCategoryAddButton_OnAction() throws IOException {
        Category.categories.add(new Category(categoryNameTextField.getText()));
        refreshCategoryTableView();
    }

    public void categoriesCategoryDeleteButton_OnAction() throws IOException {
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
        Category.categories.remove(selectedCategory);
        refreshCategoryTableView();
    }

    public void categoriesCategoryUpdateMode_OnAction() {
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
        categoryNameTextField.setText(selectedCategory.getCategoryName());
    }

    public void categoriesCategoryUpdate_OnAction() {
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
        selectedCategory.setCategoryName(categoryNameTextField.getText());
        refreshCategoryTableView();
    }


    public void manageProfilesButton_OnAction () throws IOException {
        Stage usersStage = new Stage();
        usersStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UsersManagement.fxml"))));
        usersStage.setWidth(960);
        usersStage.setHeight(540);
        usersStage.setResizable(false);
        usersStage.setTitle("angyeOng | Product Catalogue System | Product Manager Page | Profiles Management");
        usersStage.show();
    }

    public void logOutButton_OnAction(Event event) throws IOException{
        Stage catalogueStage = new Stage();
        catalogueStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"))));
        catalogueStage.setWidth(960);
        catalogueStage.setHeight(540);
        catalogueStage.setResizable(false);
        catalogueStage.setTitle("angyeOng | Product Catalogue System");
        catalogueStage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void searchTextField_OnChange () {
        Predicate<Product> searchComboBoxValue = null;
        switch (searchComboBox.getValue().toString()) {
            case "Product ID":
                searchComboBoxValue = product -> String.valueOf(product.getProductId()).contains(searchTextField.getText());
                break;
            case "Product Name":
                searchComboBoxValue = product -> product.getProductName().contains(searchTextField.getText());
                break;
            case "Category Name":
                searchComboBoxValue = product -> String.valueOf(product.getCategoryId()).contains(searchTextField.getText());
                break;
            case "Supplier Name":
                searchComboBoxValue = product -> String.valueOf(product.getSupplierId()).contains(searchTextField.getText());
                break;
            case "Quantity":
                searchComboBoxValue = product -> String.valueOf(product.getQuantity()).contains(searchTextField.getText());
                break;
            case "Price":
                searchComboBoxValue = product -> String.valueOf(product.getPrice()).contains(searchTextField.getText());
                break;
        }
        productTableView.setItems(Product.productList.filtered(searchComboBoxValue));
    }

    public void saveChangesButton_onAction () throws IOException {
        IOReadWrite.toSave();
    }
}
