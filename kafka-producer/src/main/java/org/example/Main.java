package org.example;

import org.example.producer.AIProcessProducer;
import org.example.producer.ShortsProcessProducer;
import org.example.producer.VideoDownloadProducer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AIProcessProducer aiProcessProducer = new AIProcessProducer();
        ShortsProcessProducer shortsProcessProducer = new ShortsProcessProducer();
        VideoDownloadProducer videoDownloadProducer = new VideoDownloadProducer();

        int count = 10;

        while (count > 0) {
            Thread.sleep(1000);
            aiProcessProducer.produce();
            shortsProcessProducer.produce();
            videoDownloadProducer.produce();
            count--;
        }
    }
}