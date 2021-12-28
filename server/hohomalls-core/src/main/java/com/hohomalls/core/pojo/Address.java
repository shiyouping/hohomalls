package com.hohomalls.core.pojo;

import lombok.*;

/**
 * Address.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Address extends BasePojo {

  private String postCode;
  private String country;
  private String city;
  private String street;
  private String contact;
  private String addressee;
}
