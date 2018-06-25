package io.github.ilyazinkovich.hateoas.promotions;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryPersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryRegionalPromoCodes;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class PromoCodesTest {

  @Test
  void storeAndQueryPersonalPromoCodes() {
    final PromoCodeId promoCodeId = PromoCodeId.next();
    final ClientId clientId = ClientId.next();
    final PersonalPromoCode personalPromoCode = new PersonalPromoCode(promoCodeId);
    final PersonalPromoCodes promoCodes = new InMemoryPersonalPromoCodes(new ConcurrentHashMap<>());

    promoCodes.store(clientId, personalPromoCode);

    final Set<? extends PromoCode> queriedPromoCodes =
        promoCodes.query(new Query().withClientId(Optional.of(clientId)));
    final Set<PromoCode> expected = new HashSet<>();
    expected.add(personalPromoCode);
    assertThat(queriedPromoCodes).isEqualTo(expected);
  }

  @Test
  void storeAndQueryRegionalPromoCodes() {
    final PromoCodeId promoCodeId = PromoCodeId.next();
    final Region region = new Region("UAE");
    final RegionalPromoCode regionalPromoCode = new RegionalPromoCode(promoCodeId);
    final RegionalPromoCodes promoCodes = new InMemoryRegionalPromoCodes(new ConcurrentHashMap<>());

    promoCodes.store(region, regionalPromoCode);

    final Set<? extends PromoCode> queriedPromoCodes =
        promoCodes.query(new Query().withRegion(Optional.of(region)));
    final Set<PromoCode> expected = new HashSet<>();
    expected.add(regionalPromoCode);
    assertThat(queriedPromoCodes).isEqualTo(expected);
  }

  @Test
  void storeAndQueryCombinedPromoCodes() throws ExecutionException, InterruptedException {
    final PersonalPromoCodes personalPromoCodes =
        new InMemoryPersonalPromoCodes(new ConcurrentHashMap<>());
    final RegionalPromoCodes regionalPromoCodes =
        new InMemoryRegionalPromoCodes(new ConcurrentHashMap<>());
    final Set<PromoCodes> promoCodes = Stream.of(personalPromoCodes, regionalPromoCodes)
        .collect(toSet());
    final CombinedPromoCodes combinedPromoCodes = new CombinedPromoCodes(promoCodes);

    final PromoCodeId personalPromoCodeId = PromoCodeId.next();
    final ClientId clientId = ClientId.next();
    final PersonalPromoCode personalPromoCode = new PersonalPromoCode(personalPromoCodeId);
    personalPromoCodes.store(clientId, personalPromoCode);

    final PromoCodeId regionalPromoCodeId = PromoCodeId.next();
    final Region region = new Region("UAE");
    final RegionalPromoCode regionalPromoCode = new RegionalPromoCode(regionalPromoCodeId);
    regionalPromoCodes.store(region, regionalPromoCode);

    final Set<? extends PromoCode> queriedPromoCodes = combinedPromoCodes
        .query(new Query().withClientId(Optional.of(clientId)).withRegion(Optional.of(region)))
        .get();
    assertThat(queriedPromoCodes)
        .isEqualTo(Stream.of(personalPromoCode, regionalPromoCode).collect(toSet()));
  }
}
