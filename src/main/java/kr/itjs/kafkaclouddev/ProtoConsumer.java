package kr.itjs.kafkaclouddev;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import itjs.global.v1.account.status.AccountEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class ProtoConsumer implements Consumer<String> {

  @Override
  public void accept(String jsonMessage) {
    try {
      // JSON -> Protobuf 변환
      log.info("Received JSON Message: " + jsonMessage);
      AccountEvent.Builder builder = AccountEvent.newBuilder();
      JsonFormat.parser().merge(jsonMessage, builder);
      AccountEvent userMessage = builder.build();

      System.out.println("Received Protobuf Message: " + userMessage);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException("Failed to parse JSON to Protobuf", e);
    }
  }
}
