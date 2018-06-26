package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Objects;

public class PromoCodeResource {

  public final String uri;
  public final PromoCode promoCode;

  public PromoCodeResource(final String uri, final PromoCode promoCode) {
    this.uri = uri;
    this.promoCode = promoCode;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final PromoCodeResource that = (PromoCodeResource) o;
    return Objects.equals(uri, that.uri) &&
        Objects.equals(promoCode, that.promoCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uri, promoCode);
  }
}
