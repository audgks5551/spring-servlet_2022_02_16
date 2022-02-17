package hello.servlet.basic.request;

import org.springframework.http.converter.json.GsonBuilderUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);

    }

    private void printStartLine(HttpServletRequest request) {

        System.out.println("--- REQUEST-LINE - start ---");

        System.out.println("request.getMethod() = " + request.getMethod()); // GET

        System.out.println("request.getProtocal() = " + request.getProtocol()); // HTTP/1.1

        System.out.println("request.getScheme() = " + request.getScheme()); // http

        System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header

        System.out.println("request.getRequestURI() = " + request.getRequestURI()); // /request-headers

        System.out.println("request.getQueryString() = " + request.getQueryString()); // username=hi

        System.out.println("request.isSecure() = " + request.isSecure()); // https 사용 유무

        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }

    private void printHeaders(HttpServletRequest request) {

        // 헤더 추출 방법

        // 1. 옛날 방식
        System.out.println("--- Headers - start ---");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) { // 다음 요소가 있는지 확인
            String headerName = headerNames.nextElement(); // 해당 요소를 꺼내고 다음 요소를 가르키게 한다.
            System.out.println(headerName + ": " + headerName);
        }
        System.out.println("--- Headers - end ---");
        System.out.println();

        // 2. 요즘 방식
        System.out.println("--- Headers - start ---");

        // asIterator: 반복자로써, forEachRemaining: 나머지 각 항목에 대해
        request.getHeaderNames().asIterator()
                        .forEachRemaining(headerName -> System.out.println(headerName + ": " + headerName));

        // 원하는 헤더 정보 추출
        request.getHeader("host");

        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    private void printHeaderUtils(HttpServletRequest request) {

        System.out.println("--- Header 편의 조회 start ---");

        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 포트
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " + request.getContentType());// get 방식은 null
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    private void printEtc(HttpServletRequest request) {

        System.out.println("--- 기타 조회 start ---");

        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); // 0:0:0:0:0:0:0:1
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); // 0:0:0:0:0:0:0:1
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); // 61585
        System.out.println();

        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName()); // 0:0:0:0:0:0:0:1
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); // 0:0:0:0:0:0:0:1
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); // 89

        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}


