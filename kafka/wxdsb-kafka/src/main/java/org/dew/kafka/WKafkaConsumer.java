package org.dew.kafka;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

public 
class WKafkaConsumer 
{
  protected static String KAFKA_SERVERS     = "localhost:9092";
  protected static String KAFKA_KEYSTORE    = "";
  protected static String KAFKA_KEY_PASS    = "";
  protected static String KAFKA_TRUSTSTORE  = "";
  protected static String KAFKA_TRUST_PASS  = "";
  
  protected static String KAFKA_GROUP_ID    = "dew";
  protected static String KAFKA_TOPIC_ASUR  = "dew_xds";
  protected static int    KAFKA_MAX_RECORDS = 0;
  
  public static 
  void main(String[] args) 
  {
    File keystoreFile   = getFileResource(KAFKA_KEYSTORE);
    File truststoreFile = getFileResource(KAFKA_TRUSTSTORE);
    
    // create consumer configs
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,        KAFKA_SERVERS);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,   StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,                 KAFKA_GROUP_ID);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,        "latest");
    
    if(truststoreFile != null || keystoreFile != null) {
      properties.setProperty("security.protocol",                        "SSL");
    }
    
    if(truststoreFile != null) {
      properties.setProperty("ssl.truststore.location",                  truststoreFile.getAbsolutePath());
      properties.setProperty("ssl.truststore.password",                  KAFKA_TRUST_PASS);
      properties.setProperty("ssl.truststore.type",                      getTruststoreType(truststoreFile));
    }
    
    if(keystoreFile != null) {
      properties.setProperty("ssl.client.auth",                          "required");
      properties.setProperty("ssl.keystore.location",                    keystoreFile.getAbsolutePath());
      properties.setProperty("ssl.keystore.password",                    KAFKA_KEY_PASS);
      properties.setProperty("ssl.keystore.type",                        getKeystoreType(keystoreFile));
    }
    
    if(KAFKA_MAX_RECORDS > 0) {
      properties.setProperty("max.poll.records",                         String.valueOf(KAFKA_MAX_RECORDS));
    }
    
    KafkaConsumer<String, String> consumer = null;
    try {
      consumer = new KafkaConsumer<>(properties);
      
      System.out.println("consumer.subscribe(" + Arrays.asList(KAFKA_TOPIC_ASUR) + ")...");
      
      consumer.subscribe(Arrays.asList(KAFKA_TOPIC_ASUR));
      
      while(true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
        
        for(ConsumerRecord<String, String> record: records) {
          String message = record.value();
          System.out.println(message);
        }
      }
    }
    catch(WakeupException wex) {
      wex.printStackTrace();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    finally {
      consumer.close();
    }
  }
  
  protected static
  File getFileResource(String fileName) 
  {
    if(fileName == null || fileName.length() == 0) {
      return null;
    }
    String cfgFolder = System.getProperty("user.home") + File.separator + "cfg";
    File result = null;
    try {
      result = new File(cfgFolder + File.separator + fileName);
      if(!result.exists()) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if(url != null) {
          result = new File(url.toURI());
        }
        else {
          System.err.println(fileName + " not found in classpath or in " + cfgFolder);
        }
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }
  
  protected static
  String getKeystoreType(File file)
  {
    if(file == null) return "";
    String name = file.getName().toLowerCase();
    if(name.endsWith(".p12")) return "PKCS12";
    return "JKS";
  }
  
  protected static
  String getTruststoreType(File file)
  {
    if(file == null) return "";
    String name = file.getName().toLowerCase();
    if(name.endsWith(".p12")) return "PKCS12";
    return "JKS";
  }
}
