package com.hohomalls.core.util;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;

/**
 * BeanUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public final class BeanUtil {

  private static final BeanUtilsBean3 nonNullBean = new BeanUtilsBean3();
  private static final BeanUtilsBean2 defaultBean = new BeanUtilsBean2();

  private BeanUtil() {}

  /**
   * Delegate of {@linkplain BeanUtilsBean3#copyProperties(Object, Object)}.
   *
   * @param destination Destination bean whose properties are modified
   * @param source Source bean whose properties are retrieved
   */
  @SneakyThrows
  public static void copyAllProperties(Object destination, Object source) {
    BeanUtil.defaultBean.copyProperties(destination, source);
  }

  /**
   * See {@linkplain BeanUtilsBean#copyProperties(Object, Object)}. The only difference is that this
   * implementation only copies non-null properties instead of all properties from source bean
   *
   * @param destination Destination bean whose properties are modified
   * @param source Source bean whose properties are retrieved
   */
  @SneakyThrows
  public static void copyNonnullProperties(Object destination, Object source) {
    BeanUtil.nonNullBean.copyProperties(destination, source);
  }

  private static class BeanUtilsBean3 extends BeanUtilsBean2 {
    @SneakyThrows
    @Override
    public void copyProperty(Object bean, String name, Object value) {
      if (value == null) {
        // Skip null properties
        return;
      }

      super.copyProperty(bean, name, value);
    }
  }
}
