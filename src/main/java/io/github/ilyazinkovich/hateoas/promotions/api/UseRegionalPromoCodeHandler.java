package io.github.ilyazinkovich.hateoas.promotions.api;

import static ratpack.http.Status.OK;

import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import java.util.Optional;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.path.PathTokens;

public class UseRegionalPromoCodeHandler implements Handler {

  private final RegionalPromoCodes regionalPromoCodes;

  public UseRegionalPromoCodeHandler(final RegionalPromoCodes regionalPromoCodes) {
    this.regionalPromoCodes = regionalPromoCodes;
  }

  @Override
  public void handle(final Context ctx) {
    final PathTokens pathTokens = ctx.getPathTokens();
    Optional.ofNullable(pathTokens.get("region"))
        .map(Region::new)
        .ifPresent(region -> Optional.ofNullable(pathTokens.get("promoCodeId"))
            .map(PromoCodeId::new)
            .ifPresent(promoCodeId -> regionalPromoCodes.remove(region, promoCodeId)));
    ctx.getResponse().status(OK).send();
  }
}
