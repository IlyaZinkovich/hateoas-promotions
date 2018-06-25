package io.github.ilyazinkovich.hateoas.promotions.domain;

public interface PersonalPromoCodes extends PromoCodes {

  void store(ClientId clientId, PersonalPromoCode promoCode);
}
