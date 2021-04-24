module com.hohomalls.core {
  exports com.hohomalls.core.constant;
  exports com.hohomalls.core.util;

  requires com.google.common;
  requires org.checkerframework.checker.qual;
  requires static transitive lombok;
}
