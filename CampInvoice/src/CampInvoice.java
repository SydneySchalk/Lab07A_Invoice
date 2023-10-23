import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CampInvoice extends JFrame {
    private List<LineItem> lineItems = new ArrayList<>();
    private JTextArea invoiceTextArea;
    private JTextField productNameField, quantityField, unitPriceField;
    private JButton addLineItemButton;

    public CampInvoice() {
        // Initialize the GUI components
        productNameField = new JTextField(20);
        quantityField = new JTextField(5);
        unitPriceField = new JTextField(10);
        addLineItemButton = new JButton("Add Line Item");
        invoiceTextArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);

        // Add action listener for the "Add Line Item" button
        addLineItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLineItem();
            }
        });

        // Set up the layout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Unit Price:"));
        inputPanel.add(unitPriceField);
        inputPanel.add(addLineItemButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH); // Move inputPanel to the bottom

        setTitle("Invoice Application");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new panel to hold both the "Invoice" title and the address block
        JPanel titleAndAddressPanel = new JPanel(new BorderLayout());

        // Add a centered title "Invoice" to the NORTH of the new panel
        JLabel titleLabel = new JLabel("Invoice");
        Font titleFont = new Font("Arial", Font.BOLD, 24); // You can adjust the font size (24 in this example)
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleAndAddressPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a border for the address block
        Border addressBorder = BorderFactory.createLineBorder(Color.BLACK); // You can customize the border color

        // Customer Address Block (Sample address) in the CENTER of the new panel
        JTextArea addressTextArea = new JTextArea("Customer Name\n123 Main St\nCity, State, ZIP", 4, 40);
        addressTextArea.setEditable(false);
        addressTextArea.setBorder(addressBorder); // Apply the border
        titleAndAddressPanel.add(addressTextArea, BorderLayout.CENTER);

        // Add the new panel to the JFrame in the NORTH position
        add(titleAndAddressPanel, BorderLayout.NORTH);
    }
    private void addLineItem() {
        String productName = productNameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double unitPrice = Double.parseDouble(unitPriceField.getText());

        Product product = new Product(productName, unitPrice);
        LineItem item = new LineItem(product, quantity);
        lineItems.add(item);

        updateInvoiceDisplay();
    }

    private void updateInvoiceDisplay() {
        StringBuilder invoiceText = new StringBuilder();
        double totalAmount = 0.00;

        // Add column titles
        invoiceText.append("Item\t\t\t\t\tQuantity\tUnit Price\tTotal\n\n");

        for (LineItem item : lineItems) {
            invoiceText.append(item.getProduct().getName()).append("\t\t\t\t\t");
            invoiceText.append(item.getQuantity()).append("\t");
            invoiceText.append("$").append(String.format("%.2f", item.getProduct().getUnitPrice())).append("\t");
            invoiceText.append("$").append(String.format("%.2f", item.getTotal())).append("\n");

            totalAmount += item.getTotal();
        }
        invoiceText.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        invoiceText.append("\nTotal Amount Due:\t\t\t\t\t\t").append("$").append(String.format("%.2f", totalAmount));

        invoiceTextArea.setText(invoiceText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CampInvoice app = new CampInvoice();
            app.setLocationRelativeTo(null); // Center the JFrame on the screen

            app.setVisible(true);
        });
    }
}

