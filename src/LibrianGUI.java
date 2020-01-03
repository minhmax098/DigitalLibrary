import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class LibrianGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	JFrame parent;
	String id_Librian;
	Client client;
	DataHelper dh;
	
	SimpleDateFormat formatter;
	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	public LibrianGUI(String id, JFrame parent) throws SocketException {
		this.id_Librian = id;
		this.parent = parent;
		dh = new DataHelper();
		client = new Client();
		formatter = new SimpleDateFormat("MM-dd-yyyy");
		
		this.setTitle("Librian");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 285, 505, 282);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("exit");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
		btnClose.setBounds(420, 13, 97, 25);
		contentPane.add(btnClose);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(12, 60, 505, 193);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(12, 42, 206, 129);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnViewListBooks = new JButton("View list books");
		btnViewListBooks.setBounds(12, 13, 184, 25);
		panel_1.add(btnViewListBooks);
		
		JButton btnAddNewBook = new JButton("Add new book");
		btnAddNewBook.setBounds(12, 51, 186, 25);
		panel_1.add(btnAddNewBook);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(12, 89, 84, 25);
		panel_1.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(108, 89, 90, 25);
		panel_1.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBook();
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBook();
			}
		});
		btnAddNewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddBook ab = new AddBook();
					ab.setVisible(true);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnViewListBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadListBooks();
			}
		});
		
		JLabel lblBookOption = new JLabel("Book option");
		lblBookOption.setBounds(75, 13, 66, 16);
		panel.add(lblBookOption);
		lblBookOption.setForeground(Color.RED);
		lblBookOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(287, 44, 206, 127);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnViewListRequests = new JButton("View list requests");
		btnViewListRequests.setBounds(12, 13, 182, 25);
		panel_2.add(btnViewListRequests);
		
		JButton btnClearOutOf = new JButton("Clear out of date requests");
		btnClearOutOf.setBounds(12, 51, 182, 25);
		panel_2.add(btnClearOutOf);
		
		JButton btnDeny = new JButton("Deny");
		btnDeny.setBounds(110, 89, 84, 25);
		panel_2.add(btnDeny);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.setBounds(12, 89, 84, 25);
		panel_2.add(btnAccept);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptRequest();
			}
		});
		btnDeny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				denyRequest();
			}
		});
		btnClearOutOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearOutOfDateRequests();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnViewListRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadListRequests();
			}
		});
		
		JLabel lblRequestOption = new JLabel("Request option");
		lblRequestOption.setBounds(336, 13, 95, 16);
		panel.add(lblRequestOption);
		lblRequestOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblRequestOption.setForeground(Color.RED);
		
		parent.setVisible(false);
	}
	
	public void loadListBooks()
	{
		try {
			String query = new String("Select ID_Book, Title,  Author, Genre, Rating, Borrowed_time, Remain  from Books");
			PreparedStatement pst = dh.getCnn().prepareStatement(query);		
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadListRequests()
	{
		try {
			String query = new String("Select  Books.ID_Book, Books.Title as Book, Users.ID_user, Users.Name as Reader, Users.fail_safety_point, Reserves.ReservedDate from "
					+ "((Books inner join  Reserves on Books.ID_Book = Reserves.ID_Book) inner join Users on Users.ID_user = Reserves.ID_User)");
			PreparedStatement pst = dh.getCnn().prepareStatement(query);		
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("deprecation")
	public void clearOutOfDateRequests() throws IOException
	{
		try {
			String query = new String("select * from Reserves where ReservedDate < ?");
			PreparedStatement pst = dh.getCnn().prepareStatement(query);	
			
			Calendar d = Calendar.getInstance();
			Date date = new Date(d.get(Calendar.YEAR)-1900, d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH)-3);
			pst.setDate(1, date);
			
			ResultSet rs = pst.executeQuery();
			
			Vector<String> id_user = new Vector<String>();
			Vector<String> id_book = new Vector<String>();
			while(rs.next())
			{
				id_user.add(rs.getString(1));
				id_book.add(rs.getString(2));
			}
			
			for(int i=0; i<id_user.size(); i++)
			{
				query = "Select fail_safety_point from Users where ID_user = " + id_user.elementAt(i);
				rs = dh.getCnn().prepareStatement(query).executeQuery();
				rs.next();		
				query = "update Users set fail_safety_point = "+ Math.max(0, rs.getInt(1)-3) +" where ID_user = " + id_user.elementAt(i);
				client.sendData(query);
				
				query = "Select Remain from Books where ID_Book = " + id_book.elementAt(i);
				rs = dh.getCnn().prepareStatement(query).executeQuery();
				rs.next();
				query = "update Books set Remain = " + (rs.getInt(1)+1) + " where ID_Book = " + id_book.elementAt(i);
				client.sendData(query);
			}			
			    
			query = new String("delete from Reserves where ReservedDate < '"+formatter.format(date) + "'");
			client.sendData(query);
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/*
	 * Add book to listReadedBooks of user 
	 * Change borrowing Book
	 * Set reserved book = null
	 * Increase fail safety point of user by 3
	 * Delete request from Reserves table
	*/
	@SuppressWarnings("deprecation")
	public void acceptRequest() {
		try {
			String id_user = null, id_book = null;
			int row = table.getSelectedRow();
			if(row == -1)
			{
				JOptionPane.showMessageDialog(null, "Please choose an object!");
				return;
			}
			else
			{
				id_book = table.getModel().getValueAt(row, 0).toString();
				id_user = table.getModel().getValueAt(row, 2).toString();

				String query = new String("Select Borrowed_time, Title from Books where ID_Book = "+id_book);
				PreparedStatement pst = dh.getCnn().prepareStatement(query);		
				ResultSet rs = pst.executeQuery();
				rs.next();
				
				query = "update Books set Borrowed_time = " + (rs.getInt(1) + 1) + " where ID_Book = " + id_book;
				client.sendData(query);
				
				query = "Select fail_safety_point, List_Readed_books from Users where ID_user = " + id_user;
				ResultSet rs2 = dh.getCnn().prepareStatement(query).executeQuery();
				rs2.next();	
				
				query = "update Users set Borrowing = '" + rs.getString(2) + "', fail_safety_point = " + Math.min(100, rs2.getInt(1)+3)
						+ ", Reserved = null where ID_user = " + id_user;
				client.sendData(query);
				
				FileHelper fh = new FileHelper();
				Calendar d = Calendar.getInstance();
				Date date = new Date(d.get(Calendar.YEAR)-1900, d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH));
				fh.appendToFile(rs2.getString(2), rs.getString(2) + " " + String.valueOf(date));
				
				query = "delete from Reserves where ID_User = " + id_user + " and ID_Book = " + id_book;
				client.sendData(query);
				
				pst.close();
				rs.close();
				rs2.close();
			}
			loadListRequests();	
		}catch(Exception a)
		{
			a.printStackTrace();
		}
	}
	
	public void denyRequest()
	{
		try {
			String id_user = null, id_book = null;
			int row = table.getSelectedRow();
			if(row == -1)
			{
				JOptionPane.showMessageDialog(null, "Please choose an object!");
			}
			else
			{
				id_book = table.getModel().getValueAt(row, 0).toString();
				id_user = table.getModel().getValueAt(row, 2).toString();

				String query = "delete from Reserves where ID_User = " + id_user + " and ID_Book = " + id_book;
				client.sendData(query);
			}
			loadListRequests();	
		}
		catch(Exception a)
		{
			a.printStackTrace();
		}
	}
	
	public void deleteBook()
	{
		try {
			String id_book = null;
			int row = table.getSelectedRow();
			if(row == -1)
			{
				JOptionPane.showMessageDialog(null, "Please choose an object!");
				return;
			}
			else
			{
				id_book = table.getModel().getValueAt(row, 0).toString();
				
				String query = new String("Select Comment_link from Books where ID_Book = "+id_book);
				PreparedStatement pst = dh.getCnn().prepareStatement(query);		
				ResultSet rs = pst.executeQuery();
				rs.next();
				System.out.println(rs.getString(1));
				
				FileHelper fh = new FileHelper();
				fh.deleteFile(rs.getString(1));

				query = "delete from Books where ID_Book = " + id_book;
				client.sendData(query);
			}
			JOptionPane.showMessageDialog(null, "Deleted successfully!");
			loadListBooks();				
		}catch(Exception a)
		{
			a.printStackTrace();
		}
	}
	
	public void updateBook()
	{
		try {
			String id_book = null;
			int row = table.getSelectedRow();
			if(row == -1)
			{
				JOptionPane.showMessageDialog(null, "Please choose an object!");
				return;
			}
			else
			{
				id_book = table.getModel().getValueAt(row, 0).toString();
				UpdateBook up = new UpdateBook(id_book);
				up.setVisible(true);
			}
			loadListBooks();		
		}catch(Exception a)
		{
			a.printStackTrace();
		}
	}
}
