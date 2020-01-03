import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;

public class AddBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textNumOfBook;
	private JTextField textSummary;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbGenre; 
	
	DataHelper dh;
	Client client;
	
	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddBook() throws SocketException {
		dh = new DataHelper();
		client = new Client();
		this.setTitle("Add new book");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 623, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddNewBook = new JLabel("Add New Book");
		lblAddNewBook.setForeground(Color.ORANGE);
		lblAddNewBook.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAddNewBook.setBounds(220, 13, 174, 62);
		contentPane.add(lblAddNewBook);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(71, 99, 56, 16);
		contentPane.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(71, 134, 56, 16);
		contentPane.add(lblAuthor);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(71, 163, 56, 16);
		contentPane.add(lblGenre);
		
		JLabel lblNumberOfBooks = new JLabel("Number of book");
		lblNumberOfBooks.setBounds(71, 192, 118, 16);
		contentPane.add(lblNumberOfBooks);
		
		JLabel lblSummaryFile = new JLabel("Summary file");
		lblSummaryFile.setBounds(71, 221, 118, 16);
		contentPane.add(lblSummaryFile);
		
		textTitle = new JTextField();
		textTitle.setBounds(220, 96, 273, 22);
		contentPane.add(textTitle);
		textTitle.setColumns(10);
		
		textAuthor = new JTextField();
		textAuthor.setBounds(220, 131, 273, 22);
		contentPane.add(textAuthor);
		textAuthor.setColumns(10);
		
		textNumOfBook = new JTextField();
		textNumOfBook.setBounds(220, 189, 273, 22);
		contentPane.add(textNumOfBook);
		textNumOfBook.setColumns(10);
		
		textSummary = new JTextField();
		textSummary.setBounds(220, 218, 174, 22);
		contentPane.add(textSummary);
		textSummary.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBook();
				dispose();
			}
		});
		btnSubmit.setBounds(220, 278, 97, 25);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();			
			}
		});
		btnCancel.setBounds(396, 278, 97, 25);
		contentPane.add(btnCancel);
		
		String[] genre = {"Science fiction", "Drama", "Action and Adventure", "Romance", "Horror", "History", "Information Technology"};
		cbbGenre = new JComboBox(genre);
		cbbGenre.setBounds(220, 160, 273, 22);
		contentPane.add(cbbGenre);
		
		JButton btnSelect = new JButton("select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPDFfile();
			}
		});
		btnSelect.setBounds(406, 217, 88, 25);
		contentPane.add(btnSelect);		
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(34, 65, 542, 258);
		contentPane.add(panel);
		panel.setLayout(null);
	}
	
	public void addBook()
	{
		try {
			Statement stm = dh.getCnn().createStatement();
			ResultSet rs = stm.executeQuery("select max(ID_Book) from Books");
			rs.next();
			
			FileHelper fh = new FileHelper();
			String fileName = textTitle.getText() + "Comment.txt";
			if(!fh.createCommentFile(fileName))
			{
				JOptionPane.showMessageDialog(null, "Fail to create comment file");
				return;
			}
			
			String query = new String("insert into Books values ('" 
			+ (rs.getInt(1)+1) +"','"
			+ textTitle.getText() + "','"
			+ textAuthor.getText() +"','"
			+ cbbGenre.getSelectedItem().toString() + "','" 
			+ 0 + "','" 
			+ 0 + "','"
			+ 0 + "','"
			+ 0 + "','"
			+ textSummary.getText() +"','"
			+ fh.getCommentPath() + fileName + "','"
			+ textNumOfBook.getText() + "')");

			client.sendData(query);
			JOptionPane.showMessageDialog(null, "Added!");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void selectPDFfile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Chooser summary file");
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			String fileName = chooser.getSelectedFile().getAbsolutePath(); 
			textSummary.setText(fileName);
		}
	}
}
