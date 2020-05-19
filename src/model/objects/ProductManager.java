package model.objects;

import model.IOReadWrite;

public class ProductManager extends User {

    public ProductManager(int userId, String username, String password, String name, String address,
                           String contactNumber, String emailAddress, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = "Product Manager";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = status;
    }

    public ProductManager(String username, String password, String name, String address,
                           String contactNumber, String emailAddress) {
        this.userId = IOReadWrite.getUserId();
        this.username = username;
        this.password = password;
        this.role = "Product Manager";
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.status = true;
    }


}
