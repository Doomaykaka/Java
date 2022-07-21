import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class mainServ extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException , IOException{
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			writer.println("<h2>Hello from servlet</h2>");
		}finally {
			writer.close();
		}
	}
}
