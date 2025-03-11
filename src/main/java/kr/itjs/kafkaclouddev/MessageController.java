package kr.itjs.kafkaclouddev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {
  @Autowired
  private StreamBridge streambridge;

  @GetMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String delegateToSupplier(@RequestParam String body) {
    System.out.println("Sending " + body);
    streambridge.send("basicProducer-out-0", body);
    return "send";
  }
}
