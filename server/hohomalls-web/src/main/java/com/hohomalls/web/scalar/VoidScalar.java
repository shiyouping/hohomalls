package com.hohomalls.web.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

/**
 * The class of VoidScalar.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 18/7/2021
 */
@DgsScalar(name = "Void")
public class VoidScalar implements Coercing<Void, Void> {

  @Override
  public Void parseLiteral(Object input) throws CoercingParseLiteralException {
    return null;
  }

  @Override
  public Void parseValue(Object input) throws CoercingParseValueException {
    return null;
  }

  @Override
  public Void serialize(Object dataFetcherResult) throws CoercingSerializeException {
    return null;
  }
}
