package io.github.ilyazinkovich.hateoas.promotions.infrastructure;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;
import static java.util.stream.Collectors.toSet;

import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeResource;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import java.util.Map;
import java.util.Set;

public class InMemoryPersonalPromoCodes implements PersonalPromoCodes {

  private final Map<ClientId, Set<PersonalPromoCode>> promoCodes;

  public InMemoryPersonalPromoCodes(final Map<ClientId, Set<PersonalPromoCode>> promoCodes) {
    this.promoCodes = promoCodes;
  }

  @Override
  public void store(final ClientId clientId, final PersonalPromoCode promoCode) {
    promoCodes.putIfAbsent(clientId, newKeySet());
    final Set<PersonalPromoCode> personalPromoCodes = promoCodes.get(clientId);
    personalPromoCodes.add(promoCode);
  }

  @Override
  public void remove(final ClientId clientId, final PromoCodeId promoCodeId) {
    promoCodes.get(clientId).stream()
        .filter(promoCode -> promoCode.id().equals(promoCodeId))
        .findAny().ifPresent(promoCode -> promoCodes.get(clientId).remove(promoCode));
  }

  @Override
  public Set<PromoCodeResource> query(final Query query) {
    return query.clientId().map(this::queryByClientId).orElse(emptySet());
  }

  private Set<PromoCodeResource> queryByClientId(final ClientId clientId) {
    return promoCodes.getOrDefault(clientId, emptySet()).stream()
        .map(promoCode -> new PromoCodeResource(toUri(clientId, promoCode), promoCode))
        .collect(toSet());
  }

  private String toUri(final ClientId clientId, final PersonalPromoCode promoCode) {
    return format("/promocodes/personal/%s/%s", clientId.uid, promoCode.id());
  }
}
