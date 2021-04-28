package com.hohomalls.app.document;

import com.hohomalls.mongo.document.Base;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * The User document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@Builder
@Document("users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Base {

  @Indexed(unique = true)
  private String email;

  private String mobile;

  @Indexed(unique = true)
  private String nickname;

  private UserStatus status;

  private List<Address> addresses;

  /** User status. */
  public enum UserStatus {
    ACTIVE,
    INACTIVE,
    TERMINATED
  }

  /** The embedded document. */
  @Data
  public static class Address {
    private String postCode;
    private String country;
    private String city;
    private String street;
    private String contact;
    private String addressee;
  }
}
