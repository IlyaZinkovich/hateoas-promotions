package io.github.ilyazinkovich.hateoas.promotions.api.promocodes;

import static ratpack.http.Status.OK;

import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import java.util.Optional;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.path.PathTokens;

public class UsePersonalPromoCodeHandler implements Handler {

  private final PersonalPromoCodes personalPromoCodes;

  public UsePersonalPromoCodeHandler(final PersonalPromoCodes personalPromoCodes) {
    this.personalPromoCodes = personalPromoCodes;
  }

  @Override
  public void handle(final Context ctx) {
    final PathTokens pathTokens = ctx.getPathTokens();
    Optional.ofNullable(pathTokens.get("clientId"))
        .map(ClientId::new)
        .ifPresent(clientId -> Optional.ofNullable(pathTokens.get("promoCodeId"))
            .map(PromoCodeId::new)
            .ifPresent(promoCodeId -> personalPromoCodes.remove(clientId, promoCodeId)));
    ctx.getResponse().status(OK).send();
  }
}
