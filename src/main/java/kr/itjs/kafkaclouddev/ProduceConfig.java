package kr.itjs.kafkaclouddev;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProduceConfig {

  @Bean
  public Supplier<Integer> basicProducer() {
    return () -> {
      log.info("basicProducer()");
      return 100;
    };
  }

  @Bean
  public Consumer<String> basicConsumer() {
    return number -> log.info("message = {}", number);
  }

  @Bean
  public Function<String, String> uppercase() {
    return value -> value.toUpperCase();
  }
}
