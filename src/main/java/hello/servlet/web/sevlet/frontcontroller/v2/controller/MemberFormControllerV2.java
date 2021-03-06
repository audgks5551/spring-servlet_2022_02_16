package hello.servlet.web.sevlet.frontcontroller.v2.controller;

import hello.servlet.web.sevlet.frontcontroller.MyView;
import hello.servlet.web.sevlet.frontcontroller.v2.ControllerV2;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

    // 실제 경로만 반환
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
