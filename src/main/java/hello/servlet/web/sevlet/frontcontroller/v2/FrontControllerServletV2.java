package hello.servlet.web.sevlet.frontcontroller.v2;

import hello.servlet.web.sevlet.frontcontroller.MyView;
import hello.servlet.web.sevlet.frontcontroller.v1.ControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        String requestURI = request.getRequestURI(); // 요청에서 URI 찾기

        ControllerV2 controller = controllerMap.get(requestURI); // 필요한 컨트롤러 찾기

        if(controller == null) { // 찾는 컨트롤러가 없으면 404 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response); // 실제 경로 찾음
        view.render(request, response); // 페이지 이동

        // v1 버전에서는 controller에서 페이지 이동을 하였는데
        // v2 버전에서는 servlet에서 controller.process를 호출하여 실제 경로를 찾아 페이지 이동을 담당한다.
        // 서블릿 컨테이너가 서블릿을 생성해주고
        // 서블릿은 HttpServletReqeust와 HttpServletResponse를 생성
        // 그러므로 HttpServletRequest와 HttpServletResponse는 한 서블릿내에서는 전역객체이다.?(아직확실하지않음)
        // 서블릿은 url주소를 찾고 사용자가 원하는 컨트롤러를 찾아 비지니스 로직 수행
    }
}
