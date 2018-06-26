package io.github.ilyazinkovich.hateoas.promotions.api.content;

import ratpack.func.Action;
import ratpack.handling.Chain;

public class StaticContentApi implements Action<Chain> {

  @Override
  public void execute(final Chain chain) throws Exception {
    chain.files(files -> files.dir("public").indexFiles("index.html"));
  }
}
