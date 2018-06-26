package io.github.ilyazinkovich.hateoas.promotions.domain;

public interface PersonalPromoCodes extends PromoCodes {

  void store(final ClientId clientId, final PersonalPromoCode promoCode);

  void remove(final ClientId clientId, final PromoCodeId promoCodeId);
}
