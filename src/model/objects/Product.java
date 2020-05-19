package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.function.Predicate;
import model.IOReadWrite;

public class Product {

    protected int productId;

    protected int categoryId;
    protected int supplierId;
    protected int quantity;
    protected double price;
    protected String productName;
    protected String categoryName;
    protected String supplierName;


    public static ObservableList<Product> productList = FXCollections.observableArrayList(); //POPULATE TABLE


    public Product(int productId, String productName, int categoryId, int supplierId, int quantity,
                   double price) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }



    public Product(String productName, int categoryId, int supplierId, int quantity,
                   double price) {
        this.productId = IOReadWrite.getProductId();
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }


    //TIME FOR GET AND SET BOIIIIII

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        Predicate<Category> categoryPredicate = category -> category.getCategoryId() == this.getCategoryId();
        return Category.categories.filtered(categoryPredicate).get(0).categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSupplierName() {
        Predicate<Supplier> supplierPredicate = supplier -> supplier.getSupplierId() == this.getSupplierId();
        return Supplier.suppliers.filtered(supplierPredicate).get(0).name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double sellingPrice) {
        this.price = price;
    }




    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|",
                productId, productName, categoryId, supplierId,
                quantity, price);
    }



}
