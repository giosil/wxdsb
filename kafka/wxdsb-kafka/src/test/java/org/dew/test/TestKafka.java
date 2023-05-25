package org.dew.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestKafka extends TestCase {
  
  public TestKafka(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestKafka.class);
  }
  
  public void testApp() throws Exception {
  }
  
}
