package io.github.ilyazinkovich.hateoas.promotions.api;

import static ratpack.http.Status.OK;
import static ratpack.jackson.Jackson.jsonNode;

import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import java.util.Optional;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class RegionalPromoCodeCreationHandler implements Handler {

  private final RegionalPromoCodes regionalPromoCodes;

  public RegionalPromoCodeCreationHandler(final RegionalPromoCodes regionalPromoCodes) {
    this.regionalPromoCodes = regionalPromoCodes;
  }

  @Override
  public void handle(final Context ctx) {
    ctx.parse(jsonNode()).map(CreateRegionalPromoCode::fromJson)
        .next(handleCreateRegionalPromoCodeCommand(regionalPromoCodes))
        .then(command -> ctx.getResponse().status(OK).send());
  }

  private static Action<Optional<CreateRegionalPromoCode>> handleCreateRegionalPromoCodeCommand(
      final RegionalPromoCodes regionalPromoCodes) {
    return optionalCommand -> optionalCommand.ifPresent(command -> {
      final RegionalPromoCode promoCode = new RegionalPromoCode(PromoCodeId.next());
      regionalPromoCodes.store(command.region, promoCode);
    });
  }
}
