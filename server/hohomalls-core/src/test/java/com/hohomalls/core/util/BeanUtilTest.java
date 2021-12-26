package com.hohomalls.core.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BeanUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public class BeanUtilTest {
  @Test
  public void testCopyNonnullProperties() throws Exception {
    Source source = this.source();
    Destination destination = this.destination();

    assertThat(source.name).isNull();
    assertThat(destination.getAddress()).isNull();

    BeanUtil.copyNonnullProperties(destination, source);
    assertThat(destination.getAddress()).isEqualTo(source.getAddress());
    assertThat(destination.getAge()).isEqualTo(source.getAge());
    assertThat(destination.getName()).isNotNull();
  }

  private Destination destination() {
    Destination model = new Destination();
    model.setName("name");
    return model;
  }

  private Source source() {
    Source dto = new Source();
    dto.setAddress("add");
    dto.setAge(11);
    return dto;
  }

  @Data
  public static class Source {
    private String name;
    private String address;
    private int age;
  }

  @Data
  public static class Destination {
    private String name;
    private String address;
    private int age;
  }
}
