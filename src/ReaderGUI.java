import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class ReaderGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JFrame frame;
	private JPanel contentPane;
	private JTable tbListBooks;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbSort;
	
	String id_User;
	Connection cnn;
	private JTextField txtSearch;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReaderGUI(String id, JFrame parent) {
		cnn = DataHelper.getConnection();
		this.id_User = id;
		this.frame = parent;
		this.setTitle("Digital Library");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 918, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 186, 876, 371);
		contentPane.add(scrollPane);
		
		tbListBooks = new JTable();
		tbListBooks.setBackground(Color.WHITE);
		scrollPane.setViewportView(tbListBooks);
		
		JButton btnSignOut = new JButton("Sign out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				parent.setVisible(true);
			}
		});
		btnSignOut.setBounds(791, 13, 97, 25);
		contentPane.add(btnSignOut);
		
		String[] condition = {"Title", "Rating ascending", "Rating descending", "Borrowed ascending", "Borrowed descending"};
		
		JButton btnProfile = new JButton("Profile");
		btnProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try {
					Profile p;
					p = new Profile(id_User);
					p.setVisible(true);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnProfile.setBounds(682, 13, 97, 25);
		contentPane.add(btnProfile);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(37, 51, 812, 122);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(12, 13, 474, 96);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSortBy = new JButton("Sort by");
		btnSortBy.setBounds(342, 60, 114, 25);
		panel_1.add(btnSortBy);
		cbbSort = new JComboBox(condition);
		cbbSort.setBounds(12, 61, 318, 22);
		panel_1.add(cbbSort);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(12, 13, 318, 24);
		panel_1.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearchBy = new JButton("Search by");
		btnSearchBy.setBounds(342, 13, 114, 25);
		panel_1.add(btnSearchBy);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(492, 13, 308, 96);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.setBackground(Color.LIGHT_GRAY);
		btnDetails.setBounds(12, 13, 284, 70);
		panel_2.add(btnDetails);
		btnDetails.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				try {
					String id = null;
					int row = tbListBooks.getSelectedRow();
					if(row == -1)
					{
						JOptionPane.showMessageDialog(null, "Please choose an object!");
					}
					else
					{
						id = tbListBooks.getModel().getValueAt(row, 0).toString();
						String query = new String("Select View_time  from Books where ID_Book="+id);
						PreparedStatement pst = cnn.prepareStatement(query);		
						ResultSet rs = pst.executeQuery();
						rs.next();
						int num = rs.getInt(1);
						num++;
						query = "update Books set View_time = " + num + " where ID_Book = " + id;
						cnn.prepareStatement(query).executeUpdate();
						
						pst.close();
						rs.close();
						
						BookDetails bd = new BookDetails(id, id_User);
					}
						
				}catch(Exception a)
				{
					a.printStackTrace();
				}	
			}
		});
		btnSearchBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search(txtSearch.getText()); 
			}
		});
		btnSortBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sort(cbbSort.getSelectedItem().toString());
			}
		});
		
		loadListBooks();
	}
	
	public void loadListBooks()
	{
		try {
			String query = new String("Select ID_Book, Title,  Author, Genre, Rating, Borrowed_time, Remain  from Books");
			PreparedStatement pst = cnn.prepareStatement(query);		
			ResultSet rs = pst.executeQuery();
			tbListBooks.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Search(String para)
	{
		try {
			para = "%" + para + "%";
			
			String query = new String("Select ID_Book, Title,  Author, Genre, Rating, Borrowed_time, Remain  from Books where"
					+ " Title like ? or Author like ? or Genre like ?");
			PreparedStatement pst = cnn.prepareStatement(query);	

			pst.setString(1, para);
			pst.setString(2, para);
			pst.setString(3, para);
			
			ResultSet rs = pst.executeQuery();
			tbListBooks.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sort(String condition)
	{
		String query = null;
		switch(condition)
		{
			case "Title":
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Title ASC";
				break;
			case "Rating ascending":
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Rating ASC";
				break;
			case "Rating descending":
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Rating DESC";
				break;
			case "Borrowed ascending":
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Borrowed_time ASC";
				break;
			case "Borrowed descending":
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Borrowed_time DESC";
				break;
			default:
				query = "Select ID_Book, Title, Author, Genre, Rating, Borrowed_time, Remain from Books order by Rating ASC";
				break;
		}
		try {
			PreparedStatement pst = cnn.prepareStatement(query);				
			ResultSet rs = pst.executeQuery();
			tbListBooks.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
