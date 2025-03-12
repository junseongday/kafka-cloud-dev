package kr.itjs.kafkaclouddev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.SerializationUtils;

@Slf4j
@Configuration
public class KafkaConfig {
  ObjectMapper  objectMapper = new ObjectMapper();
//  @Bean
//  public Supplier<Integer> basicProducer() {
//    return () -> {
//      log.info("basicProducer()");
//      return 100;
//    };
//  }
//
//  @Bean
//  public Consumer<String> basicConsumer() {
//    return number -> log.info("message = {}", number);
//  }

  @Bean
  public Function<String, String> uppercase() {
    return value -> value.toUpperCase();
  }

  @Bean
  public Function<Integer, Integer> firstInput() {
    return value -> {
      System.out.println(value * 2);
      return value * 2;
    };
  }

  @Bean
  public Function<Integer, String> secondInput() {
    return value -> {
      String result = value.toString() + ": 원래는 숫자";
      System.out.println(result);
      return result;
    };
  }

/* // 2.3버전에서는 파싱 문제가 있어서 byte로 파싱해서 사용

  @Bean
  public Supplier<List<byte[]>> basicProducer() {
    List<byte[]> memberList = new ArrayList<>();
    Member member1 = new Member(UUID.randomUUID().toString(), "xellos");
    memberList.add(SerializationUtils.serialize(member1));

    Member member2 = new Member(UUID.randomUUID().toString(), "zenon");
    memberList.add(SerializationUtils.serialize(member2));

    Member member3 = new Member(UUID.randomUUID().toString(), "crimson");
    memberList.add(SerializationUtils.serialize(member3));

    return () -> memberList;
  }

  @Bean
  public Consumer<List<byte[]>> basicConsumer() {
    return members -> {
      for (byte[] member : members) {
        Member real = (Member) SerializationUtils.deserialize(member);
        System.out.println(real);
      }
      log.info("message = {}", members);
    };
  }
  */

//  @Bean
//  public Supplier<List<Member>> basicProducer() {
//    List<Member> memberList = new ArrayList<>();
//    memberList.add(new Member(UUID.randomUUID().toString(), "xellos"));
//    memberList.add(new Member(UUID.randomUUID().toString(), "zenon"));
//    memberList.add(new Member(UUID.randomUUID().toString(), "crimson"));
//
//    return () -> memberList;
//  }

//  @Bean
//  public Consumer<List<Member>> basicConsumer() {
//    return members -> {
//      for (Member member : members) {
//        System.out.println(member);
//      }
//      log.info("message = {}", members);
//    };
//  }
//
  @Bean
  public Consumer<Message<List<Member>>> basicConsumer() {

    return message -> {

//        String json = objectMapper.writeValueAsString(members);
      log.info("Received members JSON: {}", message);
    };
  }

  @Bean
  public Function<String, List<Message<String>>> batch() {
    return p -> {
      List<Message<String>> list = new ArrayList<>();
      list.add(MessageBuilder.withPayload(p + ":1").build());
      list.add(MessageBuilder.withPayload(p + ":2").build());
      list.add(MessageBuilder.withPayload(p + ":3").build());
      list.add(MessageBuilder.withPayload(p + ":4").build());
      return list;
    };
  }

  @Bean
  public Consumer<List<String>> batchConsumer() {
    return members -> {
      for (String member : members) {
        System.out.println(member);
      }
      log.info("message = {}", members);
    };
  }
}
