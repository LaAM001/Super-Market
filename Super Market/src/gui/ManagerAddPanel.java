package gui;

import market.Product;
import market.Supplier;
import market.User;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;

public class ManagerAddPanel extends JPanel implements ActionListener, ItemListener, MouseListener {
    private JLabel id, name, phone, email, address, barcode, price, quantityShelf, quantityStorage, minLimitShelf,
            maxLimitShelf, minLimitStorage, maxLimitStorage, password, shelf, supplierID, shelflabel, storagelabel, background;
    private JTextField idTF, nameTF, phoneTF, emailTF, addressTF, barcodeTF, priceTF, quantityShelfTF,
            quantityStorageTF, shelfTF;
    private JSpinner minLimitShelfSpinner, maxLimitShelfSpinner, minLimitStorageSpinner, maxLimitStorageSpinner;
    private JPasswordField passwordTF;
    private JButton addButton, clearButton, deleteButton, updateButton;
    private Choice job, supplierIDChoice;
    private JPanel detailes;
    private JScrollPane sp;
    private JTable stockTable, cashierTable, supplierTable, productTable;

    // הגדרת הפנל וכל האילימנטים שבתוכו
    public ManagerAddPanel() throws ClassNotFoundException {
        setLayout(null);
        setBounds(0, 85, 1920, 950);

        detailes = new JPanel();
        detailes.setLayout(null);
        detailes.setBounds(1320, 30, 500, 660);
        detailes.setBorder(BorderFactory.createLineBorder(new Color(32, 136, 203), 6));
        detailes.setBackground(Color.white);

        job = new Choice();
        job.add("Stockkeeper");
        job.add("Cashier");
        job.add("Supplier");
        job.add("Product");
        job.setBounds(125, 30, 280, 30);
        detailes.add(job);

        id = new JLabel("ID:");
        id.setHorizontalAlignment(SwingConstants.CENTER);
        id.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        id.setBounds(190, 56, 95, 26);
        detailes.add(id);

        idTF = new JTextField();
        idTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        idTF.setBounds(90, 93, 327, 30);
        detailes.add(idTF);
        idTF.setColumns(10);

        name = new JLabel("Name:");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        name.setBounds(190, 134, 95, 26);
        detailes.add(name);

        nameTF = new JTextField();
        nameTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        nameTF.setColumns(10);
        nameTF.setBounds(90, 171, 327, 30);
        detailes.add(nameTF);

        phone = new JLabel("Phone Number:");
        phone.setHorizontalAlignment(SwingConstants.CENTER);
        phone.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        phone.setBounds(160, 212, 170, 30);
        detailes.add(phone);

        phoneTF = new JTextField();
        phoneTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        phoneTF.setColumns(10);
        phoneTF.setBounds(90, 253, 327, 30);
        detailes.add(phoneTF);

        password = new JLabel("Password:");
        password.setHorizontalAlignment(SwingConstants.CENTER);
        password.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        password.setBounds(125, 376, 235, 30);
        detailes.add(password);

        passwordTF = new JPasswordField();
        passwordTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        passwordTF.setColumns(10);
        passwordTF.setBounds(90, 417, 327, 30);
        detailes.add(passwordTF);

        email = new JLabel("Email:");
        email.setHorizontalAlignment(SwingConstants.CENTER);
        email.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        email.setBounds(125, 294, 235, 30);
        detailes.add(email);

        emailTF = new JTextField();
        emailTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        emailTF.setColumns(10);
        emailTF.setBounds(90, 335, 327, 30);
        detailes.add(emailTF);

        address = new JLabel("Address:");
        address.setHorizontalAlignment(SwingConstants.CENTER);
        address.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        address.setBounds(125, 294, 235, 30);
        address.setVisible(false);
        detailes.add(address);

        addressTF = new JTextField();
        addressTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        addressTF.setColumns(10);
        addressTF.setBounds(90, 335, 327, 30);
        addressTF.setVisible(false);
        detailes.add(addressTF);

        barcode = new JLabel("Barcode:");
        barcode.setHorizontalAlignment(SwingConstants.CENTER);
        barcode.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        barcode.setBounds(190, 56, 95, 26);
        barcode.setVisible(false);
        detailes.add(barcode);

        barcodeTF = new JTextField();
        barcodeTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        barcodeTF.setBounds(90, 93, 327, 30);
        barcodeTF.setVisible(false);
        detailes.add(barcodeTF);
        barcodeTF.setColumns(10);

        price = new JLabel("Price:");
        price.setHorizontalAlignment(SwingConstants.CENTER);
        price.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        price.setBounds(190, 212, 95, 30);
        price.setVisible(false);
        detailes.add(price);

        priceTF = new JTextField();
        priceTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        priceTF.setColumns(10);
        priceTF.setBounds(90, 253, 327, 30);
        priceTF.setVisible(false);
        detailes.add(priceTF);

        shelf = new JLabel("Shelf:");
        shelf.setHorizontalAlignment(SwingConstants.CENTER);
        shelf.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        shelf.setBounds(125, 294, 235, 30);
        shelf.setVisible(false);
        detailes.add(shelf);

        shelfTF = new JTextField();
        shelfTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        shelfTF.setColumns(10);
        shelfTF.setBounds(90, 335, 327, 30);
        shelfTF.setVisible(false);
        detailes.add(shelfTF);

        shelflabel = new JLabel("Shelf");
        shelflabel.setFont(new Font("Times New Roman", Font.BOLD, 23));
        shelflabel.setHorizontalAlignment(SwingConstants.CENTER);
        shelflabel.setBounds(90, 376, 146, 33);
        shelflabel.setVisible(false);
        detailes.add(shelflabel);

        storagelabel = new JLabel("Storage");
        storagelabel.setHorizontalAlignment(SwingConstants.CENTER);
        storagelabel.setFont(new Font("Times New Roman", Font.BOLD, 23));
        storagelabel.setBounds(271, 376, 146, 33);
        storagelabel.setVisible(false);
        detailes.add(storagelabel);

        quantityShelf = new JLabel("Quantity:");
        quantityShelf.setHorizontalAlignment(SwingConstants.CENTER);
        quantityShelf.setFont(new Font("Times New Roman", Font.ITALIC, 21));
        quantityShelf.setBounds(90, 407, 146, 30);
        quantityShelf.setVisible(false);
        detailes.add(quantityShelf);

        quantityShelfTF = new JTextField();
        quantityShelfTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        quantityShelfTF.setColumns(10);
        quantityShelfTF.setBounds(90, 437, 146, 30);
        quantityShelfTF.setVisible(false);
        detailes.add(quantityShelfTF);

        quantityStorage = new JLabel("Quantity:");
        quantityStorage.setHorizontalAlignment(SwingConstants.CENTER);
        quantityStorage.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        quantityStorage.setBounds(271, 407, 146, 30);
        quantityStorage.setVisible(false);
        detailes.add(quantityStorage);

        quantityStorageTF = new JTextField();
        quantityStorageTF.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        quantityStorageTF.setColumns(10);
        quantityStorageTF.setBounds(271, 437, 146, 30);
        quantityStorageTF.setVisible(false);
        detailes.add(quantityStorageTF);

        minLimitShelf = new JLabel("Min Limit:");
        minLimitShelf.setHorizontalAlignment(SwingConstants.CENTER);
        minLimitShelf.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        minLimitShelf.setBounds(110, 478, 110, 26);
        minLimitShelf.setVisible(false);

        detailes.add(minLimitShelf);

        minLimitShelfSpinner = new JSpinner();
        minLimitShelfSpinner.setBounds(130, 515, 60, 30);
        minLimitShelfSpinner.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        minLimitShelfSpinner.setVisible(false);
        minLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        detailes.add(minLimitShelfSpinner);

        maxLimitShelf = new JLabel("Max Limit:");
        maxLimitShelf.setHorizontalAlignment(SwingConstants.CENTER);
        maxLimitShelf.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        maxLimitShelf.setBounds(110, 556, 110, 26);
        maxLimitShelf.setVisible(false);
        detailes.add(maxLimitShelf);

        maxLimitShelfSpinner = new JSpinner();
        maxLimitShelfSpinner.setBounds(130, 591, 60, 30);
        maxLimitShelfSpinner.setVisible(false);
        maxLimitShelfSpinner.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        maxLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        detailes.add(maxLimitShelfSpinner);

        minLimitStorage = new JLabel("Min Limit:");
        minLimitStorage.setHorizontalAlignment(SwingConstants.CENTER);
        minLimitStorage.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        minLimitStorage.setBounds(281, 478, 110, 26);
        minLimitStorage.setVisible(false);
        detailes.add(minLimitStorage);

        minLimitStorageSpinner = new JSpinner();
        minLimitStorageSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        minLimitStorageSpinner.setBounds(300, 515, 60, 30);
        minLimitStorageSpinner.setVisible(false);
        minLimitStorageSpinner.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        detailes.add(minLimitStorageSpinner);

        maxLimitStorage = new JLabel("Max Limit:");
        maxLimitStorage.setHorizontalAlignment(SwingConstants.CENTER);
        maxLimitStorage.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        maxLimitStorage.setBounds(281, 556, 110, 26);
        maxLimitStorage.setVisible(false);
        detailes.add(maxLimitStorage);

        maxLimitStorageSpinner = new JSpinner();
        maxLimitStorageSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        maxLimitStorageSpinner.setBounds(300, 591, 60, 30);
        maxLimitStorageSpinner.setVisible(false);
        maxLimitStorageSpinner.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        detailes.add(maxLimitStorageSpinner);

        supplierID = new JLabel("Supplier Name:");
        supplierID.setHorizontalAlignment(SwingConstants.CENTER);
        supplierID.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        supplierID.setBounds(125, 620, 235, 30);
        supplierID.setVisible(false);
        detailes.add(supplierID);

        supplierIDChoice = new Choice();
        supplierIDChoice.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        supplierIDChoice.setBounds(90, 660, 327, 30);
        supplierIDChoice.setVisible(false);
        detailes.add(supplierIDChoice);

        addButton = new JButton("Add");
        addButton.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.white);
        addButton.setBounds(90, 466, 103, 41);
        detailes.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        deleteButton.setBounds(314, 466, 103, 41);
        deleteButton.setForeground(Color.white);
        deleteButton.setBackground(new Color(244, 67, 54));
        detailes.add(deleteButton);

        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        updateButton.setBounds(90, 525, 103, 41);
        updateButton.setForeground(Color.white);
        updateButton.setBackground(new Color(0, 140, 186));
        detailes.add(updateButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        clearButton.setBounds(314, 525, 103, 41);
        clearButton.setBackground(new Color(255, 255, 255));
        detailes.add(clearButton);

        add(detailes);

        // מפעיל פונקצה שמעדכנת כל הטבלאות
        getTabels();

        job.addItemListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        clearButton.addActionListener(this);

        // הפעלת פונקצית הרקע
        backGround();
    }

