package io.github.ilyazinkovich.hateoas.promotions.api;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class IndexPageHandler implements Handler {

  @Override
  public void handle(final Context ctx) {
    ctx.render("Welcome to PromoCodes API!!!");
  }
}
