package com.ibm.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.core.util.RandomValidateCode;


/**
 * Servlet implementation class CheckCode
 */
public class CheckCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckCode() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.process(request, response);
	}
	
	private void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String checkCode = request.getParameter("checkCode");
		if(null == checkCode){
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCode randomValidateCode = new RandomValidateCode();
			try {
				randomValidateCode.getRandcode(request, response);// 输出图片方法
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			HttpSession session = request.getSession();
			String codeFromSession = (String)session.getAttribute("RANDOMVALIDATECODEKEY");
			response.setContentType("application/x-javascript;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			if(checkCode.toLowerCase().equals(codeFromSession.toLowerCase())){
				pw.write("{\"status\" : \"1\"}");
				pw.close();
			}else{
				pw.write("{\"status\" : \"0\"}");
				pw.close();
			}
		}
	}

}
