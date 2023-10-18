package com.ingress.ms.subscription.dao.repository;

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @Query(value = "SELECT * FROM subscriptions WHERE expire_date >= now()", nativeQuery = true)
    Optional<List<SubscriptionEntity>> findAllByExpireDateEnded();

}
