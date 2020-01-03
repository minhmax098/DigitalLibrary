import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.SocketException;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField textUsername;
	private JTextField textPassword;
	private JButton btnSignUp;
	
	DataHelper dh;
	Client client;
	String pw = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SocketException 
	 */
	public Login() throws SocketException {
		dh = new DataHelper();
		client = new Client();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 656, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		dh = new DataHelper();
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(139, 373, 98, 29);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(139, 415, 98, 29);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Sign in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
		});
		btnLogin.setBounds(256, 458, 81, 25);
		frame.getContentPane().add(btnLogin);
		
		textUsername = new JTextField();
		textUsername.setBounds(256, 373, 190, 29);
		frame.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.addKeyListener(new KeyListener() {
			String s ="";
			int k;
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (k == KeyEvent.VK_BACK_SPACE) {
					   s = s.substring(0, s.length()-1);
					   pw = pw.substring(0, pw.length()-1);
					   textPassword.setText("");
					   textPassword.setText(s);
					}
					else
					{
						pw += textPassword.getText().charAt(textPassword.getText().length()-1);
						s += "*";
						textPassword.setText("");
						textPassword.setText(s);
					}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				k = e.getExtendedKeyCode();
			}
		});
		textPassword.setBounds(256, 416, 190, 29);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					SignUpGUI su = new SignUpGUI(frame);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSignUp.setBounds(360, 458, 86, 25);
		frame.getContentPane().add(btnSignUp);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Asus\\Quyet Chi\\4th year - first semester\\Java\\Digital Library\\DigitalLibrary\\Image\\icon.png"));
		label.setBounds(59, 13, 522, 347);
		frame.getContentPane().add(label);
		frame.setTitle("Digital Library");
	}
	
	private void checkLogin()
	{
		try {
			String query = new String("select * from Users where Username=? and Password_=?");
			PreparedStatement pst = dh.getCnn().prepareStatement(query);
			pst.setString(1, textUsername.getText());
			pst.setString(2, pw);
			ResultSet rs = pst.executeQuery();
			
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			
			if(count == 1)
			{
				//JOptionPane.showMessageDialog(null, "Username and Password are correct!");
				frame.setVisible(false);
				
				ResultSet rs2 = pst.executeQuery();
				rs2.next();
				ReaderGUI ri = new ReaderGUI(rs2.getString(1), frame);
				ri.setVisible(true);
			}
			else
			{
				query = new String("select * from Librians where Username=? and Password_=?");
				pst = dh.getCnn().prepareStatement(query);
				pst.setString(1, textUsername.getText());
				pst.setString(2, pw);
				rs = pst.executeQuery();
				
				count = 0;
				while(rs.next())
				{
					count++;
				}
				
				if(count == 1)
				{
					textUsername.setText("");
					textPassword.setText("");
					frame.setVisible(false);
					
					ResultSet rs2 = pst.executeQuery();
					rs2.next();
					LibrianGUI li = new LibrianGUI(rs2.getString(1), frame);
					li.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Username and Password are not corrcet! Please try again...");
					pw = "";
					textUsername.setText("");
					textPassword.setText("");
				}
			}
			
			rs.close();
			pst.close();
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
