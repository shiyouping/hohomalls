package com.hohomalls.app.document;

import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.pojo.Address;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

  @NotNull
  @Indexed(unique = true)
  private String email;

  @NotNull
  @Indexed(unique = true)
  private String nickname;

  @Size(min = 1)
  private List<Role> roles;

  private String mobile;

  @NotNull private UserStatus status;

  private List<Address> addresses;

  /** A hashed password. */
  @NotNull private String password;

  @Nonnegative private Double rating;

  /** The user status. */
  public enum UserStatus {
    ACTIVE,
    INACTIVE,
    TERMINATED
  }
}
