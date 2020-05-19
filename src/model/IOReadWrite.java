package model;

import model.objects.*;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class IOReadWrite {

    public static int productId;
    public static int catalogueId;
    public static int categoryId;
    public static int supplierId;
    public static int userId;


    public static final File idFile = new File("src/data/textFileDatabase/ID.txt");
    public static final File userFile = new File("src/data/textFileDatabase/User.txt");
    public static final File productFile = new File("src/data/textFileDatabase/Product.txt");
    public static final File catalogueFile = new File("src/data/textFileDatabase/Catalogue.txt");
    public static final File categoryFile = new File("src/data/textFileDatabase/Category.txt");
    public static final File supplierFile = new File("src/data/textFileDatabase/Supplier.txt");
    public static final File loginLog = new File("src/data/textFileDatabase/Login.log");


    public static void onStartup() throws IOException, ParseException {
        // READID
        BufferedReader reader = new BufferedReader(new FileReader(idFile));
        productId = Integer.parseInt(reader.readLine());
        catalogueId = Integer.parseInt(reader.readLine());
        categoryId = Integer.parseInt(reader.readLine());
        supplierId = Integer.parseInt(reader.readLine());
        userId = Integer.parseInt(reader.readLine());

        String lineString;

        // READEVERYTHING ELSE
        reader = new BufferedReader(new FileReader(loginLog));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Log.loginLogs.add(new Log(logArray[0], logArray[1], logArray[2]));
        }

        reader = new BufferedReader(new FileReader(productFile));
        while ((lineString = reader.readLine()) != null) {
            String[] productArray = lineString.split("\\|");
            Product.productList.add(new Product(Integer.parseInt(productArray[0]), productArray[1], Integer.parseInt(productArray[2]),
                    Integer.parseInt(productArray[3]), Integer.parseInt(productArray[4]),
                    Double.parseDouble(productArray[5])));
        }

        reader = new BufferedReader(new FileReader(catalogueFile));
        while ((lineString = reader.readLine()) != null) {
            String[] catalogueArray = lineString.split("\\|");

            List<Integer> productId = new ArrayList<>();
            for (String product: catalogueArray[3].split("<>")) {
                productId.add(Integer.parseInt(product));
            }

            Catalogue.catalogues.add(new Catalogue(Integer.parseInt(catalogueArray[0]), catalogueArray[1],
                    Integer.parseInt(catalogueArray[2]), productId, LocalDate.parse(catalogueArray[4]),
                    LocalDate.parse(catalogueArray[5]), catalogueArray[6]));
        }

        reader = new BufferedReader(new FileReader(categoryFile));
        while ((lineString = reader.readLine()) != null) {
            String[] categoryArray = lineString.split("\\|");
            Category.categories.add(new Category(Integer.parseInt(categoryArray[0]), categoryArray[1]));
        }

        reader = new BufferedReader(new FileReader(supplierFile));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Supplier.suppliers.add(new Supplier(Integer.parseInt(logArray[0]), logArray[1], logArray[2],
                    logArray[3], logArray[4], logArray[5], logArray[6], logArray[7],
                    logArray[8], Boolean.valueOf(logArray[9])));
        }

        reader = new BufferedReader(new FileReader(userFile));
        while ((lineString = reader.readLine()) != null) {
            String[] userArray = lineString.split("\\|");
            if (userArray[3].equals("Product Manager")) {
                User.users.add(new ProductManager(Integer.parseInt(userArray[0]), userArray[1], userArray[2],
                        userArray[4], userArray[5], userArray[6], userArray[7], Boolean.valueOf(userArray[8])));
            } else {
                User.users.add(new Administrator(Integer.parseInt(userArray[0]), userArray[1], userArray[2],
                        userArray[4], userArray[5], userArray[6], userArray[7], Boolean.valueOf(userArray[8])));
            }
        }
    }

    public static void toSave() throws IOException {
        // READ/WRITE
        BufferedWriter writer = new BufferedWriter(new FileWriter(idFile));
        writer.write(String.valueOf(productId));
        writer.newLine();
        writer.write(String.valueOf(catalogueId));
        writer.newLine();
        writer.write(String.valueOf(categoryId));
        writer.newLine();
        writer.write(String.valueOf(supplierId));
        writer.newLine();
        writer.write(String.valueOf(userId));
        writer.newLine();
        writer.flush();

        // READ/WRITE
        writer = new BufferedWriter(new FileWriter(loginLog));
        for (Log log: Log.loginLogs) {
            writer.write(log.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(productFile));
        for (Product product: Product.productList) {
            writer.write(product.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(catalogueFile));
        for (Catalogue catalogue: Catalogue.catalogues) {
            writer.write(catalogue.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(categoryFile));
        for (Category category: Category.categories) {
            writer.write(category.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(supplierFile));
        for (Supplier supplier: Supplier.suppliers) {
            writer.write(supplier.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(userFile));
        for (User user: User.users) {
            writer.write(user.toString());
            writer.newLine();
        }
        writer.flush();
    }

    public static int getProductId() {
        productId++;
        return productId;
    }

    public static int getCatalogueId() {
        catalogueId++;
        return catalogueId;
    }

    public static int getCategoryId() {
        categoryId++;
        return categoryId;
    }

    public static int getSupplierId() {
        supplierId++;
        return supplierId;
    }

    public static int getUserId() {
        userId++;
        return userId;
    }
}