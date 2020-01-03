import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class UpdateBook extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtSummary;
	private JTextField txtAuthor;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JTextField txtAdd;
	
	DataHelper dh;
	Client client;
	String id_book;

	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UpdateBook(String id) throws SocketException {
		dh = new DataHelper();
		client = new Client();
		id_book = id;
		
		this.setTitle("Update Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(76, 74, 87, 16);
		contentPane.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(76, 118, 69, 16);
		contentPane.add(lblAuthor);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(76, 162, 87, 16);
		contentPane.add(lblGenre);
		
		JLabel lblSumaryFile = new JLabel("Summary file");
		lblSumaryFile.setBounds(76, 211, 87, 16);
		contentPane.add(lblSumaryFile);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(186, 71, 310, 22);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		txtSummary = new JTextField();
		txtSummary.setBounds(186, 208, 204, 22);
		contentPane.add(txtSummary);
		txtSummary.setColumns(10);
		
		txtAuthor = new JTextField();
		txtAuthor.setBounds(186, 115, 310, 22);
		contentPane.add(txtAuthor);
		txtAuthor.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(402, 316, 97, 25);
		contentPane.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
				JOptionPane.showMessageDialog(null, "Updated successfully!");
				dispose();
			}
		});
		btnOk.setBounds(186, 316, 97, 25);
		contentPane.add(btnOk);
		
		String[] genres = {"Science fiction", "Drama", "Action and Adventure", "Romance", "Horror", "History", "Information Technology"};
		comboBox = new JComboBox(genres);
		comboBox.setBounds(186, 159, 310, 22);
		contentPane.add(comboBox);
		
		JLabel lblAdd = new JLabel("Add");
		lblAdd.setBounds(76, 261, 87, 16);
		contentPane.add(lblAdd);
		
		txtAdd = new JTextField();
		txtAdd.setBounds(186, 258, 116, 22);
		contentPane.add(txtAdd);
		txtAdd.setColumns(10);
		
		JButton btnSelect = new JButton("select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPDFfile();
			}
		});
		btnSelect.setBounds(402, 207, 97, 25);
		contentPane.add(btnSelect);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(41, 52, 501, 303);
		contentPane.add(panel);
		
		loadData();
	}
	
	void loadData()
	{
		try {
			Statement stm = dh.getCnn().createStatement();
			ResultSet rs = stm.executeQuery("select * from Books where ID_Book = " + id_book);
			rs.next();
			
			txtTitle.setText(rs.getString(2));
			txtAuthor.setText(rs.getString(3));
			comboBox.setSelectedItem(rs.getString(4));
			txtSummary.setText(rs.getString(9));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void Update()
	{
		try {
			Statement stm = dh.getCnn().createStatement();
			ResultSet rs = stm.executeQuery("select Remain from Books where ID_Book = " + id_book);
			rs.next();
			
			String query = new String("update Books set Title = '" 
			+ txtTitle.getText() +"', Author = '"
			+ txtAuthor.getText() + "', Genre = '"
			+ comboBox.getSelectedItem().toString() + "', Sumary_link = '" 
			+ txtSummary.getText() +"', Remain = "
			+ (rs.getInt(1) + Integer.parseInt(txtAdd.getText()) ) 
			+ " where ID_Book = " + id_book);

			client.sendData(query);
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
			txtSummary.setText(fileName);
		}
	}
}
