package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Set;

public interface PromoCodes {

  Set<? extends PromoCodeResource> query(final Query query);
}
