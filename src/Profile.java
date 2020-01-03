import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.BevelBorder;

public class Profile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtIdentityNumber;
	private JTextField txtFSPoint;
	private JTextField txtBorrowing;
	private JTextField txtReserved;
	private JDateChooser dateChooser;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbAddress;
	
	String id_User;
	DataHelper dh;
	Client client;
	
	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Profile(String id) throws SocketException {
		this.id_User = id;
		dh = new DataHelper();
		client = new Client();
		this.setTitle("Profile");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 523);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setForeground(Color.RED);
		lblProfile.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfile.setBounds(161, 0, 301, 38);
		contentPane.add(lblProfile);
		
		String[] location = {"Quang Nam", "Da Nang", "Hue", "Ha Noi", "TP Ho Chi Minh"};
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(48, 51, 505, 385);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(12, 27, 114, 16);
		panel.add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(12, 65, 114, 16);
		panel.add(lblName);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(12, 107, 114, 16);
		panel.add(lblBirthday);
		
		JLabel lblIdentityNumber = new JLabel("Identity Number");
		lblIdentityNumber.setBounds(12, 150, 114, 16);
		panel.add(lblIdentityNumber);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(12, 190, 114, 16);
		panel.add(lblAddress);
		
		JLabel lblFailSafetyPoint = new JLabel("Fail safety point");
		lblFailSafetyPoint.setBounds(12, 228, 114, 16);
		panel.add(lblFailSafetyPoint);
		
		JLabel lblBorrowing = new JLabel("Borrowing");
		lblBorrowing.setBounds(12, 268, 114, 16);
		panel.add(lblBorrowing);
		
		JLabel lblReserved = new JLabel("Reserved");
		lblReserved.setBounds(12, 308, 114, 16);
		panel.add(lblReserved);
		
		txtID = new JTextField();
		txtID.setBounds(162, 24, 323, 22);
		panel.add(txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(162, 62, 323, 22);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtIdentityNumber = new JTextField();
		txtIdentityNumber.setBounds(162, 147, 323, 22);
		panel.add(txtIdentityNumber);
		txtIdentityNumber.setColumns(10);
		
		txtFSPoint = new JTextField();
		txtFSPoint.setBounds(162, 225, 323, 22);
		panel.add(txtFSPoint);
		txtFSPoint.setColumns(10);
		
		txtBorrowing = new JTextField();
		txtBorrowing.setBounds(162, 265, 323, 22);
		panel.add(txtBorrowing);
		txtBorrowing.setColumns(10);
		
		txtReserved = new JTextField();
		txtReserved.setBounds(162, 305, 224, 22);
		panel.add(txtReserved);
		txtReserved.setColumns(10);
		
		JButton btnCancel = new JButton("remove");
		btnCancel.setBounds(396, 304, 89, 25);
		panel.add(btnCancel);
		
		JButton btnUpdateProfile = new JButton("Update");
		btnUpdateProfile.setBounds(162, 346, 97, 25);
		panel.add(btnUpdateProfile);
		cbbAddress = new JComboBox(location);
		cbbAddress.setBounds(162, 187, 323, 22);
		panel.add(cbbAddress);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(162, 107, 323, 22);
		panel.add(dateChooser);
		
		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel_1.setBounds(388, 346, 97, 25);
		panel.add(btnCancel_1);
		btnUpdateProfile.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				try {
					UpdateProfile up;
					up = new UpdateProfile(id_User);
					up.setVisible(true);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		
		loadData();
	}
	
	private void loadData()
	{
		try {
			String query = new String("Select *  from Users where ID_user = " + id_User);	
			ResultSet rs = dh.getCnn().prepareStatement(query).executeQuery();
			if(rs.next())
			{
				txtID.setText(rs.getString(1));
				txtName.setText(rs.getString(2));
				dateChooser.setDate(rs.getDate(3));
				txtIdentityNumber.setText(rs.getString(4));
				cbbAddress.setSelectedItem(rs.getString(5));
				txtFSPoint.setText(String.valueOf(rs.getInt(6)));
				txtReserved.setText(rs.getString(10));
				txtBorrowing.setText(rs.getString(11));
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * set reserved from Users table to null
	 * increase Remain of the book in Books table by 1
	 * delete reserve from Reserves table
	 */
	private void cancel()
	{
		try {
			String id_book = null;
			String query = "Update Users set Reserved = null where ID_User = " + id_User;
			client.sendData(query);
			
			
			query = "select ID_Book from Books where Title = '" + txtReserved.getText() + "'";
			PreparedStatement pst = dh.getCnn().prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			if(!rs.next())
			{
				JOptionPane.showMessageDialog(null, "There is nothing to cancel!");
				return;
			}
			id_book = rs.getString(1);

			query = "select Remain from Books where ID_Book = " + id_book;
			pst = dh.getCnn().prepareStatement(query);
			rs = pst.executeQuery();
			rs.next();
			
			query = "Update Books set Remain = "+ (rs.getInt(1)+1) +" where ID_Book = " + id_book;
			client.sendData(query);
			
			query = "delete from Reserves where ID_Book = "+ id_book +" and ID_User = " + id_User;
			client.sendData(query);
			
			txtReserved.setText("");
			rs.close();
			pst.close();
			JOptionPane.showMessageDialog(null, "Cancel Successfully!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
