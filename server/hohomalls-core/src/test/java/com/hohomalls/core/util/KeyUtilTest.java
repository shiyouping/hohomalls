package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class of KeyUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 4/6/2021
 */
public class KeyUtilTest {

  @Test
  public void testToPrivateKey() {
    Key key =
        KeyUtil.toPrivateKey(
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+aYLjh2vgvjBHedsNFHz+5BPSUdqFeNOJ4JVZvPo/Nirgj9aL4g4QIr7yVu22hCUgV+WYl9k1XjxudBesxKIKWdSCTuzfP03pdkJajbb1Qu9UK3Lyn/JK5sdlWvQvI2NG5wV8VPhfW/TuLNwaaK1m92j09Yey6gtJqeaGxeiWOJaR+cCAH2W4qEBE/ag0lcLxQ9tUR/XzSUZmCdFbUaC3LBzU+k4lleVqyfbZqdgkMsMsh2ZzUjparTWBR9Pqfym8GbFyOhZ3+0GOEF1ooYHmVyJHlX1zB0Z5noRv0VQ6UxDMT4+r049wnUQ3vneTjnh8Wq0ti8os77ZX2CkeuEYhAgMBAAECggEBAIwxKwlSn36tCObXNQyAVTdlvaJQWc+DklwlWlDj7muz+gW0YYZEPY5rxqKulf6XdK5HiOe8hVPhuiSonPW7IIL2eJwoeAY2maqWQ2xggqA5U/RouQ0h7kZve8eIG9jI+UMuvL854FR1Y/QoWzWmdGUnK8OPMwXrg7wMt4nrFL/kSqP+RV2WsgRRaf6kLe7JqSr1X+N8fPZyb8blehm9r/YcnC0HWDYxx3CBq5kf57okPI3CVnGM8m2xcp31628noQZ8JGqhFNeQe4ab1Ct/SRVq8gMtEfOIGTegBIh22P3x8hgUjq4ffzlAehGojLFQ9aw4qWDtqKjeqwN7YPcskbECgYEA+Oo3rD0vaiF/skq8BOTcnDx/R4ouKKhhaZWUqkO/AV7NGM+llGyYsfnAsqFrPk2jM9aOmdva1KEiqlVc7HVAwCSwqRT204pv6dPGzzuBAFblm1mdj7vTEOmhCEokhXH1mBcM4WEznoxOdpDOplEb4E5ZFRtNyTlOq9B+/SPTF8sCgYEAw9T/mCKduozutm0efcvqg3wtP/rCudvlpMgGn6hvR2cTLXrRazxdB8wShMh+F6bq8srYy6MkRW6mUkzJmXui9gFzNCxA3b0GIzVQcBDiLJryEj196vQfBpmOAWA1V4Lj5BSIZsJpM9fPSNASe0eYhfx7rNw8zo6zgTuSyXRRpEMCgYBv/YEa31Rr3bKEVxeM5CNVr+pcF5F4/XkGdiQzDtPrG+oPFa2bv9hbitDmDbYgi/G3qvrxwncMX4snM8zhHkgJqvhIuoPq3UhI3d1+83iw3Gflo0Y+mvT1kQfAoQsZdxJWYViMNp1w4hOTSMeREMEIBHgUBPrUZBI/P0lDP1EETQKBgQCnoJFQhoEBCFXlba4l/AC3n/mTxHAea648yMNystjeA8sUqQbNhhQT8oj/hCb60/RU83FzjigkDrzrwbLnYD9z2HXmeOLla0HvrVk3NByGFfOTzXMVRPWxzosRfmrqh63OtAy1+T/YEqjqKMFJKVXTivXKV5LtNJVOz9GsA5dGxQKBgBKvVcKUqPi0OxXamP3zz7PJdrofc/qtCDZhdlaz9ZRKo0KKdyYmUnwWyUY/Jz/1A/oGJeM9M8ydGVocylyP0EbdB2LMocOu1Bccrw/UXrUApOLfVHLKvlX1U45rLrgXl+SGm8bWSFI23/RjCotdTf60Foj81ZuGGelR9vQ7vNkp");
    assertThat(key).isNotNull();
    assertThat(key.getAlgorithm()).isEqualTo("RSA");
  }

  @Test
  public void testToPublicKey() {
    Key key =
        KeyUtil.toPublicKey(
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvmmC44dr4L4wR3nbDRR8/uQT0lHahXjTieCVWbz6PzYq4I/Wi+IOECK+8lbttoQlIFflmJfZNV48bnQXrMSiClnUgk7s3z9N6XZCWo229ULvVCty8p/ySubHZVr0LyNjRucFfFT4X1v07izcGmitZvdo9PWHsuoLSanmhsXoljiWkfnAgB9luKhARP2oNJXC8UPbVEf180lGZgnRW1Ggtywc1PpOJZXlasn22anYJDLDLIdmc1I6Wq01gUfT6n8pvBmxcjoWd/tBjhBdaKGB5lciR5V9cwdGeZ6Eb9FUOlMQzE+Pq9OPcJ1EN753k454fFqtLYvKLO+2V9gpHrhGIQIDAQAB");
    assertThat(key).isNotNull();
    assertThat(key.getAlgorithm()).isEqualTo("RSA");
  }
}
