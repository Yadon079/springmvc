package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class RequestBodyJsonController {

  private ObjectMapper objectMapper = new ObjectMapper();

  /* Servlet 객체 + ObjectMapper를 사용해 원시적으로 해결 */
  @PostMapping("/request-body-json-v1")
  public void requestBodyJsonV1(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    ServletInputStream inputStream = req.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody = {}", messageBody);

    HelloData data = objectMapper.readValue(messageBody, HelloData.class);

    log.info("username = {}, age = {}", data.getUsername(), data.getAge());

    resp.getWriter().write("ok");
  }

  /**
   * @RequestBody
   * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   *
   * @ResponseBody
   * - 모든 메서드에 @ResponseBody 적용
   * - 메시지 바디 정보 직접 반환(view 조회X)
   * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   *
   * @RequestBody를 통해 String으로 받은 후 ObjectMapper로 Json 형태로 변환
   */
  @ResponseBody
  @PostMapping("/request-body-json-v2")
  public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

    HelloData data = objectMapper.readValue(messageBody, HelloData.class);

    log.info("username = {}, age = {]", data.getUsername(), data.getAge());

    return "ok";
  }

  /**
   * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
   * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype:
  application/json)
   *
   * @RequestBody가 있어야 Http request body의 값을 가져올 수 있음
   * 실무에서는 Request를 받는 DTO를 파라미터로 받아서 사용하면 됨
   */
  @ResponseBody
  @PostMapping("/request-body-json-v3")
  public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {

    log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

  /* HttpEntity를 사용해서 해결 */
  @ResponseBody
  @PostMapping("/request-body-json-v4")
  public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {

    HelloData data = httpEntity.getBody();

    log.info("username = {}, age = {}", data.getUsername(), data.getAge());

    return "ok";
  }

  /**
   * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
   * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content type: application/json)
   *
   * @ResponseBody 적용
   * - 메시지 바디 정보 직접 반환(view 조회X)
   * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)
   */
  @ResponseBody
  @PostMapping("/request-body-json-v5")
  public HelloData requestBodyJsonV5(@RequestBody HelloData data) {

    log.info("username={}, age={}", data.getUsername(), data.getAge());

    return data;
  }

}
