package org.example.video;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class VideoDownloadConsumer {
    private static final Logger logger = LoggerFactory.getLogger(VideoDownloadConsumer.class);

    private final VideoDownloader videoDownloader;

    public VideoDownloadConsumer(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    public void consume() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "54.180.140.202:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "video-download-group");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("video-download-topic"));

        try (consumer) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);

                for (ConsumerRecord<String, String> record : records) {
                    videoDownloader.process();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
