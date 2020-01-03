import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

public class UpdateProfile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNewPW;
	private JTextField txtOldPW;
	private JTextField txtUsername;
	private JTextField txtName;
	private JTextField txtConfirmNewPW;
	private JDateChooser dateChooser;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbAddress;
	
	String id_User;
	DataHelper dh;
	Client client;
	
	String oldPW = "", newPW = "", confirmPW = ""; 
	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UpdateProfile(String id_user) throws SocketException {
		this.id_User = id_user;
		client = new Client();
		dh = new DataHelper();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(114, 83, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(114, 129, 56, 16);
		contentPane.add(lblBirthday);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(114, 178, 56, 16);
		contentPane.add(lblAddress);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(114, 224, 111, 16);
		contentPane.add(lblUsername);
		
		JLabel lblOldPassword = new JLabel("Old password");
		lblOldPassword.setBounds(114, 269, 111, 16);
		contentPane.add(lblOldPassword);
		
		JLabel lblNewPassword = new JLabel("New password");
		lblNewPassword.setBounds(114, 308, 111, 16);
		contentPane.add(lblNewPassword);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				dispose();
			}
		});
		btnOk.setBounds(245, 394, 97, 25);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(418, 394, 97, 25);
		contentPane.add(btnCancel);
		
		txtNewPW = new JTextField();
		txtNewPW.setBounds(245, 305, 270, 22);
		contentPane.add(txtNewPW);
		txtNewPW.setColumns(10);
		txtNewPW.addKeyListener(new KeyListener() {
			String s ="";
			int k;
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (k == KeyEvent.VK_BACK_SPACE) {
					   s = s.substring(0, s.length()-1);
					   newPW = newPW.substring(0, newPW.length()-1);
					   txtNewPW.setText("");
					   txtNewPW.setText(s);
					}
					else
					{
						newPW += txtNewPW.getText().charAt(txtNewPW.getText().length()-1);
						s += "*";
						txtNewPW.setText("");
						txtNewPW.setText(s);
					}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				k = e.getExtendedKeyCode();
			}
		});
		
		txtOldPW = new JTextField();
		txtOldPW.setBounds(245, 266, 270, 22);
		contentPane.add(txtOldPW);
		txtOldPW.setColumns(10);
		txtOldPW.addKeyListener(new KeyListener() {			
			String s ="";
			int k;
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (k == KeyEvent.VK_BACK_SPACE) {
					   s = s.substring(0, s.length()-1);
					   oldPW = oldPW.substring(0, oldPW.length()-1);
					   txtOldPW.setText("");
					   txtOldPW.setText(s);
					}
					else
					{
						oldPW += txtOldPW.getText().charAt(txtOldPW.getText().length()-1);
						s += "*";
						txtOldPW.setText("");
						txtOldPW.setText(s);
					}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				k = e.getExtendedKeyCode();
			}
		});
		
		txtUsername = new JTextField();
		txtUsername.setBounds(245, 221, 270, 22);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(245, 80, 270, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblUpdateProfile = new JLabel("Update Profile");
		lblUpdateProfile.setForeground(Color.RED);
		lblUpdateProfile.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblUpdateProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateProfile.setBounds(82, 13, 497, 50);
		contentPane.add(lblUpdateProfile);
		
		JLabel lblCofirmNewPassword = new JLabel("Cofirm new password");
		lblCofirmNewPassword.setBounds(114, 351, 125, 16);
		contentPane.add(lblCofirmNewPassword);
		
		txtConfirmNewPW = new JTextField();
		txtConfirmNewPW.setBounds(245, 348, 270, 22);
		contentPane.add(txtConfirmNewPW);
		txtConfirmNewPW.setColumns(10);
		txtConfirmNewPW.addKeyListener(new KeyListener() {
			String s ="";
			int k;
			
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (k == KeyEvent.VK_BACK_SPACE) {
					   s = s.substring(0, s.length()-1);
					   confirmPW = confirmPW.substring(0, confirmPW.length()-1);
					   txtConfirmNewPW.setText("");
					   txtConfirmNewPW.setText(s);
					}
					else
					{
						confirmPW += txtConfirmNewPW.getText().charAt(txtConfirmNewPW.getText().length()-1);
						s += "*";
						txtConfirmNewPW.setText("");
						txtConfirmNewPW.setText(s);
					}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				k = e.getExtendedKeyCode();
			}
		});
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(245, 129, 270, 22);
		contentPane.add(dateChooser);
		
		String[] location = {"Quang Nam", "Da Nang", "Hue", "Ha Noi", "TP Ho Chi Minh"};
		cbbAddress = new JComboBox(location);
		cbbAddress.setBounds(245, 175, 270, 22);
		contentPane.add(cbbAddress);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(82, 70, 497, 372);
		contentPane.add(panel);
		
		loadData();
	}

	private void loadData()
	{
		try {
			String query = new String("Select *  from Users where ID_user = " + id_User);	
			ResultSet rs = dh.getCnn().prepareStatement(query).executeQuery();
			if(rs.next())
			{
				txtName.setText(rs.getString(2));
				dateChooser.setDate(rs.getDate(3));
				cbbAddress.setSelectedItem(rs.getString(5));
				txtUsername.setText(rs.getString(9));
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void update()
	{
		try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String query = "Update Users set Name = '"
						+ txtName.getText() + "', Birthday = '"
						+ new String(sdf.format(dateChooser.getDate())) + "', Address = '"
						+ cbbAddress.getSelectedItem().toString() +"', Username = '"
						+ txtUsername.getText() + "'"; 
				if(newPW != "" && oldPW != "")
				{
					PreparedStatement pst = dh.getCnn().prepareStatement("Select Password_ from Users where ID_user = " + id_User);
					ResultSet rs = pst.executeQuery();
					rs.next();
					if(rs.getString(1).equals(oldPW) && newPW.equals(confirmPW))
					{
						query += ", Password_ = '" + newPW + "'";
					}
					else if(!rs.getString(1).equals(oldPW))
					{
						JOptionPane.showMessageDialog(null, "Password is not correct!");
						return;
					}
					else if(!newPW.equals(confirmPW))
					{
						JOptionPane.showMessageDialog(null, "Confirm password is not correct!");
						return;
					}
					
				}
				
				query += " where ID_user = " + id_User;				

				dh.updateDB(query);
				JOptionPane.showMessageDialog(null, "Updated Successfully!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
