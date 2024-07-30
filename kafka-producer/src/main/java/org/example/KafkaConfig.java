package org.example;

public enum KafkaConfig {
    BOOTSTRAP_SERVERS("54.180.140.202:9092");

    private final String value;

    KafkaConfig(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
