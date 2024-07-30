package org.example.ai;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class AIProcessConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AIProcessConsumer.class);

    private final AIProcessor aiProcessor;

    public AIProcessConsumer(AIProcessor aiProcessor) {
        this.aiProcessor = aiProcessor;
    }

    public void consume() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "54.180.140.202:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "ai-process-group");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("ai-process-topic"));

        try (consumer) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);

                for (ConsumerRecord<String, String> record : records) {
                    aiProcessor.process();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
