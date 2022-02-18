package hello.servlet.web.sevlet.frontcontroller.v5;

import hello.servlet.web.sevlet.frontcontroller.ModelView;
import hello.servlet.web.sevlet.frontcontroller.MyView;
import hello.servlet.web.sevlet.frontcontroller.v3.ControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.sevlet.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV5.service");

        Object handler = getHandler(request); // 주소에 해당하는 컨트롤러 객체 생성
        if(handler == null) { // 찾는 컨트롤러가 없으면 404 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 생성한 컨트롤러를 제어할 수 있는 아탑터 찾기 (못찾으면 404반환)

        ModelView mv = adapter.handle(request, response, handler); // 찾은 아답터를 통해 비즈니스 로직 수행후 모델(사용자가 필요한 데이터) 반환

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName); // 논리주소 -> 물리주소

        view.render(mv.getModel(),request, response); // 요청에 데이터 담고 페이지 이동
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw  new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 요청에서 URI 찾기
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
