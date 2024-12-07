package com.example.inventorymanagementsystem;

import com.example.inventorymanagementsystem.data.CustomerData;
import com.example.inventorymanagementsystem.data.ListData;
import com.example.inventorymanagementsystem.data.ProductData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static com.example.inventorymanagementsystem.data.ListData.*;

/**
 * DashboardController нь dashboard UI болон backend хоорондын харилцан үйлчлэлийг удирдана.
 * <p>
 * Энэ нь статистик мэдээллийг харуулах, бүтээгдэхүүнтэй харьцах, захиалгыг удирдах функцуудыг агуулдаг.
 **/

public class DashboardController implements Initializable {
    @FXML
    private TextField addProducts_brand;

    @FXML
    private Button addProducts_btn;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_brand;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_price;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_productId;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_productName;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_status;

    @FXML
    private TableColumn<ProductData, String> addProducts_col_type;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private ImageView addProducts_imageView;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productId;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private ComboBox<String> addProducts_productType;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<String> addProducts_status;

    @FXML
    private TableView<ProductData> addProducts_tableView;

    @FXML
    private Label home_availableProducts;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AreaChart<?, ?> home_incomeChart;

    @FXML
    private Label home_numberOrder;

    @FXML
    private BarChart<?, ?> home_orderChart;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField orders_amount;

    @FXML
    private Label orders_balance;

    @FXML
    private ComboBox<String> orders_brand;

    @FXML
    private Button orders_btn;

    @FXML
    private TableColumn<CustomerData, String> orders_col_brand;

    @FXML
    private TableColumn<CustomerData, String> orders_col_price;

    @FXML
    private TableColumn<CustomerData, String> orders_col_productName;

    @FXML
    private TableColumn<CustomerData, String> orders_col_quantity;

    @FXML
    private TableColumn<CustomerData, String> orders_col_type;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private ComboBox<String> orders_productName;

    @FXML
    private ComboBox<String> orders_productType;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private TableView<CustomerData> orders_tableView;

    @FXML
    private Label orders_total;

    @FXML
    private Label username;

    // Database-тэй холбоотой хэрэглүүрүүд
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    private static final String DB_TABLE_CUSTOMER = "customer";
    private static final String DB_TABLE_CUST_RECEIPT = "customer_receipt";
    private static final String DB_TABLE_PRODUCT = "product";

    AlertMessage alert = new AlertMessage();

    /**
     * SQL date авах функц.
     */
    public java.sql.Date getSqlDate() {
        Date date = new Date();
        return new java.sql.Date(date.getTime());
    }

