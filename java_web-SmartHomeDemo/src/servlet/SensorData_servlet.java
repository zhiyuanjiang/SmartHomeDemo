package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.ha.backend.Sender;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.SensorData;
import sql.OperateSql;

/**
 * Servlet implementation class SensorData_servlet
 */
@WebServlet("/SensorData_servlet")
public class SensorData_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SensorData_servlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        /**
         * 接收json字符串,自动关闭流
         */
        String st,json = "";
        try(BufferedReader reader = new BufferedReader(request.getReader())){
        	while((st = reader.readLine()) != null) {
        		json += st;
        	}
        	
        	System.out.println("the json is : "+json);
            
            //将json数据转换成传感器数据类
            if(!json.equals("failed") && !json.equals("")) {
            	try {
            		Gson gson = new Gson();
                    SensorData data = gson.fromJson(json, SensorData.class);
                    //System.out.println(data);
                    if(data != null) {
                    	OperateSql.updateData(data);
                    }else {
                    	//////////////
                    	System.out.println("sensordata is null");
                    }
                    	
            	}catch (JsonSyntaxException e) {
					// TODO: handle exception
            		System.out.println("json failed to change");
				}
            }
            
            try(PrintWriter out = new PrintWriter(response.getWriter())){
            	out.println("receive data success");
            	out.flush();
            }
        	
        }catch (IOException e) {
			// TODO: handle exception
        	System.out.println("server receive json data failed");
		}

	}

}
