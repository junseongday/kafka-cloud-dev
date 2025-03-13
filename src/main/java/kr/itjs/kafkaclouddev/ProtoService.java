package kr.itjs.kafkaclouddev;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import itjs.global.v1.account.status.AccountEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class ProtoService {

  @Bean
  public Consumer<byte[]> protoConsumer() {
    return protobufData -> {
      try {
        log.info("🔥 Received Protobuf Message: {}" + protobufData);
        // 1️⃣ Protobuf 역직렬화 (Kafka에서 받은 바이너리 데이터를 객체로 변환)
        AccountEvent userMessage = AccountEvent.parseFrom(protobufData);

        // 2️⃣ 변환된 메시지 출력
        System.out.println("🔥 Received Protobuf Message: " + userMessage);
      } catch (Exception e) {
        throw new RuntimeException("❌ Failed to parse Protobuf message", e);
      }
    };
  }

}
