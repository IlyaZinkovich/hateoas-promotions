package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Objects;
import java.util.UUID;

public class ClientId {

  public final String uid;

  public ClientId(final String uid) {
    this.uid = uid;
  }

  public static ClientId next() {
    return new ClientId(UUID.randomUUID().toString());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ClientId clientId = (ClientId) o;
    return Objects.equals(uid, clientId.uid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid);
  }
}
