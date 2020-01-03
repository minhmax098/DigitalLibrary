import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

public class BookDetails extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textGenre;
	private JTextField textRating;
	private JTextField textViews;
	private JTextField textRatingTime;
	private JTextField textBorrowedTime;
	private JTextField txtComment;
	private JTextArea textCommentArea;
	private JTextField textRemain;
	@SuppressWarnings("rawtypes")
	private JComboBox cbbRatingPoint;
	
	String id_Book;
	String id_User;
	DataHelper dh;
	FileHelper fh;
	Client client;

	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BookDetails(String id_Book, String id_user) throws SocketException { 
		dh = new DataHelper();
		fh = new FileHelper();
		client = new Client();
		this.id_Book = id_Book;
		this.id_User = id_user;
		this.setTitle("Book Details");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 793);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDetails = new JLabel("Book Details");
		lblDetails.setForeground(Color.ORANGE);
		lblDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDetails.setBounds(370, 13, 162, 28);
		contentPane.add(lblDetails);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(63, 112, 103, 16);
		contentPane.add(lblAuthor);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(63, 153, 103, 16);
		contentPane.add(lblGenre);
		
		JLabel lblRating = new JLabel("Rating");
		lblRating.setBounds(63, 196, 103, 16);
		contentPane.add(lblRating);
		
		JLabel lblViews = new JLabel("Views");
		lblViews.setBounds(63, 237, 103, 16);
		contentPane.add(lblViews);
		
		JLabel lblRatingTime = new JLabel("Rating time");
		lblRatingTime.setBounds(329, 196, 71, 16);
		contentPane.add(lblRatingTime);
		
		JLabel lblBorrowedTime = new JLabel("Borrowed time");
		lblBorrowedTime.setBounds(63, 277, 103, 16);
		contentPane.add(lblBorrowedTime);
		
		textTitle = new JTextField();
		textTitle.setBounds(203, 68, 585, 22);
		contentPane.add(textTitle);
		textTitle.setColumns(10);
		
		textAuthor = new JTextField();
		textAuthor.setColumns(10);
		textAuthor.setBounds(203, 109, 585, 22);
		contentPane.add(textAuthor);
		
		textGenre = new JTextField();
		textGenre.setColumns(10);
		textGenre.setBounds(203, 150, 292, 22);
		contentPane.add(textGenre);
		
		textRating = new JTextField();
		textRating.setColumns(10);
		textRating.setBounds(203, 193, 71, 22);
		contentPane.add(textRating);
		
		textViews = new JTextField();
		textViews.setColumns(10);
		textViews.setBounds(203, 234, 71, 22);
		contentPane.add(textViews);
		
		textRatingTime = new JTextField();
		textRatingTime.setColumns(10);
		textRatingTime.setBounds(424, 193, 71, 22);
		contentPane.add(textRatingTime);
		
		textBorrowedTime = new JTextField();
		textBorrowedTime.setColumns(10);
		textBorrowedTime.setBounds(203, 274, 71, 22);
		contentPane.add(textBorrowedTime);
		
		JLabel lblComments = new JLabel("Comments");
		lblComments.setForeground(Color.CYAN);
		lblComments.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblComments.setBounds(63, 389, 103, 33);
		contentPane.add(lblComments);
		
		txtComment = new JTextField();
		txtComment.setBounds(63, 697, 598, 22);
		contentPane.add(txtComment);
		txtComment.setColumns(10);
		
		JButton btnTableOf = new JButton("Table of contents");
		btnTableOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTableOfContent();
			}
		});
		btnTableOf.setBounds(63, 349, 131, 25);
		contentPane.add(btnTableOf);
		
		JButton btnBorrow = new JButton("Borrow");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					borrow();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrow.setBounds(398, 349, 97, 25);
		contentPane.add(btnBorrow);
		
		JButton btnRate = new JButton("Rate");
		btnRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rateBook();
			}
		});
		btnRate.setBounds(685, 349, 97, 25);
		contentPane.add(btnRate);
		
		JLabel lblRateThisBook = new JLabel("Rate this book");
		lblRateThisBook.setBounds(582, 314, 206, 19);
		contentPane.add(lblRateThisBook);
		
		JLabel lblBorrowThisBook = new JLabel("Borrow this book");
		lblBorrowThisBook.setBounds(398, 311, 97, 28);
		contentPane.add(lblBorrowThisBook);
		
		JButton btnComment = new JButton("Comment");
		btnComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleNewComment();
			}
		});
		btnComment.setBounds(673, 697, 115, 22);
		contentPane.add(btnComment);
		
		JLabel lblRemain = new JLabel("Remain");
		lblRemain.setBounds(63, 315, 103, 16);
		contentPane.add(lblRemain);
		
		textRemain = new JTextField();
		textRemain.setBounds(203, 309, 71, 22);
		contentPane.add(textRemain);
		textRemain.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 435, 724, 250);
		contentPane.add(scrollPane);
		
		textCommentArea = new JTextArea(60,60);
		scrollPane.setViewportView(textCommentArea);
		textCommentArea.setEditable(false);
		textCommentArea.setLineWrap(true);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(793, 13, 97, 25);
		contentPane.add(btnClose);
		
		String[] point = {"1","2","3","4","5","6","7","8","9","10"};
		cbbRatingPoint = new JComboBox(point);
		cbbRatingPoint.setBounds(582, 350, 79, 22);
		contentPane.add(cbbRatingPoint);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(28, 46, 779, 343);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(35, 24, 103, 16);
		panel.add(lblTitle);
		
		loadData();
		this.setVisible(true);
	}
	
	private void loadData() {
		try {
			String query = new String("Select *  from Books where ID_Book = " + id_Book);
			PreparedStatement pst = dh.getCnn().prepareStatement(query);		
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				textTitle.setText(rs.getString(2));
				textAuthor.setText(rs.getString(3));
				textGenre.setText(rs.getString(4));
				textRating.setText(String.valueOf(rs.getFloat(5)));
				textRatingTime.setText(String.valueOf(rs.getInt(6)));
				textViews.setText(String.valueOf(rs.getInt(7)));	
				textBorrowedTime.setText(String.valueOf(rs.getInt(8)));
				textRemain.setText(String.valueOf(rs.getInt(11)));
			}
			
			query = "Select Comment_link from Books where ID_Book = " + id_Book;
			pst = dh.getCnn().prepareStatement(query);
			rs = pst.executeQuery();
			rs.next();
			FileHelper fh = new FileHelper();
			fh.fileToTextArea(rs.getString(1), textCommentArea);
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void openTableOfContent()
	{
		try {
			String query = new String("Select Sumary_link from Books where ID_Book = " + id_Book);
			PreparedStatement pst = dh.getCnn().prepareStatement(query);		
			ResultSet rs = pst.executeQuery();
			rs.next();
			
			fh.openPDF(rs.getString(1));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void handleNewComment()
	{
		try {
			String cmt = txtComment.getText();
			String userName = null;
			String query = "Select Username from Users where ID_User = " + id_User;
			PreparedStatement pst = dh.getCnn().prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			rs.next();
			userName = rs.getString(1);
			
			query = "Select Comment_link from Books where ID_Book = " + id_Book;
			pst = dh.getCnn().prepareStatement(query);
			rs = pst.executeQuery();
			rs.next();
			
			FileHelper fh = new FileHelper();			
			fh.appendToFile(rs.getString(1), userName + ": " + cmt);
			
			fh.fileToTextArea(rs.getString(1), textCommentArea);
			
			txtComment.setText("");
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void borrow() throws IOException {
		Calendar d = Calendar.getInstance();
		try {
			String query = "Select Reserved from Users where ID_user = " + id_User;
			ResultSet rs = dh.getCnn().prepareStatement(query).executeQuery();
			rs.next();
			String reservedBook = rs.getString(1);
			if(reservedBook == null)			
			{
				query = "insert into Reserves values ('"
						+ id_User + "','" 
						+ id_Book + "','" 
						+ new Date(d.get(Calendar.YEAR)-1900, d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH)) + "')";
				System.out.println(query);
				client.sendData(query);
				
				query = "select Remain from Books where ID_Book = " + id_Book;
				rs = dh.getCnn().prepareStatement(query).executeQuery();
				rs.next();
				int num = rs.getInt(1) - 1;

				if(num>=0)
				{
					query = "Update Books set Remain = " + num + " where ID_Book = " + id_Book;
					client.sendData(query);
					
					query = "Select Title from Books where ID_Book = " + id_Book;
					rs = dh.getCnn().prepareStatement(query).executeQuery();
					rs.next();
					
					query = "Update Users set Reserved = '" + rs.getString(1) + "' where ID_User = " + id_User;
					client.sendData(query);
					
					JOptionPane.showMessageDialog(null, "Success! Please go to the library and get your book in 3 day");			
				}
				else {
					JOptionPane.showMessageDialog(null, "Sorry! This book is out of order");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You are already reserved another book! Please cancel previous order first");
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void rateBook()
	{
		try {
			String query = "Select Rating_time from Books where ID_Book = " + id_Book;
			ResultSet rs = dh.getCnn().prepareStatement(query).executeQuery();
			rs.next();
			int rt = rs.getInt(1);
			
			query = "Select Rating from Books where ID_Book = " + id_Book;
			rs = dh.getCnn().prepareStatement(query).executeQuery();
			rs.next();
			float rating = rs.getFloat(1);
			
			rating = (rating*rt + Integer.valueOf(cbbRatingPoint.getSelectedItem().toString()))/(rt+1);
			
			query = "Update Books set Rating = " + rating + " where ID_Book = " + id_Book;
			dh.getCnn().prepareStatement(query).executeUpdate();
			
			query = "Update Books set Rating_time = " + (rt+1) + " where ID_Book = " + id_Book;
			dh.getCnn().prepareStatement(query).executeUpdate();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
