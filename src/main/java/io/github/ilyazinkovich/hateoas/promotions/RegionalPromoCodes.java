package io.github.ilyazinkovich.hateoas.promotions;

public interface RegionalPromoCodes extends PromoCodes {

  void store(final Region region, final RegionalPromoCode regionalPromoCode);
}
