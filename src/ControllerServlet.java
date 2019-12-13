import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;
	private LoginDAO loginDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
		loginDAO = new LoginDAO(jdbcURL, jdbcUsername, jdbcPassword);
		System.out.println("xin chao");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/showlogin":{
				showLogin(request, response);
				break;
			}
			case "/login": {
				login(request, response);
				break;
			}
			case "/new": {
				showNewForm(request, response);
				break;
			}
			case "/insert": {
				insertBook(request, response);
				break;
			}
			case "/delete": {
				deleteBook(request, response);
				break;
			}
			case "/edit": {
				showEditForm(request, response);
				break;
			}
			case "/update": {
				updateBook(request, response);
				break;
			}
			case "/list":{
				listBook(request, response);
				break;
			}
			default: {
				showLogin(request, response);
				break;
			}
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showLogin(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		System.out.println("hello");
		dispatcher.forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> users = loginDAO.listAllUser();
		String userName = request.getParameter("uname");
		String password = request.getParameter("psw");
		for (User user : users) {
			if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
				response.sendRedirect("list");
			}else {
				System.out.println("login loi");
			}
		}

	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Book> listBook = bookDAO.listAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookFrom.jsp");
		System.out.println("hello");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = bookDAO.getBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookFrom.jsp");
		request.setAttribute("book", existingBook);
		System.out.println(existingBook.author);
		dispatcher.forward(request, response);

	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));
		Book newBook = new Book(title, author, price);
		bookDAO.insertBook(newBook);
		response.sendRedirect("list");
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));
		Book book = new Book(id, title, author, price);
		System.out.println(book.author);
		bookDAO.updateBook(book);
		response.sendRedirect("list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book book = new Book(id);
		bookDAO.deleteBook(book);
		response.sendRedirect("list");

	}
}