import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class SignUpGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textIdentityNumber;
	private JTextField textUsername;
	private JTextField textPassword;
	private JTextField textVerifyPassword;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbAddress;
	private JDateChooser dateChooserBirthday;
	
	DataHelper dh;
	Client client;
	
	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SignUpGUI(JFrame parent) throws SocketException {
		this.setVisible(true);
		this.frame = parent;
		this.setTitle("Sign up");
		dh = new DataHelper();
		client = new Client();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewAccount = new JLabel("New Account");
		lblNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewAccount.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewAccount.setBounds(73, 13, 371, 45);
		contentPane.add(lblNewAccount);
		
		JLabel lblName = new JLabel("Full name");
		lblName.setBounds(73, 85, 99, 23);
		contentPane.add(lblName);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(73, 121, 99, 23);
		contentPane.add(lblBirthday);
		
		JLabel lblIdentiyNumber = new JLabel("Identiy number");
		lblIdentiyNumber.setBounds(73, 168, 99, 23);
		contentPane.add(lblIdentiyNumber);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(73, 204, 99, 23);
		contentPane.add(lblAddress);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 242, 99, 23);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 278, 99, 23);
		contentPane.add(lblPassword);
		
		JLabel lblVerifyPassword = new JLabel("Verify password");
		lblVerifyPassword.setBounds(73, 314, 99, 23);
		contentPane.add(lblVerifyPassword);
		
		textName = new JTextField();
		textName.setBounds(190, 85, 254, 23);
		contentPane.add(textName);
		textName.setColumns(10);
		
		textIdentityNumber = new JTextField();
		textIdentityNumber.setColumns(10);
		textIdentityNumber.setBounds(190, 168, 254, 23);
		contentPane.add(textIdentityNumber);
		
		textUsername = new JTextField();
		textUsername.setColumns(10);
		textUsername.setBounds(190, 242, 254, 23);
		contentPane.add(textUsername);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		textPassword.setBounds(190, 278, 254, 23);
		contentPane.add(textPassword);
		
		textVerifyPassword = new JTextField();
		textVerifyPassword.setColumns(10);
		textVerifyPassword.setBounds(190, 314, 254, 23);
		contentPane.add(textVerifyPassword);
		
		dateChooserBirthday = new JDateChooser();
		dateChooserBirthday.setBounds(190, 122, 254, 22);
		contentPane.add(dateChooserBirthday);
		
		String[] location = {"Quang Nam", "Da Nang", "Hue", "Ha Noi", "TP Ho Chi Minh"};
		cbbAddress = new JComboBox(location);
		cbbAddress.setBounds(190, 204, 254, 22);
		contentPane.add(cbbAddress);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUser();
				dispose();
				frame.setVisible(true);
			}
		});
		btnSubmit.setBounds(190, 375, 84, 25);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				frame.setVisible(true);
			}
		});
		btnCancel.setBounds(371, 375, 73, 25);
		contentPane.add(btnCancel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				frame.setVisible(true);
			}
		});
		btnLogin.setBounds(286, 375, 73, 25);
		contentPane.add(btnLogin);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(51, 63, 423, 353);
		contentPane.add(panel);
		panel.setLayout(null);
	}
	
	private void addUser()
	{
		try {
			
			if(textName.getText().equals("") || textIdentityNumber.getText().equals("") || textUsername.getText().equals("") || textPassword.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please fullfill all information!");
				return;
			}
			
			if(textVerifyPassword.getText().equals(textPassword.getText()) == false)
			{
				JOptionPane.showMessageDialog(null, "Verify password is not correct! ");
				return;
			}
			
			String query = "select Username from Users";
			PreparedStatement pst = dh.getCnn().prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				if(rs.getString(1).equals(textUsername.getText()))
				{
					JOptionPane.showMessageDialog(null, "Username already exists! ");
					return;
				}
			}
			
			query = "select max(ID_User) from Users";
			
			pst = dh.getCnn().prepareStatement(query);
			rs = pst.executeQuery();
			rs.next();
			
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			
			FileHelper fh = new FileHelper();
			String fileName = textUsername.getText()+".txt";
			System.out.println(fileName);
			if(!fh.createUserFile(fileName))
			{
				JOptionPane.showMessageDialog(null, "Fail to create user's file");
				return;
			}
			
			query = new String("insert into Users values ('"
					+ (rs.getInt(1)+1) + "','"
					+ textName.getText() +"','"
					+ new String(df.format(dateChooserBirthday.getDate())) + "','"
					+ textIdentityNumber.getText() + "','"
					+ cbbAddress.getSelectedItem().toString() + "',"
					+ 100 + ",'"
					+ fh.getUserPath() + "','"
					+ textPassword.getText() + "','"
					+ textUsername.getText() + "', null, null)");
			
			client.sendData(query);
			JOptionPane.showMessageDialog(null, "Success!");
			pst.close();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
