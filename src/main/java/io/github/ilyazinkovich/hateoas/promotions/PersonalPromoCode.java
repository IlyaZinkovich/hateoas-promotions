package io.github.ilyazinkovich.hateoas.promotions;

import java.util.Objects;

public class PersonalPromoCode implements PromoCode {

  private final PromoCodeId id;

  public PersonalPromoCode(final PromoCodeId id) {
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
    final PersonalPromoCode that = (PersonalPromoCode) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
