package Main;


import Main.ListTransaction;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class InsertTransaction extends JFrame {
    private final Connect conn = Connect.getInstance();
    private final String[] typesMobil = {};
    private final String[] typesSeat = {};
    private final String[] typesPaket = {"Paket Mobil", "Per Seat",};


    private JPanel panel;
    private JPanel formPanel;
    private GridBagConstraints constraints;
    private GridBagConstraints formConstraints;
    private JLabel lblTitle;
    private JLabel lblNoBooking;
    private JTextField tfNoBooking;
    private JLabel lblNama;
    private JTextField tfNama;
    private JLabel lblNoTelp;
    private JTextField tfNoTelp;
    private JLabel lblJMobil;
    private JComboBox<String> cbJMobil;
    private JLabel lblJSeat;
    private JComboBox<String> cbJSeat;
    private JLabel lblTSeat;
    private JTextField tfTSeat;
    private JLabel lblJPaket;
    private JComboBox<String> cbJPaket;
    private JButton btnInsert;

    public void dataFromDataBaseToComboBox(){
        try {
            String query = "SELECT * FROM jenis_mobil";
            ResultSet rs = conn.executeQuery(query);
            System.out.println(rs);
            
            while (rs.next()) {    
                String name = rs.getString("name");
                cbJMobil.addItem(name);
            }

            query = "SELECT * FROM jenis_seat";
            rs = conn.executeQuery(query);
            System.out.println(rs);
            
            while (rs.next()) {    
                String name = rs.getString("name");
                cbJSeat.addItem(name);
            }
            
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
            
        } catch (SQLException e) {
        }
    }

    public void generateNoBooking(){
        try {
            String query = "SELECT count(*) FROM member";
            ResultSet rs = conn.executeQuery(query);
            int count = 0;
            
            while (rs.next()) {    
                 count = rs.getInt(1);
            }
        
            tfNoBooking.setText("TRX0"+(count+1));
            tfNoBooking.setEnabled(false);
            
            
        } catch (SQLException e) {
        }
    }

    public InsertTransaction(Sample sample) {
        initView();
        dataFromDataBaseToComboBox();
        generateNoBooking();
        addLabels();
        addInputFields();
        add(panel);
        setTitle("Insert Form");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sample.showFrame();
            }
        });
    }

    private void initView() {
        lblTitle = new JLabel("Insert New Transacation");
        lblNoBooking = new JLabel("No Booking");
        tfNoBooking = new JTextField();
        lblNama = new JLabel("Nama");
        tfNama = new JTextField();  
        lblNoTelp = new JLabel("No Telp");
        tfNoTelp = new JTextField();
        lblJMobil = new JLabel("Jenis Mobil");
        cbJMobil = new JComboBox<>(typesMobil);
        lblJSeat = new JLabel("Jenis Seat");
        cbJSeat = new JComboBox<>(typesSeat);
        lblTSeat = new JLabel("Total Seat");
        tfTSeat = new JTextField();
        tfTSeat.setText("0");  
        lblJPaket = new JLabel("Jenis Paket");
        cbJPaket = new JComboBox<>(typesPaket);        
        btnInsert = new JButton("Insert");
        panel = new JPanel(new GridBagLayout());
        formPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        formConstraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.insets = new Insets(8, 8, 8, 8);
        constraints.insets = new Insets(8, 8, 8, 8);

        btnInsert.addActionListener(actionEvent -> insert());
    }

    private void addLabels() {
        constraints.weightx = 1;
        constraints.gridy = 0;
        panel.add(lblTitle, constraints);
        constraints.gridy = 1;
        formConstraints.weightx = .1;
        formConstraints.gridx = 0;
        formConstraints.gridy = 0;
        formPanel.add(lblNoBooking, formConstraints);
        formConstraints.gridy = 1;
        formPanel.add(lblNama, formConstraints);
        formConstraints.gridy = 2;
        formPanel.add(lblNoTelp, formConstraints);
        formConstraints.gridy = 3;
        formPanel.add(lblJMobil, formConstraints);
        formConstraints.gridy = 4;
        formPanel.add(lblJSeat, formConstraints);
        formConstraints.gridy = 5;
        formPanel.add(lblTSeat, formConstraints);
        formConstraints.gridy = 6;
        formPanel.add(lblJPaket, formConstraints);
        formConstraints.gridy = 7;
        panel.add(formPanel, constraints);
        constraints.gridy = 2;
        panel.add(btnInsert, constraints);
    }

    private void addInputFields() {
        formConstraints.weightx = .9;
        formConstraints.gridy = 0;
        formConstraints.gridx = 1;
        formPanel.add(tfNoBooking, formConstraints);
        formConstraints.gridy = 1;
        formPanel.add(tfNama, formConstraints);
        formConstraints.gridy = 2;
        formPanel.add(tfNoTelp, formConstraints);
        formConstraints.gridy = 3;
        formPanel.add(cbJMobil, formConstraints);
        formConstraints.gridy = 4;
        formPanel.add(cbJSeat, formConstraints);
        formConstraints.gridy = 5;
        formPanel.add(tfTSeat, formConstraints);
        formConstraints.gridy = 6;
        formPanel.add(cbJPaket, formConstraints);
        formConstraints.gridy = 7;
    }

    private int getBiayaPemasangan(String typeJenisMobil){
        try {
            String query = "SELECT * FROM jenis_mobil where name ='"+ typeJenisMobil +"' limit 1";
            ResultSet rs = conn.executeQuery(query);
            int price = 0;
            
            while (rs.next()) {    
                price = rs.getInt("biaya_pemasangan");
            }

           return price;
        } catch (SQLException e) {
        }
        return 0;
    }

