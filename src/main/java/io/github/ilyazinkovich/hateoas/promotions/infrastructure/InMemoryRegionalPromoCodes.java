package io.github.ilyazinkovich.hateoas.promotions.infrastructure;

import static java.util.Collections.emptySet;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;

import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import java.util.Map;
import java.util.Set;

public class InMemoryRegionalPromoCodes implements RegionalPromoCodes {

  private final Map<Region, Set<RegionalPromoCode>> promoCodes;

  public InMemoryRegionalPromoCodes(final Map<Region, Set<RegionalPromoCode>> promoCodes) {
    this.promoCodes = promoCodes;
  }

  @Override
  public void store(final Region region, final RegionalPromoCode promoCode) {
    promoCodes.putIfAbsent(region, newKeySet());
    final Set<RegionalPromoCode> regionalPromoCodes = promoCodes.get(region);
    regionalPromoCodes.add(promoCode);

  }

  @Override
  public Set<? extends PromoCode> query(final Query query) {
    return query.region().map(promoCodes::get).orElse(emptySet());
  }
}
