package io.github.ilyazinkovich.hateoas.promotions;

public interface PersonalPromoCodes extends PromoCodes {

  void store(ClientId clientId, PersonalPromoCode promoCode);
}