private int perhitunganBiaya(String typeJenisSeat){
    try {
                String query = "SELECT * FROM jenis_seat where name ='"+ typeJenisSeat +"' limit 1";
                ResultSet rs = conn.executeQuery(query);
                int price = 0;

                while (rs.next()) { 
                    price = rs.getInt("price_paket");
                   
                }

               return price;
            } catch (SQLException e) {
            }
            return 0;
}

private int perhitunganBiaya(String typeJenisSeat, int jumlahSeat){
    try {
                String query = "SELECT * FROM jenis_seat where name ='"+ typeJenisSeat +"' limit 1";
                ResultSet rs = conn.executeQuery(query);
                int price = 0;

                while (rs.next()) { 
                    price = rs.getInt("price_paket") * jumlahSeat;
                   
                }

               return price;
            } catch (SQLException e) {
            }
            return 0;
}

    private int getBiayaPerbaikanSeat(String typeJenisSeat, String paket, int jumlahSeat){
        int price = 0;
        if(paket == "Paket Mobil") {
               price = perhitunganBiaya(typeJenisSeat);
        }
           if(paket == "Per Seat") {
                price = perhitunganBiaya(typeJenisSeat, jumlahSeat);
        }
       return price;
        
    }

    public String getDetailHarga(String typeJenisSeat){
        String detailHarga = ""; 
            if("Genuine Leather".equals(typeJenisSeat)){
                System.out.println("masuk sini");
                GenuineLeather gn = new GenuineLeather();
                detailHarga = gn.detailharga();
            } else if("Faux Leather".equals(typeJenisSeat)){
                FauxLeather fl = new FauxLeather();
                detailHarga = fl.detailharga();
            } else if("Nilon".equals(typeJenisSeat)){
                Nilon nl = new Nilon();
                detailHarga = nl.detailharga();
            }
        return detailHarga;
    }

   

    private void insert() {
        // Validation Form Start
        int valueSeat = Integer.parseInt(tfTSeat.getText());
        if (tfNoBooking.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Booking Tidak Boleh kosong");
            return;
        } else if (tfNama.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Nama Tidak Boleh kosong");
            return;
        }else if (tfNoTelp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Telepon Tidak Boleh kosong");
            return;
        }else if(valueSeat<1 ){
            JOptionPane.showMessageDialog(null,"Total Seat tidak boleh 0");
            return;
        }
        //Validation Form End

        // insert db start
            String no_booking = tfNoBooking.getText();
            String nama = tfNama.getText();
            String no_telp = tfNoTelp.getText();
            String jenis_mobil = String.valueOf(cbJMobil.getSelectedItem());
            String jenis_seat = String.valueOf(cbJSeat.getSelectedItem());
            String jenis_paket = String.valueOf(cbJPaket.getSelectedItem());
            int jumlah_seat = valueSeat;
            int biaya_ganti_seat = getBiayaPerbaikanSeat(jenis_seat, jenis_paket, jumlah_seat); 
            int biaya_pasang = getBiayaPemasangan(jenis_mobil); 
            int total = biaya_ganti_seat + biaya_pasang;

            conn.executeUpdate("insert into member "
                                + "(no_booking, nama, no_telepon, jenis_mobil, jenis_bahan_seat, jumlah_seat, biaya_ganti_seat, biaya_pasang, total ) "
                                + "values('"+no_booking+"','"+nama+"','"+no_telp+"','"+jenis_mobil+"', '"+jenis_seat+"', '"+ jumlah_seat +"', '"+biaya_ganti_seat+"', '"+biaya_pasang+"', '"+total+"')");
            //insert db End

            String detailHarga = getDetailHarga(jenis_seat);


            //action after insert 
            JOptionPane.showMessageDialog(null,"Success Insert Data!, "+ detailHarga);
            new ListTransaction();
            dispose();
       
        
    }
}