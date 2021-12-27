package com.hohomalls.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public final class Address {

  private String postCode;
  private String country;
  private String city;
  private String street;
  private String contact;
  private String addressee;
}
