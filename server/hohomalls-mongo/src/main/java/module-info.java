module com.hohomalls.mongo {
  exports com.hohomalls.mongo.document;
  exports com.hohomalls.mongo.repository;

  requires com.google.common;
  requires java.validation;
  requires org.checkerframework.checker.qual;
  requires spring.boot;
  requires spring.context;
  requires spring.data.commons;
  requires spring.data.mongodb;
  requires transitive com.hohomalls.core;
}
