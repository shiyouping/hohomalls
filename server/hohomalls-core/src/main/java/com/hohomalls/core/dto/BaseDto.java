package com.hohomalls.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The base class for all DTOs.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/4/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto implements Serializable {
  private static final long serialVersionUID = -1187682743392836578L;
  private String id;
}
