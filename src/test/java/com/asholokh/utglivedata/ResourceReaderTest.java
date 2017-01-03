package com.asholokh.utglivedata;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asholokh.utglivedata.reader.ResourceReader;
import com.github.junrar.exception.RarException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ResourceReaderTest {
  @Autowired
  private ResourceReader resourceReader;

  @Test
  @Ignore
  public void testReadData() throws IOException, RarException {
    Object result = resourceReader.readAllData();

    assertNotNull(result);
  }
}
