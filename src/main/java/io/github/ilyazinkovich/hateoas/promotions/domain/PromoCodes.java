package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Set;

public interface PromoCodes {

  Set<PromoCodeResource> query(final Query query);
}
