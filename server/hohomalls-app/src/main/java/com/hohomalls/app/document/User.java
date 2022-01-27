package com.hohomalls.app.document;

import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.pojo.Address;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * The User pojo.
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
public class User extends BaseDoc {

  @Indexed(unique = true)
  private String email;

  @Indexed(unique = true)
  private String nickname;

  private List<Role> roles;

  private String mobile;

  private UserStatus status;

  private List<Address> addresses;

  /** A hashed password. */
  private String password;

  private Double rating;

  /** The user status. */
  public enum UserStatus {
    ACTIVE,
    INACTIVE,
    TERMINATED
  }
}
