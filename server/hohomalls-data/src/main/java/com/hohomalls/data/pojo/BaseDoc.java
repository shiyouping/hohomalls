package com.hohomalls.data.pojo;

import com.hohomalls.core.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The parent class of all data documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDoc extends BasePojo {}
