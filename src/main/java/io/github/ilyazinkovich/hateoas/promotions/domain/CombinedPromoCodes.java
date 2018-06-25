package io.github.ilyazinkovich.hateoas.promotions.domain;

import static java.util.Collections.emptySet;
import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CombinedPromoCodes {

  private final Set<PromoCodes> promoCodes;

  public CombinedPromoCodes(final Set<PromoCodes> promoCodes) {
    this.promoCodes = promoCodes;
  }

  public CompletableFuture<? extends Set<? extends PromoCode>> query(final Query query) {
    return promoCodes.stream()
        .map(promos -> CompletableFuture.supplyAsync(() -> promos.query(query)))
        .reduce((left, right) -> left.thenCombine(right, (leftSet, rightSet) -> {
          leftSet.addAll(rightSet);
          return leftSet;
        })).orElse(completedFuture(emptySet()));
  }
}
