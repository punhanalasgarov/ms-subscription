package com.ingress.ms.subscription.dao.entity;

import com.ingress.ms.subscription.model.enums.SubscriptionStatus;
import com.ingress.ms.subscription.model.enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "subscriptions")
@NoArgsConstructor
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long userId;
    private Long productId;

    @Enumerated(STRING)
    private SubscriptionType type;

    @Enumerated(STRING)
    private SubscriptionStatus status;

    @CreationTimestamp
    private LocalDateTime createDate;

    private LocalDateTime expireDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionEntity subscriptionEntity = (SubscriptionEntity) o;
        return Objects.equals(id, subscriptionEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
