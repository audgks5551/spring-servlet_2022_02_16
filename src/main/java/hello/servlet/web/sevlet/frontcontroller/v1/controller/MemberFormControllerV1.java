package hello.servlet.web.sevlet.frontcontroller.v1.controller;

import hello.servlet.web.sevlet.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String viewPath = "/WEB-INF/views/new-form.jsp"; // 실제 뷰 경로

        // 페이지 이동을 하기 위해
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); // Forwarding할 정보를 인자값으로 받는다, 인자값은 페이지 이동할 경로
        dispatcher.forward(request, response); // dispatcher.forward(): 다른 서블릿이나 JSP로 이동할 수 있는 기능 == 페이지이동 ( dispatcher: 운행 관리원 )

    }
}
