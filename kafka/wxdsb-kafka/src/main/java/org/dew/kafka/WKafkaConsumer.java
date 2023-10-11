package org.dew.kafka;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
  protected static String KAFKA_TOPIC       = "test";
  protected static int    KAFKA_MAX_RECORDS = 0;
  
  protected static String KAFKA_SASL_MECH   = "SCRAM-SHA-256";
  protected static String KAFKA_SASL_JAAS   = "";
  
  public static 
  void main(String[] args) 
  {
    System.out.println("+------------------+");
    System.out.println("|  WKafkaConsumer  |");
    System.out.println("+------------------+");
    
    String argServer  = null;
    String argUser    = null;
    String argPass    = null;
    String argTopic   = null;
    String argGroupId = null;
    
    if(args != null && args.length > 0) argServer  = args[0];
    if(args != null && args.length > 1) argUser    = args[1];
    if(args != null && args.length > 2) argPass    = args[2];
    if(args != null && args.length > 3) argTopic   = args[3];
    if(args != null && args.length > 4) argGroupId = args[4];
    
    if(argServer != null && argServer.length() > 0) {
      KAFKA_SERVERS = argServer;
    }
    if(argUser != null && argUser.length() > 0 && argPass != null && argPass.length() > 0) {
      KAFKA_SASL_JAAS = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"" + argUser + "\" password=\"" + argPass + "\";";
    }
    if(argTopic != null && argTopic.length() > 0) {
      KAFKA_TOPIC = argTopic;
    }
    if(argGroupId != null && argGroupId.length() > 0) {
      KAFKA_GROUP_ID = argGroupId;
    }
    
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
    else if(KAFKA_SASL_JAAS != null && KAFKA_SASL_JAAS.length() > 0) {
      properties.setProperty("security.protocol", "SASL_PLAINTEXT");
      properties.setProperty("sasl.mechanism",    KAFKA_SASL_MECH);
      properties.setProperty("sasl.jaas.config",  KAFKA_SASL_JAAS);
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
    
    // Print sorted configuration entries
    System.out.println("Config:");
    List<String> keys = new ArrayList<String>();
    Iterator<Object> iterator = properties.keySet().iterator();
    while(iterator.hasNext()) {
      Object key = iterator.next();
      keys.add(key.toString());
    }
    Collections.sort(keys);
    for(int i = 0; i < keys.size(); i++) {
      String key = keys.get(i);
      System.out.println(key + " = " + properties.getProperty(key));
    }
    System.out.println("--------------------");
    
    KafkaConsumer<String, String> consumer = null;
    try {
      consumer = new KafkaConsumer<>(properties);
      
      System.out.println("consumer.subscribe(" + Arrays.asList(KAFKA_TOPIC) + ")...");
      
      consumer.subscribe(Arrays.asList(KAFKA_TOPIC));
      
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
