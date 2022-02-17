package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 상태 정보
        response.setStatus(HttpServletResponse.SC_OK);

        // 응답 헤더
//        response.setHeader("Content-Type", "text/plain; charset=utf-8");
        content(response);

        // 쿠키
//        response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        cookie(response);

        // 캐쉬 제어
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        // SSL을 사용해서 민감한 정보들을 보호한다고 해도 브라우저에 이 정보가 캐싱되면 문제가 발생할 수 있다.
        // (PC방이나 공공장소에서 사용후 캐슁으로 인해 개인 정보가 남는등..)
        response.setHeader("Pragma", "no-cache");

        // 임의의 헤더 정보도 삽입 가능
        response.setHeader("my-header", "hello");

        // 리다이렉트
        redirect(response);

        // 메세지 바디에 넣기
        PrintWriter writer = response.getWriter();
        writer.println("ok");


    }

    private void content(HttpServletResponse response) {

        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2

        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        // response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {

        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {

        // 원하는 형태
        //Status Code 302
        //Location: /basic/hello-form.html

        //response.setStatus(HttpServletResponse.SC_FOUND); // 302
        //response.setHeader("Location", "/basic/hello-form.html");

        // 위의 두개를 한꺼번에 쓰는 방법
        response.sendRedirect("/basic/hello-form.html");
    }
}
