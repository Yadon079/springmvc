package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

  /* V2 : @RequestParam을 사용해 필드와 매칭해서 받기 */
  /**
   * @RequestParam 사용
   * - 파라미터 이름으로 바인딩
   * @ResponseBody 추가
   * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
   */
  @ResponseBody // 해당 태그를 통해 response 시 바로 문자 반환 -> @RestController인 것 처럼! (뷰 검사 X)
  @RequestMapping("/request-param-v2")
  public String requestParamV2(@RequestParam("username") String memberName,
                               @RequestParam("age") int memberAge) {
    log.info("username = {}, age = {}", memberName, memberAge);

    return "ok";
  }

  /* V3 : @RequestParam의 변수명을 필드명과 일치시켜 생략 */
  /**
   * @RequestParam 사용
   * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
   */
  @ResponseBody
  @RequestMapping("/request-param-v3")
  public String requestParamV3(@RequestParam String username,
                               @RequestParam int age) {
    log.info("username = {}, age = {}", username, age);

    return "ok";
  }

  /* V4 : @RequestParam 애노테이션 자체를 생략 -> 권장하지 않음 */
  /**
   * @RequestParam 사용
   * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
   */
  @ResponseBody
  @RequestMapping("/request-param-v4")
  public String requestParamV4(String username, int age) {
    log.info("username = {}, age = {}", username, age);

    return "ok";
  }

  /* @RequestParam의 required 옵션으로 필수 값을 검증할 수 있음 */
  /**
   * @RequestParam.required
   * /request-param -> username이 없으므로 예외
   *
   * 주의!
   * /request-param?username= -> 빈문자로 통과
   *
   * 주의!
   * /request-param
   * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함
   */
  @ResponseBody
  @RequestMapping("/request-param-required")
  public String requestParamRequired(@RequestParam(required = true) String username,
                                     @RequestParam(required = false) Integer age) {
    log.info("username = {}, age = {}", username, age);

    return "ok";
  }

  /* @RequestParam의 defaultValue 옵션으로 기본 값을 지정할 수 있음 */
  /**
   * @RequestParam
   * - defaultValue 사용
   *
   * 참고: defaultValue는 빈 문자의 경우에도 적용
   * /request-param?username=
   */
  @ResponseBody
  @RequestMapping("/request-param-default")
  public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                    @RequestParam(required = false, defaultValue = "-1") int age) {
    log.info("username = {}, age = {}", username, age);

    return "ok";
  }

  /**
   * @RequestParam Map, MultiValueMap
   * Map(key=value)
   * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
   */
  @ResponseBody
  @RequestMapping("/request-param-map")
  public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
    log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));

    return "ok";
  }

  @ResponseBody
  @RequestMapping("/model-attribute-v1")
  public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

  @ResponseBody
  @RequestMapping("/model-attribute-v2")
  public String modelAttribute(HelloData helloData) {
    log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

}
