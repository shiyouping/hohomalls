package com.hohomalls.web.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;
import org.jetbrains.annotations.NotNull;

/**
 * The class of VoidScalar.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 18/7/2021
 */
@DgsScalar(name = "Void")
public class VoidScalar implements Coercing<Void, Void> {

  @Override
  @NotNull
  public Void parseLiteral(@NotNull Object input) {
    return null;
  }

  @Override
  @NotNull
  public Void parseValue(@NotNull Object input) {
    return null;
  }

  @Override
  public Void serialize(@NotNull Object dataFetcherResult) {
    return null;
  }
}
