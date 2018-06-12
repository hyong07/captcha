package kh.captcha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class CaptchaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();
			String command = requestURI.substring(contextPath.length());
			

			if(command.equals("/captcha.do")) {
				
				response.setCharacterEncoding("utf8");
				String value = request.getParameter("value");
				System.out.println(value);

				Captcha capt = new Captcha();
				String dirPath = request.getServletContext().getRealPath("captchaImage") + "\\";
				String keyValue = capt.getKey();
				String fileName = capt.getImage(keyValue,dirPath);
				boolean result = capt.comp_result(keyValue,value);
				System.out.println(dirPath);
			
			
				if(value != null) {
					System.out.println(value);
					response.getWriter().print(result);
				}
				else {
					response.getWriter().print(fileName);
				}

			}

		}catch(Exception e) {
			e.printStackTrace();}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
