package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Objects;

public class Region {

  public final String name;

  public Region(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Region region = (Region) o;
    return Objects.equals(name, region.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
