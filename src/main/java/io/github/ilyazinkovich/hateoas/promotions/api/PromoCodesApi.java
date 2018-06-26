package io.github.ilyazinkovich.hateoas.promotions.api;

import static java.util.stream.Collectors.toSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryPersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryRegionalPromoCodes;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;

public class PromoCodesApi {

  public static void main(String[] args) throws Exception {
    final PersonalPromoCodes personalPromoCodes =
        new InMemoryPersonalPromoCodes(new ConcurrentHashMap<>());
    final RegionalPromoCodes regionalPromoCodes =
        new InMemoryRegionalPromoCodes(new ConcurrentHashMap<>());
    final CombinedPromoCodes combinedPromoCodes =
        new CombinedPromoCodes(Stream.of(personalPromoCodes, regionalPromoCodes).collect(toSet()));
    final ObjectMapper mapper = new ObjectMapper();
    final Action<Chain> actionChain = chain -> chain
        .get(new IndexPageHandler())
        .post("promocodes/personal", new PersonalPromoCodeCreationHandler(personalPromoCodes))
        .put("promocodes/personal/:clientId/:promoCodeId",
            new UsePersonalPromoCodeHandler(personalPromoCodes))
        .post("promocodes/regional", new RegionalPromoCodeCreationHandler(regionalPromoCodes))
        .get("promocodes", new PromoCodesQueryHandler(combinedPromoCodes, mapper));
    RatpackServer.start(server -> server.handlers(actionChain));
  }
}
