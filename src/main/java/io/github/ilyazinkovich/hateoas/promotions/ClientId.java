package io.github.ilyazinkovich.hateoas.promotions;

import java.util.Objects;
import java.util.UUID;

public class ClientId {

  private final String uid;

  public ClientId(final String uid) {
    this.uid = uid;
  }

  static ClientId next() {
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
