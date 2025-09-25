import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

class Product {
    String name, category, description;
    int price;

    Product(String name, int price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    String getFormattedPrice() {
        return "Rp " + String.format("%,d", price);
    }
}

class ProductTableModel extends AbstractTableModel {
    private final String[] columns = { "Name", "Category", "Price", "Description" };
    private List<Product> products;

    ProductTableModel(List<Product> products) {
        this.products = products;
    }

    public int getRowCount() {
        return products.size();
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    public Object getValueAt(int row, int col) {
        Product p = products.get(row);
        return switch (col) {
            case 0 -> p.name;
            case 1 -> p.category;
            case 2 -> p.getFormattedPrice();
            case 3 -> p.description;
            default -> null;
        };
    }
}

class MergeSort {
    public static <T> void mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) return;
        
        List<T> temp = new ArrayList<>(list);
        mergeSortHelper(list, temp, 0, list.size() - 1, comparator);
    }
    
    private static <T> void mergeSortHelper(List<T> list, List<T> temp, int left, int right, Comparator<T> comparator) {
        if (left >= right) return;
        
        int mid = left + (right - left) / 2;
        
        // Recursively sort left and right setengah"
        mergeSortHelper(list, temp, left, mid, comparator);
        mergeSortHelper(list, temp, mid + 1, right, comparator);
        
        // Merge setengah yang sorted
        merge(list, temp, left, mid, right, comparator);
    }
    
