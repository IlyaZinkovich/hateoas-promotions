package io.github.ilyazinkovich.hateoas.promotions.infrastructure;

import static java.util.Collections.emptySet;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;

import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
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
  public Set<? extends PromoCode> query(final Query query) {
    return query.clientId().map(promoCodes::get).orElse(emptySet());
  }
}
