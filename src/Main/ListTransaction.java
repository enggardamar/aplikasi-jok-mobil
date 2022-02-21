package Main;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListTransaction extends JFrame {

    private final DefaultTableModel dtm = new DefaultTableModel();

    private final Connect conn = Connect.getInstance();
    private final Vector<String> headerTable = new Vector<>();
    private final Vector<Vector<String>> dataTable = new Vector<>();

    public ListTransaction() {
        createForm();
        JLabel titleLabel = new JLabel("Trasaction Member");
        add(titleLabel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        JButton button = new JButton("Insert Transaction");
        button.addActionListener(actionEvent -> {
            setVisible(false);
            new InsertTransaction(() -> {
                refreshData();
                setVisible(true);
            });
        });
        add(button, BorderLayout.SOUTH);    
        table.setModel(dtm);
        refreshData();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                conn.close();
            }
        });
    }

    public void refreshData() {
        headerTable.clear();
        dataTable.clear();
        headerTable.add("ID");
        headerTable.add("Nama");
        headerTable.add("No Telp");
        headerTable.add("No Booking");
        headerTable.add("Jenis Mobil");
        headerTable.add("Jenis Seat");                
        headerTable.add("Total Paid");
        dtm.setDataVector(dataTable, headerTable);
        try {
            ResultSet rs = conn.executeQuery("SELECT * FROM member");
            while (rs.next()) {
                Vector<String> product = new Vector<>();
                product.add(rs.getString("id"));
                product.add(rs.getString("nama"));
                product.add(rs.getString("no_telepon"));
                product.add(rs.getString("no_booking"));
                product.add(rs.getString("jenis_mobil"));
                product.add(rs.getString("jenis_bahan_seat"));
                product.add(String.valueOf(rs.getInt("total")));
                dataTable.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createForm() {
        setTitle("Transaction History");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        
    }

}
