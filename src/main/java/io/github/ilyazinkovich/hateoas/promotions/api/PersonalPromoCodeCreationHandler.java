package io.github.ilyazinkovich.hateoas.promotions.api;

import static ratpack.http.Status.OK;
import static ratpack.jackson.Jackson.jsonNode;

import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import java.util.Optional;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class PersonalPromoCodeCreationHandler implements Handler {

  private final PersonalPromoCodes personalPromoCodes;

  public PersonalPromoCodeCreationHandler(final PersonalPromoCodes personalPromoCodes) {
    this.personalPromoCodes = personalPromoCodes;
  }

  @Override
  public void handle(final Context ctx) {
    ctx.parse(jsonNode()).map(CreatePersonalPromoCode::fromJson)
        .next(handleCreatePersonalPromoCodeCommand(personalPromoCodes))
        .then(command -> ctx.getResponse().status(OK).send());
  }

  private Action<Optional<CreatePersonalPromoCode>> handleCreatePersonalPromoCodeCommand(
      final PersonalPromoCodes personalPromoCodes) {
    return optionalCommand -> optionalCommand.ifPresent(command -> {
      final PersonalPromoCode promoCode = new PersonalPromoCode(PromoCodeId.next());
      personalPromoCodes.store(command.clientId, promoCode);
    });
  }
}
