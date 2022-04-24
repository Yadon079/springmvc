package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

  /* V1 : Servlet 객체를 통해서 값 파싱하기 */
  /* 반환 타입이 없으면서 응답에 값을 직접 넣으면, view 조회 X */
  @RequestMapping("/request-param-v1")
  public void requestParamV1(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));
    log.info("username = {}, age = {}", username, age);

    resp.getWriter().write("ok");
  }
  
  
}
