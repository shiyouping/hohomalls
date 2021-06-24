package com.hohomalls.app.service;

import com.hohomalls.web.property.TokenProperties;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.service.TokenServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceImplTest {

  private static String jws;

  @Test
  @Order(1)
  void testGenerate() {
    TokenProperties properties = getProperties();
    TokenService service = new TokenServiceImpl(properties);
    Optional<String> jws = service.generate("ricky@gmail.com", "ricky");
    assertThat(jws.isPresent()).isTrue();
    TokenServiceImplTest.jws = jws.get();
  }

  @Test
  @Order(2)
  void testParseEmail() {
    TokenProperties properties = getProperties();
    TokenService service = new TokenServiceImpl(properties);
    Optional<String> email = service.getEmail(jws);
    assertThat(email.isPresent()).isTrue();
    assertThat(email.get()).isEqualTo("ricky@gmail.com");
  }

  private TokenProperties getProperties() {
    TokenProperties properties = new TokenProperties();
    properties.setLifespan(24L);
    properties.setPrivateKey(
        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCT5+cLbNB7Vv5U55ePg+r2rYIwv5LdDP/inHebrwj2y97wTb31lvzRVwxOUmPG9YMYwhBMKkkqzAeBgKK6CrEWA4VKOEW6JcbdcsS5eNBVMJHISE5d6QmIYsxlBSiTpE21aNFzqm+jA76LN12AQPRG7YNLJ91WBEkzTPV3Y9VKs09iX12T+PSwLtEsEK9YNc52fxEkE8IkDCj8hKz9TgDF+mHdwq7JrcT8b74sHDGvSEbPXefuuWo+z6GUtE0I2+XJuRCu1ywqdje9gxdPb4Us+mrO2nYmZFN5NtVT+hNmnjkmutWzMJD8K135nHmsKyJ7IFzitm8o1bB0cKjwryHVAgMBAAECggEBAILs7EPbzezwg/BqlWoQD0TPMIiwfHO6lyRk9yfT2G6G51D2sGmefytcD+Og4Pv0xlh7KOwTHnOI6jgDw4hGAJ4I/ctGZsqxuAi9GFFKFbjjZpjRELXmnG6jbmUaHOIctkEYiRgHTg0SOnCH0PfAB7xlcSxtHGuRHa8Qt9U4hU06ceBEieD2hz5y4X5A7qqH42tDkPyyZ7xQiAlYeVIcrcTGr28gYKxDSyuqsPFGbV7zo1pikZccAajETU3k5upJ43StYrxKwVuBpqyaxrF7cdfpAt1lITHKUte2mELpkoJ3xh3cn3LX7BciFUVbHgL+L2yw5P0zzDFFyJRkIW67rV0CgYEA5HVkeVSfv3vnQZXnbRywSxjYZ8gchCssFI5izeCc1wZnK6Kn+Jw6Zm6CSwCOqvTlhFZHpzzO5LL75evCiw8KiFa6QJ3QyImQcG843LrTF7/mkuYUWSI7I1ogPKM/oX/dMXcmEREcS46xjJ2SoniwVTrFF8jEoQakvO6X0HrgXScCgYEApbyFblFFf9RJib9yCzqxoxI21ejlUdymW9j6LyqlNHTKDH/0KGiydjLJPXmry5aUiydrZLoZZmAJ+9bB1wUJOlRhSX2ar/pJpxmcwXqSCu0jQiDKP/zytYiYswKvAm3qvd697erRdi4YDyZr0QXOaepRlLC0XA2uPF+ARcQU3qMCgYAii7XTuv4lAGFpw3cpQqtNz8X5e4MEYVrbCOTb6NkOksNLD9+Ccm1KS++b08u/AiUqq7lOCp3ma0I39DyIto+LKkIjvzlw+YxD1C2vAvkkoDoHgI8XI1v57ojtYmoey6zw6+lvrzyuGDe04abotoNDgA2JmSxShSSoBRQjzFDdBQKBgDCiwgU9lEeO5IWnyK/C6Z4RGkZrsd/0AF2zrrdorFJYc3J3mg7Bqp1FCgkgS2nTJoQvbTSB6DJCvKtKuld/AtY7LNGEKoC50iNXQMkGTxUlwdMGDFP6xr6+9xCRGQp9dwWA9/t5jT5BCI5pl/oe2hP6zzXzJPpeiWLuI5ZVlEpBAoGBAI4CNpwM51jH5PUgHPK2hygHYG7nlxqjM54eqhY+TgxAveyl3tTZto+ZqtRlRDxTp8l8+PrivSA6cDDGL3yL238szcOjELq7LV01IGnYHbOH0AgwY5vP3yhpBML/VYzF5dafkOa/nJSH6a7QCTTV6exY4iAt1e+9Ckg/4TEAV3Qy");
    properties.setPublicKey(
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk+fnC2zQe1b+VOeXj4Pq9q2CML+S3Qz/4px3m68I9sve8E299Zb80VcMTlJjxvWDGMIQTCpJKswHgYCiugqxFgOFSjhFuiXG3XLEuXjQVTCRyEhOXekJiGLMZQUok6RNtWjRc6pvowO+izddgED0Ru2DSyfdVgRJM0z1d2PVSrNPYl9dk/j0sC7RLBCvWDXOdn8RJBPCJAwo/ISs/U4Axfph3cKuya3E/G++LBwxr0hGz13n7rlqPs+hlLRNCNvlybkQrtcsKnY3vYMXT2+FLPpqztp2JmRTeTbVU/oTZp45JrrVszCQ/Ctd+Zx5rCsieyBc4rZvKNWwdHCo8K8h1QIDAQAB");
    return properties;
  }
}
