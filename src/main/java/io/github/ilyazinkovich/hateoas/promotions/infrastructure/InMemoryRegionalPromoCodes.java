package io.github.ilyazinkovich.hateoas.promotions.infrastructure;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;
import static java.util.stream.Collectors.toSet;

import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeResource;
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
  public void remove(final Region region, final PromoCodeId promoCodeId) {
    promoCodes.get(region).stream()
        .filter(promoCode -> promoCode.id().equals(promoCodeId))
        .findAny().ifPresent(promoCode -> promoCodes.get(region).remove(promoCode));
  }

  @Override
  public Set<PromoCodeResource> query(final Query query) {
    return query.region().map(this::queryByRegion).orElse(emptySet());
  }

  private Set<PromoCodeResource> queryByRegion(final Region region) {
    return promoCodes.getOrDefault(region, emptySet()).stream()
        .map(promoCode -> new PromoCodeResource(toUri(region, promoCode), promoCode))
        .collect(toSet());
  }

  private String toUri(final Region region, final RegionalPromoCode promoCode) {
    return format("/promocodes/regional/%s/%s", region.name, promoCode.id());
  }
}
