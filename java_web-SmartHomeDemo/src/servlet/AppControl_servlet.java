package servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.SensorData;
import sql.OperateSql;

/**
 * Servlet implementation class AppControl_servlet
 */
@WebServlet("/AppControl_servlet")
public class AppControl_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppControl_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id").toString();
		String flag = request.getParameter("flag").toString();
		SensorData data = OperateSql.queryData(id);
		String ip = data.getIp();
		try {
			Socket client = new Socket(ip, 80);
			client.setSoTimeout(10000);
			//获取Socket的输出流，用来发送数据到服务端  
			try(PrintStream out = new PrintStream(client.getOutputStream());) {
				out.println(flag);
				System.out.println("与设备："+id+"通信成功");
			}
			client.close();
		}catch(IOException e) {
			System.out.println("与设备："+id+"通信失败");
		}
	}

}