    private static <T> void merge(List<T> list, List<T> temp, int left, int mid, int right, Comparator<T> comparator) {
        // Copy data to temp arrays
        for (int i = left; i <= right; i++) {
            temp.set(i, list.get(i));
        }
        
        int i = left;    // Initial index dari subarray kiri
        int j = mid + 1; // Initial index dari subarray kanan
        int k = left;    // Initial index dari merged subarray
        
        // Merge the temp arrays back into list[left..right]
        while (i <= mid && j <= right) {
            if (comparator.compare(temp.get(i), temp.get(j)) <= 0) {
                list.set(k, temp.get(i));
                i++;
            } else {
                list.set(k, temp.get(j));
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of left subarray
        while (i <= mid) {
            list.set(k, temp.get(i));
            i++;
            k++;
        }
        
        // Copy remaining elements of right subarray
        while (j <= right) {
            list.set(k, temp.get(j));
            j++;
            k++;
        }
    }
}

public class EComMergeApp extends JFrame {
    private List<Product> products = new ArrayList<>();
    private ProductTableModel tableModel;
    private JTable table;
    private JTextField nameField, priceField, descField, searchField;
    private JComboBox<String> categoryBox, sortBox;
    private JLabel statusLabel;

    public EComMergeApp() {
        setTitle("Electronic Store by Matchanova Team");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primary = new Color(99, 107, 47);
        Color success = new Color(136, 145, 134);
        Color danger = new Color(143, 111, 122);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel header = new JPanel();
        header.setBackground(primary);
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Matchanova Electronic Store", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title);

        JPanel controls = new JPanel(new GridBagLayout());
        controls.setBorder(BorderFactory.createTitledBorder("Product Management"));
        controls.setPreferredSize(new Dimension(280, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        controls.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(12);
        controls.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controls.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(12);
        controls.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        controls.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryBox = new JComboBox<>(new String[] { "Laptop", "Smartphone", "TV", "Audio", "Gaming", "Accessories" });
        controls.add(categoryBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        controls.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descField = new JTextField(12);
        controls.add(descField, gbc);

        JPanel btnPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JButton addBtn = createButton("Add", success);
        JButton updateBtn = createButton("Update", primary);
        JButton deleteBtn = createButton("Delete", danger);
        JButton clearBtn = createButton("Clear", Color.GRAY);
        JButton saveBtn = createButton("Save", new Color(173, 147, 111));
        JButton loadBtn = createButton("Load", new Color(103, 95, 140));

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controls.add(btnPanel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        controls.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        searchField = new JTextField(12);
        controls.add(searchField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        controls.add(new JLabel("Sort:"), gbc);
        gbc.gridx = 1;
        sortBox = new JComboBox<>(new String[] { "Price: Low-High", "Price: High-Low", "Name: A-Z", "Name: Z-A" });
        controls.add(sortBox, gbc);

        JButton sortBtn = createButton("Sort", primary);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        controls.add(sortBtn, gbc);

        tableModel = new ProductTableModel(products);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Product Inventory"));

        statusLabel = new JLabel("Ready - Total: 0 products");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        main.add(header, BorderLayout.NORTH);
        main.add(controls, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);
        main.add(statusLabel, BorderLayout.SOUTH);
        add(main);

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        clearBtn.addActionListener(e -> clearFields());
        sortBtn.addActionListener(e -> sortProducts());
        saveBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("products.csv"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                saveToCSV(fc.getSelectedFile().getAbsolutePath());
        });
        loadBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                loadFromCSV(fc.getSelectedFile().getAbsolutePath());
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                loadSelected();
        });

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterProducts();
            }
        });

        loadSampleData();
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void addProduct() {
        if (validateInput()) {
            products.add(new Product(nameField.getText().trim(),
                    Integer.parseInt(priceField.getText().trim()),
                    categoryBox.getSelectedItem().toString(),
                    descField.getText().trim()));
            refreshTable();
            clearFields();
            updateStatus("Product added!", Color.GREEN);
        }
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;
        if (validateInput()) {
            Product p = products.get(row);
            p.name = nameField.getText().trim();
            p.price = Integer.parseInt(priceField.getText().trim());
            p.category = categoryBox.getSelectedItem().toString();
            p.description = descField.getText().trim();
            refreshTable();
            clearFields();
            updateStatus("Product updated!", Color.BLUE);
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row != -1) {
            products.remove(row);
            refreshTable();
            clearFields();
            updateStatus("Product deleted!", Color.RED);
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        descField.setText("");
        categoryBox.setSelectedIndex(0);
        table.clearSelection();
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Product p = products.get(row);
            nameField.setText(p.name);
            priceField.setText(String.valueOf(p.price));
            categoryBox.setSelectedItem(p.category);
            descField.setText(p.description);
        }
    }

    private void filterProducts() {
        String search = searchField.getText().toLowerCase();
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (p.name.toLowerCase().contains(search) || p.category.toLowerCase().contains(search)) {
                filtered.add(p);
            }
        }
        table.setModel(new ProductTableModel(filtered));
    }

    private void sortProducts() {
        if (products.isEmpty())
            return;
        
        String sort = sortBox.getSelectedItem().toString();
        boolean byPrice = sort.startsWith("Price");
        boolean asc = sort.contains("Low-High") || sort.contains("A-Z");
        
        // Using manual merge sort instead of built-in sort
        Comparator<Product> comparator;
        if (byPrice) {
            comparator = asc ? 
                (a, b) -> Integer.compare(a.price, b.price) : 
                (a, b) -> Integer.compare(b.price, a.price);
        } else {
            comparator = asc ? 
                (a, b) -> a.name.compareToIgnoreCase(b.name) : 
                (a, b) -> b.name.compareToIgnoreCase(a.name);
        }
        
        MergeSort.mergeSort(products, comparator);
        refreshTable();
        updateStatus("Products sorted using Merge Sort!", Color.BLUE);
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name required!");
            return false;
        }
        try {
            int price = Integer.parseInt(priceField.getText().trim());
            if (price < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valid price required!");
            return false;
        }
        return true;
    }

    private void refreshTable() {
        tableModel = new ProductTableModel(products);
        table.setModel(tableModel);
        updateStatus("Total: " + products.size() + " products", Color.BLACK);
    }

    private void updateStatus(String msg, Color color) {
        statusLabel.setText(msg + " - Total: " + products.size() + " products");
        statusLabel.setForeground(color);
    }

    private void loadSampleData() {
        products.add(new Product("iPhone 15 Pro", 15000000, "Smartphone", "Latest Apple smartphone"));
        products.add(new Product("MacBook Air M3", 20000000, "Laptop", "Apple laptop with M3 chip"));
        products.add(new Product("Samsung 4K TV", 8000000, "TV", "55-inch Smart TV"));
        refreshTable();
    }

    private void loadFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            products.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 4) {
                    products.add(new Product(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]));
                }
            }
            refreshTable();
            updateStatus("Loaded from: " + filePath, new Color(41, 128, 185));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage());
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '\"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                        sb.append('\"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == '\"') {
                    inQuotes = true;
                } else if (c == ',') {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }
        }
        tokens.add(sb.toString());
        return tokens.toArray(new String[0]);
    }

    private String csvEscape(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    private void saveToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Product p : products) {
                writer.printf("%s,%d,%s,%s%n",
                        csvEscape(p.name),
                        p.price,
                        csvEscape(p.category),
                        csvEscape(p.description));
            }
            updateStatus("Saved to: " + filePath, new Color(39, 174, 96));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EComMergeApp().setVisible(true));
    }
}
