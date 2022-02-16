package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");

        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // 지정된 파라미터 값 추출
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // 헤더 정보 ( 예) Content-Type: text/plain;charset=utf-8 )
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        // 페이로드 정보 ( 예) http://localhost:89/hello?username=이 => username: 이 )
        response.getWriter().write("hello " + username);

    }

}