    // פונקציה שמוסיפה רקע לפנל
    public void backGround() {
        if (background != null)
            remove(background);
        background = new JLabel(new ImageIcon(ManagerAddPanel.class.getResource("/images/wallpaper.jpeg")));
        background.setBounds(0, 0, 1920, 950);
        add(background);
    }

    // מעדכן כל הטבלאות מהמערכים של ה Service
    public void getTabels() {
        if (sp != null)
            remove(sp);
        String[] columnNames = new String[4];
        String[][] dataArr = new String[0][4];
        // הגדרת שמות העמודות בטבלאות המחסניים והקופאיים
        columnNames[0] = "ID";
        columnNames[1] = "name";
        columnNames[2] = "email";
        columnNames[3] = "phone number";

        // הוספה למערך כל המשתמשים שההרשאה שלהם היא מחסנאי
        for (int i = 0; i < Service.users.size(); i++) {
            if (Service.users.get(i).getPermission().equals("Stockkeeper")) {
                dataArr = Service.insertRow(dataArr,
                        new String[]{Service.users.get(i).getID() + "", Service.users.get(i).getName(),
                                Service.users.get(i).getEmail(), Service.users.get(i).getPhoneNum() + ""});
            }
        }

        // הגדרת הטבלה של המחסנאי
        stockTable = new JTable(dataArr, columnNames) {
            // לא מאפשר עריכה של הטבלה
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            // מזיז כל הטקסט לאמצע
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                return comp;
            }

        };


