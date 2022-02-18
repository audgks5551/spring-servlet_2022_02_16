package hello.servlet.web.sevlet.frontcontroller.v3;

import hello.servlet.web.sevlet.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);
}
