package com.ingress.ms.subscription.dao.repository;

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    @Query("SELECT s FROM SubscriptionEntity s WHERE s.expireDate >= current_timestamp")
    List<SubscriptionEntity> findAllByExpireDateEnded();
}
