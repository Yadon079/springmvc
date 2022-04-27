package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

  @PostMapping("/request-body-string-v1")
  public void requestBodyString(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    ServletInputStream inputStream = req.getInputStream();
    // 반드시 stream을 바꿀 때에는 인코딩 형식을 지정
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody = {}", messageBody);

    resp.getWriter().write("ok");
  }

  @PostMapping("/request-body-string-V2")
  public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
    
    // 반드시 stream을 바꿀 때에는 인코딩 형식을 지정
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody = {}", messageBody);

    responseWriter.write("ok");
  }

  /**
   * HttpEntity: HTTP header, body 정보를 편라하게 조회
   * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
   * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   *
   * 응답에서도 HttpEntity 사용 가능
   * - 메시지 바디 정보 직접 반환(view 조회X)
   * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   * 
   * HttpEntity를 상속받은 RequestEntity / ResponseEntity를 사용해도 됨
   */
  @PostMapping("/request-body-string-V3")
  public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {

    String messageBody = httpEntity.getBody();

    log.info("messageBody = {}", messageBody);

    return new HttpEntity<>("ok");
  }

  /**
   * @RequestBody
   * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
   * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   *
   * @ResponseBody
   * - 메시지 바디 정보 직접 반환(view 조회X)
   * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
   */
  @ResponseBody
  @PostMapping("/request-body-string-V4")
  public String requestBodyStringV4(@RequestBody String messageBody) {

    log.info("messageBody = {}", messageBody);

    return "ok";
  }

}