        dataArr = new String[0][4];
        // הוספה למערך כל המשתמשים שההרשאה שלהם היא קופאי
        for (int i = 0; i < Service.users.size(); i++) {
            if (Service.users.get(i).getPermission().equals("Cashier")) {
                dataArr = Service.insertRow(dataArr,
                        new String[]{Service.users.get(i).getID() + "", Service.users.get(i).getName(),
                                Service.users.get(i).getEmail(), Service.users.get(i).getPhoneNum() + ""});
            }
        }
        // הגדרת הטבלה של הקופאי
        cashierTable = new JTable(dataArr, columnNames) {
            // לא מאפשר עריכה של הטבלה
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            // מזיז כל הטקסט לאמצע
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                return comp;
            }
        };
        cashierTable.setFocusable(false);

        // הגדרת שמות העמודות בטבלה של הספקים
        columnNames[0] = "ID";
        columnNames[1] = "name";
        columnNames[2] = "phone number";
        columnNames[3] = "address";

        dataArr = new String[0][4];
        // הוספה למערך כל הספקים
        for (int i = 0; i < Service.suppliers.size(); i++) {
            dataArr = Service.insertRow(dataArr,
                    new String[]{Service.suppliers.get(i).getID() + "", Service.suppliers.get(i).getName(),
                            Service.suppliers.get(i).getPhoneNum() + "", Service.suppliers.get(i).getAddress()});

        }
        // הגדרת הטבלה של הספקים
        supplierTable = new JTable(dataArr, columnNames) {
            // לא מאפשר עריכה של הטבלה
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            // מזיז כל הטקסט לאמצע
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                return comp;
            }
        };
        supplierTable.setFocusable(false);

        // הגדרת שמות העמודות בטבלה של המוצרים
        columnNames = new String[11];
        columnNames[0] = "barcode";
        columnNames[1] = "name";
        columnNames[2] = "price";
        columnNames[3] = "shelf";
        columnNames[4] = "quantity shelf";
        columnNames[5] = "quantity storage";
        columnNames[6] = "min shelf";
        columnNames[7] = "max shelf";
        columnNames[8] = "min storage";
        columnNames[9] = "max storage";
        columnNames[10] = "supplier";

        dataArr = new String[0][11];
        // הוספה למערך כל המוצרים
        for (int i = 0; i < Service.products.size(); i++) {
            dataArr = Service.insertRow(dataArr, new String[]{Service.products.get(i).getBarcode() + "",
                    Service.products.get(i).getName(), Service.products.get(i).getPrice() + "",
                    Service.products.get(i).getShelf() + "", Service.products.get(i).getQuantityShelf() + "",
                    Service.products.get(i).getQuantityStorage() + "", Service.products.get(i).getMinLimitShelf() + "",
                    Service.products.get(i).getMaxLimitShelf() + "", Service.products.get(i).getMinLimitStorage() + "",
                    Service.products.get(i).getMaxLimitStorage() + "", Service.products.get(i).getSupplier().getName()});

        }
        // הגדרת הטבלה של המוצרים
        productTable = new JTable(dataArr, columnNames) {
            // לא מאפשר עריכה של הטבלה
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            // מזיז כל הטקסט לאמצע
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                return comp;
            }
        };

        // הוספת עיצוב לכל הטבלאות
        Service.tableDesign(cashierTable);
        Service.tableDesign(stockTable);
        Service.tableDesign(productTable);
        Service.tableDesign(supplierTable);

        // הוספת מאזין לכל הטבלאות
        stockTable.addMouseListener(this);
        cashierTable.addMouseListener(this);
        supplierTable.addMouseListener(this);
        productTable.addMouseListener(this);

        // מגדיר את הגודל של כל הטבלאות
        if (job.getSelectedItem().equals("Product")) {
            sp = new JScrollPane(productTable);
            sp.setBounds(10, 30, 1300, 866);
            add(sp);
            sp.setVisible(true);
        } else if (job.getSelectedItem().equals("Supplier")) {
            sp = new JScrollPane(supplierTable);
            sp.setBounds(10, 30, 1100, 866);
            add(sp);
        } else if (job.getSelectedItem().equals("Cashier")) {
            sp = new JScrollPane(cashierTable);
            sp.setBounds(10, 30, 1100, 866);
            add(sp);
        } else if (job.getSelectedItem().equals("Stockkeeper")) {
            sp = new JScrollPane(stockTable);
            sp.setBounds(10, 30, 1100, 866);
            add(sp);
        }
        sp.getViewport().setBackground(new Color(255, 255, 255));
        sp.setBorder(BorderFactory.createLineBorder(new Color(32, 136, 203), 6));

        supplierIDChoice.removeAll();
        // מוסיף כל הספקים לשדה האופציות
        for (int i = 0; i < Service.suppliers.size(); i++) {
            supplierIDChoice.add(Service.suppliers.get(i).getName());
        }


        // הפעלת פונקצית הרקע
        backGround();
    }

    // בדיקת כל החריגות של הקלט
    public boolean exceptions() {
        try {
            if (job.getSelectedItem().equals("Stockkeeper") || job.getSelectedItem().equals("Cashier")) {
                if (idTF.getText().length() != 9 || !idTF.getText().matches("^[0-9]*$"))
                    throw new Exception("id");
                else if (nameTF.getText().length() == 0)
                    throw new Exception("name");
                else if (phoneTF.getText().length() != 10 || !phoneTF.getText().matches("^[0-9]*$"))
                    throw new Exception("phone");
                else if (emailTF.getText().length() == 0
                        || !emailTF.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                    throw new Exception("email");
                else if (passwordTF.getText().length() == 0)
                    throw new Exception("password");
            } else if (job.getSelectedItem().equals("Supplier")) {
                if (idTF.getText().length() != 9 || !idTF.getText().matches("^[0-9]*$"))
                    throw new Exception("id");
                else if (nameTF.getText().length() == 0)
                    throw new Exception("name");
                else if (phoneTF.getText().length() != 10 || !phoneTF.getText().matches("^[0-9]*$"))
                    throw new Exception("phone");
                else if (addressTF.getText().length() == 0)
                    throw new Exception("address");
            } else if (job.getSelectedItem().equals("Product")) {
                if (!barcodeTF.getText().matches("^[0-9]*$") || barcodeTF.getText().length() == 0)
                    throw new Exception("barcode");
                else if (nameTF.getText().length() == 0)
                    throw new Exception("name");
                else if (!priceTF.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || priceTF.getText().length() == 0)
                    throw new Exception("price");
                else if (!quantityShelfTF.getText().matches("^[0-9]*$") || quantityShelfTF.getText().length() == 0)
                    throw new Exception("quantityShelf");
                else if ((int) minLimitShelfSpinner.getValue() > Integer.parseInt(quantityShelfTF.getText()))
                    throw new Exception("minShelf");
                else if ((int) maxLimitShelfSpinner.getValue() < Integer.parseInt(quantityShelfTF.getText()))
                    throw new Exception("maxShelf");
                else if (!quantityStorageTF.getText().matches("^[0-9]*$") || quantityStorageTF.getText().length() == 0)
                    throw new Exception("quantityStorage");
                else if ((int) minLimitStorageSpinner.getValue() > Integer.parseInt(quantityStorageTF.getText()))
                    throw new Exception("minStorage");
                else if ((int) maxLimitStorageSpinner.getValue() < Integer.parseInt(quantityStorageTF.getText()))
                    throw new Exception("maxStorage");
                else if (supplierIDChoice.getItemCount() == 0)
                    throw new Exception("noSuppliers");

            }
        } catch (Exception e) {
            if (e.getMessage().equals("id"))
                JOptionPane.showMessageDialog(null, "Check ID", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("name"))
                JOptionPane.showMessageDialog(null, "Check Name", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("phone"))
                JOptionPane.showMessageDialog(null, "Check Phone Number", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("email"))
                JOptionPane.showMessageDialog(null, "Check Email Address", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("password"))
                JOptionPane.showMessageDialog(null, "Check Password", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("address"))
                JOptionPane.showMessageDialog(null, "Check Address", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("barcode"))
                JOptionPane.showMessageDialog(null, "Check Barcode", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("price"))
                JOptionPane.showMessageDialog(null, "Check Price", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("quantityShelf"))
                JOptionPane.showMessageDialog(null, "Check Quantity On Shelf", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("minShelf"))
                JOptionPane.showMessageDialog(null, "Check Minimum Quantity Shelf", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("maxShelf"))
                JOptionPane.showMessageDialog(null, "Check Maximum Quantity Shelf", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("quantityStorage"))
                JOptionPane.showMessageDialog(null, "Check Quantity In Storage", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("minStorage"))
                JOptionPane.showMessageDialog(null, "Check Minimum Quantity Storage", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("maxStorage"))
                JOptionPane.showMessageDialog(null, "Check Maximum Quantity Storage", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);
            else if (e.getMessage().equals("noSuppliers"))
                JOptionPane.showMessageDialog(null, "There is no Suppliers", "Invalid Value",
                        JOptionPane.INFORMATION_MESSAGE);

            return false;
        }
        return true;
    }

    //  שינוי לממשק הרצוי לפי בחירת המשתמש
    @Override
    public void itemStateChanged(ItemEvent e) {
        //מפעיל פונקצה שמעדכנת כל הטבלאות
        getTabels();
        remove(sp);
        idTF.setText("");
        nameTF.setText("");
        phoneTF.setText("");
        emailTF.setText("");
        barcodeTF.setText("");
        priceTF.setText("");
        quantityShelfTF.setText("");
        quantityStorageTF.setText("");
        addressTF.setText("");
        passwordTF.setText("");
        shelfTF.setText("");
        minLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        maxLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        minLimitStorageSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
        maxLimitStorageSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));

        // בחירת אפשרות מסויימת ועריכת הממשק לתוצאה המתאימה
        if (!job.getSelectedItem().equals("Product")) {
            detailes.setBounds(1320, 30, 500, 660);
            id.setVisible(true);
            idTF.setVisible(true);
            name.setVisible(true);
            nameTF.setVisible(true);
            phone.setVisible(true);
            phoneTF.setVisible(true);
            password.setVisible(true);
            passwordTF.setVisible(true);

            barcode.setVisible(false);
            barcodeTF.setVisible(false);
            price.setVisible(false);
            priceTF.setVisible(false);
            quantityShelf.setVisible(false);
            quantityShelfTF.setVisible(false);
            quantityStorage.setVisible(false);
            quantityStorageTF.setVisible(false);
            minLimitShelf.setVisible(false);
            maxLimitShelf.setVisible(false);
            shelf.setVisible(false);
            shelfTF.setVisible(false);
            maxLimitShelfSpinner.setVisible(false);
            minLimitShelfSpinner.setVisible(false);
            maxLimitStorageSpinner.setVisible(false);
            minLimitStorageSpinner.setVisible(false);
            minLimitStorage.setVisible(false);
            maxLimitStorage.setVisible(false);
            storagelabel.setVisible(false);
            shelflabel.setVisible(false);
            supplierID.setVisible(false);
            supplierIDChoice.setVisible(false);

            addButton.setBounds(90, 466, 103, 41);
            deleteButton.setBounds(314, 466, 103, 41);
            updateButton.setBounds(90, 525, 103, 41);
            clearButton.setBounds(314, 525, 103, 41);

        }
        if (job.getSelectedItem().equals("Cashier") || job.getSelectedItem().equals("Stockkeeper")) {

            email.setVisible(true);
            emailTF.setVisible(true);
            address.setVisible(false);
            addressTF.setVisible(false);
            if (job.getSelectedItem().equals("Cashier"))
                sp = new JScrollPane(cashierTable);
            else
                sp = new JScrollPane(stockTable);


        } else if (job.getSelectedItem().equals("Supplier")) {
            sp = new JScrollPane(supplierTable);

            address.setVisible(true);
            addressTF.setVisible(true);
            email.setVisible(false);
            emailTF.setVisible(false);
            password.setVisible(false);
            passwordTF.setVisible(false);

        } else if (job.getSelectedItem().equals("Product")) {
            sp = new JScrollPane(productTable);

            detailes.setBounds(1320, 30, 500, 866);

            addButton.setBounds(90, 702, 103, 41);
            deleteButton.setBounds(314, 702, 103, 41);
            updateButton.setBounds(90, 760, 103, 41);
            clearButton.setBounds(314, 760, 103, 41);

            barcode.setVisible(true);
            barcodeTF.setVisible(true);
            name.setVisible(true);
            nameTF.setVisible(true);
            price.setVisible(true);
            priceTF.setVisible(true);
            shelf.setVisible(true);
            shelfTF.setVisible(true);
            quantityShelf.setVisible(true);
            quantityShelfTF.setVisible(true);
            quantityStorage.setVisible(true);
            quantityStorageTF.setVisible(true);
            minLimitShelf.setVisible(true);
            maxLimitShelf.setVisible(true);
            minLimitShelfSpinner.setVisible(true);
            maxLimitShelfSpinner.setVisible(true);
            maxLimitStorageSpinner.setVisible(true);
            minLimitStorageSpinner.setVisible(true);
            minLimitStorage.setVisible(true);
            maxLimitStorage.setVisible(true);
            storagelabel.setVisible(true);
            shelflabel.setVisible(true);
            supplierID.setVisible(true);
            supplierIDChoice.setVisible(true);

            id.setVisible(false);
            idTF.setVisible(false);
            email.setVisible(false);
            emailTF.setVisible(false);
            password.setVisible(false);
            passwordTF.setVisible(false);
            phone.setVisible(false);
            phoneTF.setVisible(false);
            address.setVisible(false);
            addressTF.setVisible(false);
        }
        sp.getViewport().setBackground(new Color(255, 255, 255));
        sp.setBorder(BorderFactory.createLineBorder(new Color(32, 136, 203), 6));
        if (job.getSelectedItem().equals("Product"))
            sp.setBounds(10, 30, 1300, 866);
        else
            sp.setBounds(10, 30, 1100, 866);
        add(sp);
        // הפעלת פונקצית הרקע
        backGround();
    }

    // הפונקציה מופעלת בלחיצה על אחד מהכפתורים
    @Override
    public void actionPerformed(ActionEvent e) {
        // בעת לחיצה מוסיף את המידע המתאים למקום המתאים
        if (e.getSource() == addButton) {
            // בדיקת חריגות
            if (!exceptions())
                return;
            if (job.getSelectedItem().equals("Stockkeeper")) {
                // בדיקה אם המחסנאי כבר קיים במערכת
                for (int i = 0; i < Service.users.size(); i++) {
                    if (Service.users.get(i).getID() == Integer.parseInt(idTF.getText())) {
                        JOptionPane.showMessageDialog(null, "User is exist", "", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                User stockkeeper = new User(Integer.parseInt(idTF.getText()), nameTF.getText(), emailTF.getText(),
                        phoneTF.getText(), passwordTF.getText(), "Stockkeeper");
                // שליחת פרטי המחסנאי לפונקציה להמשך טיפול
                SendToServer.add(stockkeeper.toString());
            } else if (job.getSelectedItem().equals("Cashier")) {
                // בדיקה אם המחסנאי כבר קיים במערכת
                for (int i = 0; i < Service.users.size(); i++) {
                    if (Service.users.get(i).getID() == Integer.parseInt(idTF.getText())) {
                        JOptionPane.showMessageDialog(null, "User is exist", "", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                User cashier = new User(Integer.parseInt(idTF.getText()), nameTF.getText(), emailTF.getText(),
                        phoneTF.getText(), passwordTF.getText(), "Cashier");
                // שליחת פרטי הקופאי לפונקציה להמשך טיפול
                SendToServer.add(cashier.toString());
            } else if (job.getSelectedItem().equals("Supplier")) {
                // בדיקה אם הספק כבר קיים במערכת
                for (int i = 0; i < Service.suppliers.size(); i++) {
                    if (Service.suppliers.get(i).getID() == Integer.parseInt(idTF.getText())) {
                        JOptionPane.showMessageDialog(null, "Supplier is exist", "", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                Supplier supplier = new Supplier(Integer.parseInt(idTF.getText()), nameTF.getText(), phoneTF.getText(), addressTF.getText());
                // שליחת פרטי הקופאי לפונקציה להמשך טיפול
                SendToServer.add(supplier.toString());
            } else if (job.getSelectedItem().equals("Product")) {
                // בדיקה אם המוצר כבר קיים במערכת
                for (int i = 0; i < Service.products.size(); i++) {
                    if (Service.products.get(i).getBarcode() == Integer.parseInt(barcodeTF.getText())) {
                        JOptionPane.showMessageDialog(null, "product is exist", "", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                int i;
                for (i = 0; i < Service.suppliers.size(); i++) {
                    if (Service.suppliers.get(i).getName().equals(supplierIDChoice.getSelectedItem())) {
                        break;
                    }
                }

                Product product = new Product(Integer.parseInt(barcodeTF.getText()), nameTF.getText(),
                        Double.parseDouble(priceTF.getText()), shelfTF.getText(),
                        Integer.parseInt(quantityShelfTF.getText()), Integer.parseInt(quantityStorageTF.getText()),
                        (int) minLimitShelfSpinner.getValue(), (int) maxLimitShelfSpinner.getValue(),
                        (int) minLimitStorageSpinner.getValue(), (int) maxLimitStorageSpinner.getValue(),
                        Service.suppliers.get(i).getID());
                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                SendToServer.add(product.toString());

            }
            idTF.setText("");
            nameTF.setText("");
            phoneTF.setText("");
            addressTF.setText("");
            passwordTF.setText("");
            emailTF.setText("");
            barcodeTF.setText("");
            priceTF.setText("");
            shelfTF.setText("");
            quantityShelfTF.setText("");
            quantityStorageTF.setText("");

        }
        // בעת לחיצה מוחק מוחק את כל הטקסט שבשדות
        else if (e.getSource() == clearButton) {
            idTF.setText("");
            nameTF.setText("");
            phoneTF.setText("");
            emailTF.setText("");
            barcodeTF.setText("");
            priceTF.setText("");
            quantityShelfTF.setText("");
            quantityStorageTF.setText("");
            addressTF.setText("");
            passwordTF.setText("");
            shelfTF.setText("");
            minLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
            maxLimitShelfSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
            minLimitStorageSpinner
                    .setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
            maxLimitStorageSpinner
                    .setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
            supplierIDChoice.select(supplierIDChoice.getItem(0));
        }
        // בעת לחיצה מבצע עדכון לנתונים
        else if (e.getSource() == updateButton) {
            // בדיקת חריגות
            if (!exceptions())
                return;
            if (job.getSelectedItem().equals("Stockkeeper")) {
                User stockkeeper = new User(Integer.parseInt(idTF.getText()), nameTF.getText(), emailTF.getText(),
                        phoneTF.getText(), passwordTF.getText(), "Stockkeeper");
                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                SendToServer.update("Update#" + stockkeeper.toString());
            } else if (job.getSelectedItem().equals("Cashier")) {
                User cashier = new User(Integer.parseInt(idTF.getText()), nameTF.getText(), emailTF.getText(),
                        phoneTF.getText(), passwordTF.getText(), "Cashier");
                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                SendToServer.update("Update#" + cashier.toString());
            } else if (job.getSelectedItem().equals("Supplier")) {
                Supplier supplier = new Supplier(Integer.parseInt(idTF.getText()), nameTF.getText(), phoneTF.getText(),
                        addressTF.getText());
                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                SendToServer.update("Update#" + supplier.toString());

            } else if (job.getSelectedItem().equals("Product")) {
                int supplierID = 0;
                for (int i = 0; i < Service.suppliers.size(); i++) {
                    if (supplierIDChoice.getSelectedItem().equals(Service.suppliers.get(i).getName())) {
                        supplierID = Service.suppliers.get(i).getID();
                        break;
                    }
                }
                boolean correctBarcode = false;
                for (int i = 0; i < Service.products.size(); i++) {
                    if (Service.products.get(i).getBarcode() == Integer.parseInt(barcodeTF.getText())) {
                        Product product = new Product(Integer.parseInt(barcodeTF.getText()), nameTF.getText(),
                                Double.parseDouble(priceTF.getText()), shelfTF.getText(),
                                Integer.parseInt(quantityShelfTF.getText()), Integer.parseInt(quantityStorageTF.getText()),
                                (int) minLimitShelfSpinner.getValue(), (int) maxLimitShelfSpinner.getValue(),
                                (int) minLimitStorageSpinner.getValue(), (int) maxLimitStorageSpinner.getValue(),
                                supplierID);
                        // שליחת פרטי המוצר לפונקציה להמשך טיפול
                        SendToServer.update("Update#" + product.toString());
                        correctBarcode = true;
                        break;
                    }
                }
                if (correctBarcode == false)
                    JOptionPane.showMessageDialog(null, "Product not found, Check Barcode", "", JOptionPane.INFORMATION_MESSAGE);

            }

        } else if (e.getSource() == deleteButton) {
            // בדיקה אם ת.ז תקינה
            if (!job.getSelectedItem().equals("Product") && idTF.getText().length() != 9) {
                JOptionPane.showMessageDialog(null, "Check ID", "Invalid Value", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (job.getSelectedItem().equals("Stockkeeper")) {
                // שליחת פרטי המחסנאי לפונקציה להמשך טיפול
                SendToServer.delete("Stockkeeper", Integer.parseInt(idTF.getText()));
            } else if (job.getSelectedItem().equals("Cashier")) {
                // שליחת פרטי הקופאי לפונקציה להמשך טיפול
                SendToServer.delete("Cashier", Integer.parseInt(idTF.getText()));
                // שליחת פרטי הספק לפונקציה להמשך טיפול
            } else if (job.getSelectedItem().equals("Supplier")) {
                int cnt = 0;
                // בדיקה האם יש מוצרים שייכים לספק מסויים
                for (int i = 0; i < Service.products.size(); i++) {
                    if (Service.products.get(i).getSupplier().getID() == Integer.parseInt(idTF.getText()))
                        cnt++;
                }
                // אם אין, מוחקים את הספק
                if (cnt == 0) {
                    supplierIDChoice.remove(nameTF.getText());
                    SendToServer.delete("Supplier", Integer.parseInt(idTF.getText()));
                }
                // אם יש...
                else {
                    boolean deleteSupplier = false;
                    // מחליט אם רוצה למחוק את כל המוצרים והספק
                    int res = JOptionPane.showConfirmDialog(this,
                            "There are many products belong to this supplier,\n Are you sure you want to delete this supplier and his products?",
                            "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    // אם כן
                    if (res == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < Service.products.size(); i++) {
                            if (Service.products.get(i).getSupplier().getID() == Integer.parseInt(idTF.getText())) {
                                if (Service.products.get(i).getQuantityShelf() == 0 && Service.products.get(i).getQuantityStorage() == 0) {
                                    // מוחק את כל ההתראות ששייכות למוצר
                                    for (int j = 0; j < Service.notifications.size(); j++) {
                                        if (Service.notifications.get(j).getProduct().getBarcode() == Service.products.get(i).getBarcode()) {
                                            // שליחת פרטי ההתראה לפונקציה להמשך טיפול
                                            SendToServer.notification("Delete", Service.notifications.get(j).toString());
                                            break;
                                        }
                                    }
                                    // שליחת פרטי המוצר לפונקציה להמשך טיפול
                                    SendToServer.delete("Product", Service.products.get(i).getBarcode());
                                    deleteSupplier = true;
                                } else {
                                    // המוצר עדיין במלאי
                                    res = JOptionPane.showConfirmDialog(this,
                                            "The product " + Service.products.get(i).getName()
                                                    + " is in stock,\n Are you sure to delete it?",
                                            "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    if (res == JOptionPane.YES_OPTION) {
                                        // מוחק את כל ההתראות ששייכות למוצר
                                        for (int j = 0; j < Service.notifications.size(); j++) {
                                            if (Service.notifications.get(j).getProduct().getBarcode() == Service.products.get(i).getBarcode()) {
                                                // שליחת פרטי ההתראה לפונקציה להמשך טיפול
                                                SendToServer.notification("Delete", Service.notifications.get(j).toString());
                                                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                                                SendToServer.delete("Product", Service.products.get(i).getBarcode());
                                                deleteSupplier = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (deleteSupplier == true) {
                            supplierIDChoice.remove(nameTF.getText());
                            // שליחת פרטי הספק לפונקציה להמשך טיפול
                            SendToServer.delete("Supplier", Integer.parseInt(idTF.getText()));
                        }
                    }
                }
            } else if (job.getSelectedItem().equals("Product")) {
                boolean correctBarcode = false;
                for (int i = 0; i < Service.products.size(); i++) {
                    if (Service.products.get(i).getBarcode() == Integer.parseInt(barcodeTF.getText())) {
                        correctBarcode = true;
                        if (Service.products.get(i).getQuantityShelf() == 0 && Service.products.get(i).getQuantityStorage() == 0) {
                            // מוחק את כל ההתראות ששייכות למוצר
                            for (int j = 0; j < Service.notifications.size(); j++) {
                                if (Service.notifications.get(j).getProduct().getBarcode() == Service.products.get(i).getBarcode()) {
                                    // שליחת פרטי ההתראה לפונקציה להמשך טיפול
                                    SendToServer.notification("Delete", Service.notifications.get(j).toString());
                                    break;
                                }
                            }
                            // שליחת פרטי המוצר לפונקציה להמשך טיפול
                            SendToServer.delete("Product", Integer.parseInt(barcodeTF.getText()));
                        } else {
                            // המוצר עדיין במלאי
                            int res = JOptionPane.showConfirmDialog(this,
                                    "The product " + Service.products.get(i).getName()
                                            + " is in stock,\n Are you sure to delete it?",
                                    "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (res == JOptionPane.YES_OPTION) {
                                // מוחק את כל ההתראות ששייכות למוצר
                                for (int j = 0; j < Service.notifications.size(); j++) {
                                    if (Service.notifications.get(j).getProduct().getBarcode() == Service.products.get(i).getBarcode()) {
                                        // שליחת פרטי ההתראה לפונקציה להמשך טיפול
                                        SendToServer.notification("Delete", Service.notifications.get(j).toString());
                                        break;
                                    }
                                }
                                // שליחת פרטי המוצר לפונקציה להמשך טיפול
                                SendToServer.delete("Product", Integer.parseInt(barcodeTF.getText()));
                            }
                        }
                        break;
                    }
                }
                // אם לא מצא את הברקוד של המוצר
                if (correctBarcode == false)
                    JOptionPane.showMessageDialog(null, "Product not found, Check Barcode", "",
                            JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    // פונקציה מופעלת כשלוחצים על שורה מאחת הטבלאות
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // to detect double click events
            //זיהוי האינדקס של השורה מתוך הטבלה
            int row;
            // העברת הפרטים לשדות
            if (job.getSelectedItem().equals("Supplier")) {
                row = supplierTable.getSelectedRow();
                idTF.setText((String) supplierTable.getValueAt(row, 0));
                nameTF.setText((String) supplierTable.getValueAt(row, 1));
                phoneTF.setText((String) supplierTable.getValueAt(row, 2));
                addressTF.setText((String) supplierTable.getValueAt(row, 3));

            } else if (job.getSelectedItem().equals("Product")) {
                row = productTable.getSelectedRow();
                barcodeTF.setText((String) productTable.getValueAt(row, 0));
                nameTF.setText((String) productTable.getValueAt(row, 1));
                priceTF.setText((String) productTable.getValueAt(row, 2));
                shelfTF.setText((String) productTable.getValueAt(row, 3));
                quantityShelfTF.setText((String) productTable.getValueAt(row, 4));
                quantityStorageTF.setText((String) productTable.getValueAt(row, 5));
                minLimitShelfSpinner
                        .setModel(new SpinnerNumberModel(Integer.parseInt((String) productTable.getValueAt(row, 6)),
                                new Integer(0), null, new Integer(1)));
                maxLimitShelfSpinner
                        .setModel(new SpinnerNumberModel(Integer.parseInt((String) productTable.getValueAt(row, 7)),
                                new Integer(0), null, new Integer(1)));
                minLimitStorageSpinner
                        .setModel(new SpinnerNumberModel(Integer.parseInt((String) productTable.getValueAt(row, 8)),
                                new Integer(0), null, new Integer(1)));
                maxLimitStorageSpinner
                        .setModel(new SpinnerNumberModel(Integer.parseInt((String) productTable.getValueAt(row, 9)),
                                new Integer(0), null, new Integer(1)));
                for (int i = 0; i < Service.products.size(); i++) {
                    if (Integer.parseInt(barcodeTF.getText()) == Service.products.get(i).getBarcode()) {
                        supplierIDChoice.select(Service.products.get(i).getSupplier().getName());
                        break;
                    }
                }

            } else if (job.getSelectedItem().equals("Stockkeeper")) {
                row = stockTable.getSelectedRow();
                idTF.setText((String) stockTable.getValueAt(row, 0));
                nameTF.setText((String) stockTable.getValueAt(row, 1));
                emailTF.setText((String) stockTable.getValueAt(row, 2));
                phoneTF.setText((String) stockTable.getValueAt(row, 3));
                for (int i = 0; i < Service.users.size(); i++) {
                    if (Integer.parseInt(idTF.getText()) == Service.users.get(i).getID()) {
                        passwordTF.setText(Service.users.get(i).getPassword());
                        break;
                    }
                }
            } else if (job.getSelectedItem().equals("Cashier")) {
                row = cashierTable.getSelectedRow();
                idTF.setText((String) cashierTable.getValueAt(row, 0));
                nameTF.setText((String) cashierTable.getValueAt(row, 1));
                emailTF.setText((String) cashierTable.getValueAt(row, 2));
                phoneTF.setText((String) cashierTable.getValueAt(row, 3));
                for (int i = 0; i < Service.users.size(); i++) {
                    if (Integer.parseInt(idTF.getText()) == Service.users.get(i).getID()) {
                        passwordTF.setText(Service.users.get(i).getPassword());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}