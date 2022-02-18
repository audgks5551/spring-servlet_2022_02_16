package hello.servlet.web.sevlet.frontcontroller.v3;

import hello.servlet.web.sevlet.frontcontroller.ModelView;
import hello.servlet.web.sevlet.frontcontroller.MyView;
import hello.servlet.web.sevlet.frontcontroller.v2.ControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI(); // 요청에서 URI 찾기

        ControllerV3 controller = controllerMap.get(requestURI); // 필요한 컨트롤러 찾기

        if(controller == null) { // 찾는 컨트롤러가 없으면 404 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap

        Map<String, String> paramMap = createParamMap(request); // 요청 파라미터의 모두 가져오기

        ModelView mv = controller.process(paramMap); // 비지니스 로직 후 모델 반환 ( 모델안에는 논리 주소가 있다.)

        MyView view = viewResolver(mv); // ModelView의 논리주소를 실제 주소로 변경하여 MyView 객체 반환 (페이지 이동 가능 및 실제 주소 생성)

        view.render(mv.getModel(),request, response);

    }

    private MyView viewResolver(ModelView mv) {
        return new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
