package io.github.ilyazinkovich.hateoas.promotions.api.promocodes;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;

import ratpack.func.Action;
import ratpack.handling.Chain;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class PromoCodesApi implements Action<Chain> {

  private final PersonalPromoCodes personalPromoCodes;
  private final RegionalPromoCodes regionalPromoCodes;
  private final CombinedPromoCodes combinedPromoCodes;
  private final ObjectMapper mapper;

  public PromoCodesApi(final PersonalPromoCodes personalPromoCodes,
      final RegionalPromoCodes regionalPromoCodes,
      final ObjectMapper mapper) {
    this.personalPromoCodes = personalPromoCodes;
    this.regionalPromoCodes = regionalPromoCodes;
    this.combinedPromoCodes =
        new CombinedPromoCodes(Stream.of(personalPromoCodes, regionalPromoCodes).collect(toSet()));
    this.mapper = mapper;
  }

  @Override
  public void execute(final Chain chain) {
    chain
        .post("promocodes/personal", new PersonalPromoCodeCreationHandler(personalPromoCodes))
        .put("promocodes/personal/:clientId/:promoCodeId",
            new UsePersonalPromoCodeHandler(personalPromoCodes))
        .post("promocodes/regional", new RegionalPromoCodeCreationHandler(regionalPromoCodes))
        .put("promocodes/regional/:region/:promoCodeId",
            new UseRegionalPromoCodeHandler(regionalPromoCodes))
        .get("promocodes", new PromoCodesQueryHandler(combinedPromoCodes, mapper));
  }
}