    /**
     * Нүүр хэсэгт нийт захиалгын тоог харуулах функц.
     */
    public void homeDisplayTotalOrders() {
        String count = "SELECT COUNT(id) FROM " + DB_TABLE_CUSTOMER + " WHERE date = '" + getSqlDate() + "'";
        connect = Database.connectDB();

        int countOrders = 0;

        try {
            assert connect != null;
            prepare = connect.prepareStatement(count);
            result = prepare.executeQuery();

            while (result.next()) {
                countOrders = result.getInt("COUNT(id)");
            }
            // Нийт захиалгын тоог харуулахын тулд label-ийг шинэчилнэ.
            home_numberOrder.setText(String.valueOf(countOrders));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Нүүр хэсэгт нийт орлогын мэдээллийг харуулах функц.
     */
    public void homeTotalIncome() {
        String selectData = "SELECT SUM(total) FROM " + DB_TABLE_CUST_RECEIPT + " ";
        connect = Database.connectDB();

        double totalIncome = 0;

        try {
            assert connect != null;
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                totalIncome = result.getDouble("SUM(total)");
            }
            // Нийт орлогын label-ийг тооцоолсон утгаар шинэчилнэ.
            home_totalIncome.setText(totalIncome + "₮");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Боломжтой бүтээгдэхүүний нийт тоог харуулна.
     **/
    public void homeAvailableProducts() {
        String sql = "SELECT COUNT(id) FROM " + DB_TABLE_PRODUCT + " WHERE status = 'Боломжтой'";
        connect = Database.connectDB();

        int countAP = 0;

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countAP = result.getInt("COUNT(id)");
            }
            // Боломжтой бүтээгдэхүүний тоог харуулахын тулд label-ийг шинэчилнэ.
            home_availableProducts.setText(String.valueOf(countAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Өдөр тутмын орлогын trend-ийг график дээр харуулна.
     */
    public void homeIncomeChart() {
        home_incomeChart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM " + DB_TABLE_CUST_RECEIPT + " GROUP BY TIMESTAMP(date) ASC LIMIT 6";
        home_incomeChart.getData().add(updateChart(sql));
    }

    /**
     * Захиалгын trend-ийг огноогоор харуулна.
     */
    public void homeOrdersChart() {
        home_orderChart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM " + DB_TABLE_CUSTOMER + " GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";
        home_orderChart.getData().add(updateChart(sql));
    }

    /**
     * Графикийг SQL query дэх өгөгдлийг ашиглан шинэчлэнэ.
     *
     * @param query Өгөгдлийг авах SQL query.
     */
    public XYChart.Series updateChart(String query) {
        connect = Database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chart;
    }

    /**
     * Талбарууд нь хоосон эсэхийг шалгах функц.
     */
    private boolean isInputValidOnProduct() {
        return addProducts_productId.getText().isEmpty() ||
                addProducts_productType.getSelectionModel().getSelectedItem() == null ||
                addProducts_brand.getText().isEmpty() ||
                addProducts_productName.getText().isEmpty() ||
                addProducts_price.getText().isEmpty() ||
                addProducts_status.getSelectionModel().getSelectedItem() == null ||
                ListData.path == null || ListData.path.isEmpty(); // ""
    }

    /**
     * Database-д бараа нэмэх функц.
     */
    public void addProductsAdd() {
        String sql = "INSERT INTO " + DB_TABLE_PRODUCT + " (product_id, type, brand, product_name, price, status, image, date) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            // Аль нэг талбар нь хоосон бол алдаа өгнө
            if (isInputValidOnProduct()) {
                alert.errorMessage("Хоосон талбаруудыг бөглөнө үү!");
            }

            // Алдаагүй үед тухайн ID-тай бараа аль хэдийн байгаа эсэхийг шалгана
            String checkData = "SELECT product_id FROM " + DB_TABLE_PRODUCT + " WHERE product_id = '" + addProducts_productId.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            // Хэрэв байвал
            if (result.next()) {
                alert.errorMessage(addProducts_productId.getText() + " дугаартай бараа аль хэдийн нэмэгдсэн байна!");
            } else {
                // Байхгүй бол
                assert connect != null;
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addProducts_productId.getText());
                prepare.setString(2, addProducts_productType.getSelectionModel().getSelectedItem());
                prepare.setString(3, addProducts_brand.getText());
                prepare.setString(4, addProducts_productName.getText());
                prepare.setString(5, addProducts_price.getText());
                prepare.setString(6, addProducts_status.getSelectionModel().getSelectedItem());

                String uri = ListData.path;
                uri = uri.replace("\\", "\\\\");
                prepare.setString(7, uri);

                prepare.setString(8, String.valueOf(getSqlDate()));

                prepare.executeUpdate(); // өөрчлөлтийг db-д оруулна
                addProductsShowListData(); // хүснэгтийг шинэчлэнэ
                addProductsReset(); // талбаруудыг цэвэрлэнэ
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Одоо байгаа бүтээгдэхүүнийг шинэчлэх функц.
     */
    public void addProductsUpdate() {
        String uri = ListData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE " + DB_TABLE_PRODUCT + " SET " +
                "type = '" + addProducts_productType.getSelectionModel().getSelectedItem()
                + "', brand = '" + addProducts_brand.getText()
                + "', product_name = '" + addProducts_productName.getText()
                + "', price = '" + addProducts_price.getText()
                + "', status = '" + addProducts_status.getSelectionModel().getSelectedItem()
                + "', image = '" + uri + "', date = '" + getSqlDate() + "' WHERE product_id = '"
                + addProducts_productId.getText() + "'";
        connect = Database.connectDB();

        try {
            if (isInputValidOnProduct()) {
                alert.errorMessage("Хоосон талбаруудыг бөглөнө үү!");
            }

            if (alert.confirmMessage("Та " + addProducts_productId.getText() + " дугаартай барааг шинэчлэхдээ итгэлтэй байна уу?")) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert.successMessage("Амжилттай шинэчлэгдлээ.");

                addProductsShowListData();
                addProductsReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Өгөгдлийн сангаас бүтээгдэхүүнийг устгах функц.
     */
    public void addProductsDelete() {
        String sql = "DELETE FROM " + DB_TABLE_PRODUCT + " WHERE product_id = '" + addProducts_productId.getText() + "'";
        connect = Database.connectDB();

        try {
            if (isInputValidOnProduct()) {
                alert.errorMessage("Хоосон талбаруудыг бөглөнө үү!");
            }

            if (alert.confirmMessage("Та " + addProducts_productId.getText() + " дугаартай барааг устгахдаа итгэлтэй байна уу?")) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert.successMessage("Амжилттай устгалаа.");

                addProductsShowListData();
                addProductsReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Формыг цэвэрлэх функц.
     */
    public void addProductsReset() {
        addProducts_productId.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_price.setText("");
        addProducts_status.getSelectionModel().clearSelection();

        ListData.path = "";
        addProducts_imageView.setImage(null);
    }

    /**
     * Зураг оруулж ирэх функц.
     */
    public void addProductsImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Барааны зураг сонгох");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png", "*jpeg"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            ListData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 111, 136, false, true);
            addProducts_imageView.setImage(image);
        }
    }

    public void addProductsListType() {
        addProducts_productType.setItems(addDataToList(ListData.listType));
    }

    public void addProductsListStatus() {
        addProducts_status.setItems(addDataToList(ListData.listStatus));
    }

    /**
     * ComboBox-д нэмэх өгөгдлийг бэлтгэх функц.
     *
     * @param listItems нэмэх өгөгдлүүд
     */
    public ObservableList addDataToList(String[] listItems) {
        List<String> list = new ArrayList<>(Arrays.asList(listItems));
        return FXCollections.observableArrayList(list);
    }

    /**
     * Бараа хайх
     */
    public void addProductsSearch() {
        FilteredList<ProductData> filteredList = new FilteredList<>(ListData.addProductsList, e -> true);

        // search field listener
        addProducts_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            String searchKey = (newValue == null || newValue.isEmpty()) ? "" : newValue.toLowerCase();

            filteredList.setPredicate(product -> {
                if (searchKey.isEmpty()) {
                    return true; // Хоосон бол бүгдийг харуулна
                }
                return matchesSearch(product, searchKey);
            });
        });

        SortedList<ProductData> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
        addProducts_tableView.setItems(sortedList);
    }

    /**
     * Хайсан бүтээгдэхүүн байгаа эсэхийг шалгах функц.
     *
     * @param product   Хайх бараанууд
     * @param searchKey Хайх түлхүүр
     * @return хайсан бараа байвал true буцаана
     */
    private boolean matchesSearch(ProductData product, String searchKey) {
        return (product.getProductId().toString().contains(searchKey)
                || product.getType().toLowerCase().contains(searchKey)
                || product.getBrand().toLowerCase().contains(searchKey)
                || product.getProductName().toLowerCase().contains(searchKey)
                || product.getPrice().toString().contains(searchKey)
                || product.getStatus().toLowerCase().contains(searchKey));
    }

    /**
     * Бараа хүснэгтээс мэдээллүүдийг авч харгалзах загвар өгөгдөлд олгох
     */
    public ObservableList<ProductData> addProductsListData() {
        ObservableList<ProductData> productList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM " + DB_TABLE_PRODUCT + " ";
        connect = Database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prodD;

            while (result.next()) {
                prodD = new ProductData(result.getInt("product_id"),
                        result.getString("type"),
                        result.getString("brand"),
                        result.getString("product_name"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                productList.add(prodD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * Барааны мэдээллээр хүснэгтийн багануудыг дүүргэх
     */
    public void addProductsShowListData() {
        ListData.addProductsList = addProductsListData();

        addProducts_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProducts_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addProducts_tableView.setItems(ListData.addProductsList);
    }

    /**
     * Хүснэгтэн дэх барааны мэдээлэл дээр дарахад дэлгэрэнгүйг харуулах функц.
     */
    public void addProductsSelect() {
        ProductData prodD = addProducts_tableView.getSelectionModel().getSelectedItem();
        int num = addProducts_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) return;

        addProducts_productId.setText(String.valueOf(prodD.getProductId()));
        addProducts_productType.getSelectionModel().select(prodD.getType());
        addProducts_brand.setText(prodD.getBrand());
        addProducts_productName.setText(prodD.getProductName());
        addProducts_status.getSelectionModel().select(prodD.getStatus());
        addProducts_price.setText(String.valueOf(prodD.getPrice()));

        String uri = "file: " + prodD.getImage();
        image = new Image(uri, 111, 136, false, true);
        addProducts_imageView.setImage(image);
        ListData.path = prodD.getImage();
    }

    /**
     * Өгөгдлийн санд шинэ хэрэглэгчийн захиалгыг нэмэх.
     */
    public void ordersAdd() {
        customerId();

        String sql = "INSERT INTO " + DB_TABLE_CUSTOMER + " (customer_id, type, brand, product_name, quantity, price, date)"
                + "VALUES(?,?,?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            String checkData = "SELECT * FROM " + DB_TABLE_PRODUCT + " WHERE product_name = '"
                    + orders_productName.getSelectionModel().getSelectedItem() + "'";

            double priceData = 0;

            statement = connect.createStatement();
            // Бүтээгдэхүүний нэрээр бүтээгдэхүүний дэлгэрэнгүй мэдээллийг авна.
            result = statement.executeQuery(checkData);

            if (result.next()) {
                // Бүтээгдэхүүний үнийг fetch хийнэ.
                priceData = result.getDouble("price");
            }

            // Тоо хэмжээнээс хамаарч нийт үнийг тооцоолно.
            double totalPData = (priceData * qty);

            // Хэрэглэгчийн оруулсан мэдээллийг баталгаажуулж, бүрэн бус захиалга хийхээс сэргийлнэ.
            if (orders_productType.getSelectionModel().getSelectedItem() == null
                    || orders_brand.getSelectionModel().getSelectedItem() == null
                    || orders_productName.getSelectionModel().getSelectedItem() == null
                    || totalPData == 0) {
                alert.errorMessage("Та эхлээд бараагаа сонгоно уу.");
            } else {
                prepare = connect.prepareStatement(sql);
                // SQL параметрийн утгыг тохируулна.
                prepare.setString(1, String.valueOf(ListData.customerId));
                prepare.setString(2, orders_productType.getSelectionModel().getSelectedItem());
                prepare.setString(3, orders_brand.getSelectionModel().getSelectedItem());
                prepare.setString(4, orders_productName.getSelectionModel().getSelectedItem());
                prepare.setString(5, String.valueOf(qty));
                prepare.setString(6, String.valueOf(totalPData));
                prepare.setString(7, String.valueOf(getSqlDate()));
                prepare.executeUpdate();

                // Захиалгын жагсаалтыг шинэчилнэ.
                ordersShowListData();
                // Нийт дүнг шинэчилнэ.
                ordersDisplayTotal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Хэрэглэгчийн төлбөрийг боловсруулж, төлбөрийн баримтыг бүртгэх функц.
     */
    public void ordersPay() {
        customerId();

        String sql = "INSERT INTO " + DB_TABLE_CUST_RECEIPT + " (customer_id, total, amount, balance, date) " +
                "VALUES (?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            if (!(totalP > 0 || orders_amount.getText().isEmpty() || amountP == 0)) {
                alert.errorMessage("Хүчингүй. Дахин шалгана уу.");
                return;
            }

            if (!alert.confirmMessage("Та итгэлтэй байна уу?")) {
                return;
            }

            prepare = connect.prepareStatement(sql);
            // Төлбөрийн дэлгэрэнгүй мэдээллийг тохируулна.
            prepare.setString(1, String.valueOf(ListData.customerId));
            prepare.setString(2, String.valueOf(totalP));
            prepare.setString(3, String.valueOf(amountP));
            prepare.setString(4, String.valueOf(balanceP));
            prepare.setString(5, String.valueOf(getSqlDate()));
            prepare.executeUpdate();

            alert.successMessage("Амжилттай.");

            // reset fields
            totalP = 0;
            balanceP = 0;
            amountP = 0;
            orders_balance.setText("0.0₮");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JasperReports ашиглан хэрэглэгчийн баримтыг үүсгэх функц.
     */
    public void orderReceipt() {
        HashMap hash = new HashMap();
        // Баримтыг одоогийн хэрэглэгчийн ID-тай холбоно.
        hash.put("inventoryP", ListData.customerId);
        try {
            // Jasper загварыг ажиллуулах
            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Dell\\Documents\\24-25 FALL\\Software Development\\Lab\\InventoryManagementSystem\\src\\main\\java\\com\\example\\inventorymanagementsystem\\Report.jrxml");
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);
            // report харуулах
            JasperViewer.viewReport(jPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * Одоо байгаа хэрэглэгчийн бүх захиалгыг арилгах функц.
     */
    public void ordersReset() {
        customerId();

        String sql = "DELETE FROM " + DB_TABLE_CUSTOMER + " WHERE customer_id = '" + ListData.customerId + "'";
        connect = Database.connectDB();

        try {
            if (orders_tableView.getItems().isEmpty()) {
                alert.errorMessage("Захиалга олдсонгүй!");
                return;
            }
            if (!alert.confirmMessage("Та дахин тохируулахдаа итгэлтэй байна уу?")) {
                return;
            }

            statement = connect.createStatement();
            statement.executeUpdate(sql);

            ordersShowListData();

            balanceP = 0;
            amountP = 0;
            orders_balance.setText("0.0₮");
            orders_total.setText("0.0₮");
            orders_amount.setText("0.0₮");

            alert.successMessage("Амжилттай дахин тохирууллаа.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Нийт үнэ болон оруулсан дүнгийн зөрүүг тооцоолсны дараа үлдэгдлийг шинэчилнэ
     */
    public void ordersAmount() {
        if (orders_amount.getText().isEmpty()) {
            alert.errorMessage("Хэмжээгээ оруулна уу!");
            return;
        }

        amountP = Double.parseDouble(orders_amount.getText());

        if (totalP <= 0) {
            alert.errorMessage("Нийт үнэ 0-с их байх ёстой!");
            return;
        }

        if (amountP >= totalP) {
            balanceP = amountP - totalP;
            orders_balance.setText(balanceP + "₮");
        } else {
            alert.errorMessage("Хангалтгүй дүн. Хүчинтэй төлбөр оруулна уу!");
            orders_amount.setText("");
        }
    }

    /**
     * Одоо байгаа хэрэглэгчийн бүх захиалгын нийт үнийг тооцоолж харуулах функц.
     */
    public void ordersDisplayTotal() {
        customerId();

        String sql = "SELECT SUM(price) FROM " + DB_TABLE_CUSTOMER + " WHERE customer_id = '" + ListData.customerId + "'";
        connect = Database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                // захиалгын нийт үнийг нэгтгэн гаргана.
                totalP = result.getDouble("SUM(price)");
            }
            // Нийт үнийг шинэчилнэ.
            orders_total.setText(totalP + "₮");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ordersListType() {
        orders_productType.setItems(addDataToList(ListData.listType));
        // Сонгосон төрлөөс хамааран брэндийн жагсаалтыг шинэчилнэ.
        ordersListBrand();
    }

    /**
     * Сонгосон бүтээгдэхүүний төрлөөс хамааран брэнд цэсийг дүүргэх
     */
    public void ordersListBrand() {
        String sql = "SELECT brand FROM " + DB_TABLE_PRODUCT + " WHERE type = '"
                + orders_productType.getSelectionModel().getSelectedItem()
                + "' and status = 'Боломжтой' GROUP BY brand";
        connect = Database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                // Сонгосон төрлөөр боломжтой брэндүүдийг fetch хийх.
                listData.add(result.getString("brand"));
            }

            orders_brand.setItems(listData);
            // Сонгосон брэнд дээр үндэслэн бүтээгдэхүүний жагсаалтыг шинэчилнэ.
            ordersListProductName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Сонгосон брэнд дээр үндэслэсэн бүтээгдэхүүний нэр цэсийг дүүргэх функц.
     */
    public void ordersListProductName() {
        String sql = "SELECT product_name FROM " + DB_TABLE_PRODUCT + " WHERE brand = '"
                + orders_brand.getSelectionModel().getSelectedItem() + "'";
        connect = Database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }

            orders_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Тоо хэмжээний spinner control
     */
    public void ordersSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        orders_quantity.setValueFactory(spinner);
    }

    /**
     * Spinner control-оос утгыг авах.
     */
    public void ordersShowSpinnerValue() {
        qty = orders_quantity.getValue();
    }

    /**
     * Одоо байгаа хэрэглэгчдийн бүх захиалгыг өгөгдлийн сангаас татах.
     */
    public ObservableList<CustomerData> ordersListData() {
        customerId();

        ObservableList<CustomerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + DB_TABLE_CUSTOMER + " WHERE customer_id = '" + ListData.customerId + "'";
        connect = Database.connectDB();

        try {
            CustomerData customerD;
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerD = new CustomerData(result.getInt("customer_id"),
                        result.getString("type"),
                        result.getString("brand"),
                        result.getString("product_name"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getDate("date"));

                listData.add(customerD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    /**
     * Захиалгын өгөгдлийг TableView харуулах
     */
    public void ordersShowListData() {
        ordersList = ordersListData();

        orders_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        orders_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orders_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orders_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orders_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ordersDisplayTotal();
        orders_tableView.setItems(ordersList);
    }

    /**
     * Хэрэглэгчийн ID үүсгэх функц.
     */
    public void customerId() {
        String custId = "SELECT * FROM " + DB_TABLE_CUSTOMER + " ";
        connect = Database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(custId);
            result = prepare.executeQuery();

            int checkId = 0;

            while (result.next()) {
                // Сүүлийн customer id-г авах
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT * FROM " + DB_TABLE_CUST_RECEIPT + " ";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            while (result.next()) {
                checkId = result.getInt("customer_id");
            }

            statement = connect.createStatement();

            if (customerId == 0) {
                customerId += 1;
            }
            if (checkId == customerId) {
                customerId += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Home, products, order формуудын хооронд шилжинэ.
     */
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addProducts_form.setVisible(false);
            orders_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: #fff;");
            addProducts_btn.setStyle("-fx-background-color: transparent");
            orders_btn.setStyle("-fx-background-color: transparent");

            homeDisplayTotalOrders();
            homeTotalIncome();
            homeAvailableProducts();

            homeIncomeChart();
            homeOrdersChart();
        }

        if (event.getSource() == addProducts_btn) {
            home_form.setVisible(false);
            addProducts_form.setVisible(true);
            orders_form.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color: #fff;");
            home_btn.setStyle("-fx-background-color: transparent");
            orders_btn.setStyle("-fx-background-color: transparent");

            addProductsShowListData();
            addProductsListStatus();
            addProductsListType();
            addProductsSearch();
        }

        if (event.getSource() == orders_btn) {
            home_form.setVisible(false);
            addProducts_form.setVisible(false);
            orders_form.setVisible(true);

            orders_btn.setStyle("-fx-background-color: #fff;");
            addProducts_btn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");

            ordersShowListData();
            ordersListType();
            ordersListBrand();
            ordersListProductName();
            ordersSpinner();
        }
    }

    public void defaultNav() {
        home_btn.setStyle("-fx-background-color: #fff;");
    }

    /**
     * Хэрэглэгчийг системээс гаргана.
     */
    public void logout() {
        if (!(alert.confirmMessage("Та гарахдаа итгэлтэй байна уу?"))) {
            return;
        }

        try {
            // gol formoo hide hiih
            logout.getScene().getWindow().hide();

            // login form oo holboh
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginSignup.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

                stage.setOpacity(0.8);
            });
            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        username.setText(ListData.username);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
    }

    /**
     * Эхлэх үед dashboard-ийн үндсэн төлөвийг тохируулна.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defaultNav();
        displayUsername();

        homeDisplayTotalOrders();
        homeTotalIncome();
        homeAvailableProducts();

        homeIncomeChart();
        homeOrdersChart();

        // TableView дээр байгаа датаг харуулах
        addProductsShowListData();
        addProductsListStatus();
        addProductsListType();

        ordersShowListData();
        ordersListType();
        ordersListBrand();
        ordersListProductName();
        ordersSpinner();
    }
}
