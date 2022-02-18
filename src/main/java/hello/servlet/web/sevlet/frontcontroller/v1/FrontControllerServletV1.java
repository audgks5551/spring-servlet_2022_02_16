package hello.servlet.web.sevlet.frontcontroller.v1;

import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI(); // URI 획득
        // 예 ) URI = /front-controller/v1/members/new-form

        ControllerV1 controller = controllerMap.get(requestURI); // 요청에서 입력받은 URI와 컨트롤러의 key와 일치하는 컨트롤러 찾기
        // 예) "/front-controller/v1/members/new-form" == "/front-controller/v1/members/new-form" => new MemberFormControllerV1() 반환

        if(controller == null) { // 컨트롤러를 찾을 수 없을 때는 http 404를 응답한다.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response);

    }
}
