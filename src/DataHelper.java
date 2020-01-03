import java.sql.*;

public class DataHelper {
	Connection cnn=null;
	
	public DataHelper()
	{
		try {
			cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DigitalLibrary;IntegratedSecurity=True");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection()
	{
		try {
			//Class.forName("com.sqlserver.jbdc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DigitalLibrary;IntegratedSecurity=True");
			//JOptionPane.showMessageDialog(null, "Connection Successful!");
			return conn;

		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateDB(String query)
	{
		try {
			PreparedStatement pst = cnn.prepareStatement(query);		
			pst.executeUpdate();
			pst.close();
			System.out.println("Updated Successful!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Connection getCnn() {
		return cnn;
	}

	public void setCnn(Connection cnn) {
		this.cnn = cnn;
	}
	
}
