package com.gupao.springcloudstreamkafka.raw.api;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Kafka Producer Demo(使用原始API)
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/12
 */
public class KafkaProducerDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 初始化配置
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        // 创建 Kafka Producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties);
        // 创建 Kakfa 消息  = ProducerRecord
        String topic = "gupao";
        Integer partition = 0;
        Long timestamp = System.currentTimeMillis();
        String key = "message-key";
        String value = "gupao.com";
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(topic, partition, timestamp, key, value);
        // 发放 Kakfa 消息
        Future<RecordMetadata> metadataFuture =  kafkaProducer.send(record);
        // 强制执行
        metadataFuture.get();
    }

}
