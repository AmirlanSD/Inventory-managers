package model.objects;


import controller.LoginPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import model.IOReadWrite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Catalogue {
    protected int catalogueId;
    protected String name;
    protected int userId;
    protected List<Integer> productsId;
    protected List<Double> productsDiscount;
    protected LocalDate dateStart;
    protected LocalDate dateEnd;
    protected String description;
    public static ObservableList<Catalogue> catalogues = FXCollections.observableArrayList();

    public Catalogue(int catalogueId, String name, int userId, List<Integer> productsId,
                     LocalDate dateStart, LocalDate dateEnd, String description) {
        this.catalogueId = catalogueId;
        this.name = name;
        this.userId = userId;
        this.productsId = productsId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public Catalogue(String name, List<Integer> productsId, LocalDate dateStart,
                     LocalDate dateEnd, String description) {
        this.catalogueId = IOReadWrite.getCatalogueId();
        this.name = name;
        this.userId = LoginPage.getInstance().getUserId();
        this.productsId = productsId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserUsername() {
        Predicate<User> userPredicate = user -> user.getUserId() == this.getUserId();
        return User.users.filtered(userPredicate).get(0).username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getProductsName() {
        Predicate<Product> productPredicate = product -> this.getProductsId().contains(product.getProductId());
        FilteredList<Product> products = Product.productList.filtered(productPredicate);
        List<String> productsName = new ArrayList<>();
        for (Product product: products) {
            productsName.add(product.getProductName());
        }
        return productsName;
    }

    public List<Integer> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<Integer> productsId) {
        this.productsId = productsId;
    }

    public List<Double> getProductsDiscount() {
        return productsDiscount;
    }

    public void setProductsDiscount(List<Double> productsDiscount) {
        this.productsDiscount = productsDiscount;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                catalogueId, name, userId,
                productsId.stream().map(Object::toString).collect(Collectors.joining("<>")),
                dateStart, dateEnd, description
        );
    }
}
