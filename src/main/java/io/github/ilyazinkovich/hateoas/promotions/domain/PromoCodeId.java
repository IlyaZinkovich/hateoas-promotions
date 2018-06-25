package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Objects;
import java.util.UUID;

public class PromoCodeId {

  private final String uid;

  public PromoCodeId(final String uid) {
    this.uid = uid;
  }

  public static PromoCodeId next() {
    return new PromoCodeId(UUID.randomUUID().toString());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final PromoCodeId that = (PromoCodeId) o;
    return Objects.equals(uid, that.uid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid);
  }

  @Override
  public String toString() {
    return uid;
  }
}
