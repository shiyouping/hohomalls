package com.hohomalls.app.model;

import com.hohomalls.app.enumeration.UserStatus;
import com.hohomalls.core.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The class of UserModel.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/5/2021
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseModel {

  private static final long serialVersionUID = -8430376066014905655L;
  private String email;
  private String mobile;
  private String nickname;
  private UserStatus status;
  private List<AddressModel> addresses;

  /** The embedded document. */
  @Data
  @EqualsAndHashCode(callSuper = true)
  private static class AddressModel extends BaseModel {
    private static final long serialVersionUID = -4445635298089358627L;
    private String postCode;
    private String country;
    private String city;
    private String street;
    private String contact;
    private String addressee;
  }
}
