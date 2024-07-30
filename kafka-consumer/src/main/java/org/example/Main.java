package org.example;

import org.example.ai.AIProcessConsumer;
import org.example.ai.AIProcessor;
import org.example.shorts.ShortsProcessConsumer;
import org.example.shorts.ShortsProcessor;
import org.example.video.VideoDownloadConsumer;
import org.example.video.VideoDownloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        AIProcessConsumer aiProcessConsumer = new AIProcessConsumer(new AIProcessor());
        ShortsProcessConsumer shortsProcessConsumer = new ShortsProcessConsumer(new ShortsProcessor());
        VideoDownloadConsumer videoDownloadConsumer = new VideoDownloadConsumer(new VideoDownloader());

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(aiProcessConsumer::consume);
        executorService.execute(shortsProcessConsumer::consume);
        executorService.execute(videoDownloadConsumer::consume);
    }
}