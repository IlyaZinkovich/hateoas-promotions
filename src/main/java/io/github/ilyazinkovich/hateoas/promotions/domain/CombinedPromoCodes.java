package io.github.ilyazinkovich.hateoas.promotions.domain;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

public class CombinedPromoCodes {

  private final Set<PromoCodes> promoCodes;

  public CombinedPromoCodes(final Set<PromoCodes> promoCodes) {
    this.promoCodes = promoCodes;
  }

  public Set<? extends PromoCodeResource> query(final Query query) {
    return promoCodes.stream()
        .map(promos -> promos.query(query))
        .flatMap(Set::stream)
        .collect(toSet());
  }
}
