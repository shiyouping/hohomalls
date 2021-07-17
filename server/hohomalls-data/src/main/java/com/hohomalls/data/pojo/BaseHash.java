package com.hohomalls.data.pojo;

import com.hohomalls.core.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The class of BaseHash.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 16/7/2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseHash extends BasePojo implements Serializable {
  private static final long serialVersionUID = 4251929247232799479L;
}
