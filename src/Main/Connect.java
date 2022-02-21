package Main;

import java.sql.*;  

public class Connect {
	private Statement st;
	private Connection con;
    private static Connect connect = null;

    public static Connect getInstance() {
        if (connect == null) connect = new Connect();
        return connect;
    }

	private  Connect() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/bengkel";
            con = DriverManager.getConnection(url, "root", "");
            st = con.createStatement(1004, 1008);
            System.out.println("Connection Successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
	}

	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
            System.out.println("Connection Error");
		}
		return rs;
	}

	public void executeUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection Error");
		}
	}

    public void close() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}