package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Objects;

public class RegionalPromoCode implements PromoCode {

  private final PromoCodeId id;

  public RegionalPromoCode(final PromoCodeId id) {
    this.id = id;
  }

  @Override
  public PromoCodeId id() {
    return id;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RegionalPromoCode that = (RegionalPromoCode) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
