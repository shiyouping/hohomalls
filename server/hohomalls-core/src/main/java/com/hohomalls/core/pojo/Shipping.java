package com.hohomalls.core.pojo;

import com.hohomalls.core.enumeration.Currency;
import lombok.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

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

  @Nonnegative private Double fee;
  @Nonnull private ShippingOption option;
  @Nonnull private String description;
  @Nonnull private Currency currency;

  public enum ShippingOption {
    MEETUP("Meetup"),
    MAILING("Mailing");

    private final String name;

    ShippingOption(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
