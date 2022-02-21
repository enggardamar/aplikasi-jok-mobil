/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Enggar
 */
public class Nilon extends Seat {
    private final Connect conn = Connect.getInstance();

    @Override
    public String detailharga(){
        int hargaPaket = 0;
        int hargaPerSeat = 0;
        String keunggulan = "kain nilon bisa tahan lama sekaligus sangat keropos karena banyaknya pori-pori.";
            

        try {
            String query = "SELECT * FROM jenis_seat where name ='Nilon' limit 1";
            ResultSet rs = conn.executeQuery(query);
            int price = 0;
            
            while (rs.next()) { 
                 hargaPaket = rs.getInt("price_paket");
                hargaPerSeat = rs.getInt("price_paket");
            }

            return ("Harga unttuk Nilon paket adalah " + hargaPaket + " dan untuk per seat adalah "+ hargaPerSeat);
        } catch (SQLException e) {
        }
        return null;

       
    }
}