package kr.itjs.kafkaclouddev;

import itjs.global.v1.account.status.AccountEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.google.protobuf.util.JsonFormat;

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

//  @GetMapping
//  @RequestMapping("/proto")
//  @ResponseStatus(HttpStatus.ACCEPTED)
//  public String delegateProtoToSupplier() {
//
//    // make AccountEvent
//    AccountEvent body = AccountEvent.newBuilder()
//        .setAccountId("1234")
//        .setEventType("A")
//        .setTimestamp(System.currentTimeMillis())
//        .build();
//
//    String jsonMessage;
//    try {
//      // body to json
//      jsonMessage = JsonFormat.printer().print(body);
//    } catch (Exception e) {
//      throw new RuntimeException("Failed to convert Protobuf to JSON", e);
//    }
//
//    Message<String> message = MessageBuilder
//        .withPayload(jsonMessage)
//        .setHeader("content-type", "application/json")
//        .build();
//    boolean result = streambridge.send("protoProducer-out-0", message);
//
//    if (!result) {
//      throw new RuntimeException("Message sending failed");
//    }
//
//    return "send";
//  }

  @GetMapping
  @RequestMapping("/proto")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String delegateProtoToSupplie2() {

    // make AccountEvent
    AccountEvent body = AccountEvent.newBuilder()
        .setAccountId("1234")
        .setEventType("A")
        .setTimestamp(System.currentTimeMillis())
        .build();

    byte[] protobufData =  body.toByteArray();

    Message<byte[]> message = MessageBuilder
        .withPayload(protobufData)
        .setHeader("content-type", "application/octet-stream")
        .build();
    boolean result = streambridge.send("protoProducer-out-0", message);

    if (!result) {
      throw new RuntimeException("Message sending failed");
    }

    return "send";
  }
}
