package io.github.ilyazinkovich.hateoas.promotions.domain;

public interface RegionalPromoCodes extends PromoCodes {

  void store(final Region region, final RegionalPromoCode regionalPromoCode);

  void remove(final Region region, final PromoCodeId promoCodeId);
}
