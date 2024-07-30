package org.example.shorts;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class ShortsProcessConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ShortsProcessConsumer.class);

    private final ShortsProcessor shortsProcessor;

    public ShortsProcessConsumer(ShortsProcessor shortsProcessor) {
        this.shortsProcessor = shortsProcessor;
    }

    public void consume() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "54.180.140.202:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "shorts-process-group");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("shorts-process-topic"));

        try (consumer) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);

                for (ConsumerRecord<String, String> record : records) {
                    shortsProcessor.process();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
