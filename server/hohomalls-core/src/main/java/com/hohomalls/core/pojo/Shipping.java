package com.hohomalls.core.pojo;

import lombok.*;

/**
 * Shipping.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Shipping extends BasePojo {

  private Double fee;
  private Option option;
  private String description;

  public enum Option {
    MEETUP("Meetup"),
    MAILING("Mailing");

    private final String name;

    Option(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
