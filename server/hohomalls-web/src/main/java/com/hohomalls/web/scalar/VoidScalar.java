package com.hohomalls.web.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;

/**
 * The class of VoidScalar.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 18/7/2021
 */
@DgsScalar(name = "Void")
public class VoidScalar implements Coercing<Void, Void> {

  @Override
  public Void parseLiteral(Object input) {
    return null;
  }

  @Override
  public Void parseValue(Object input) {
    return null;
  }

  @Override
  public Void serialize(Object dataFetcherResult) {
    return null;
  }
}
