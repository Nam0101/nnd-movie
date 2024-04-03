package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.nndmove.app.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private Long paymentTime;

    @NotNull
    private Long paymentPrice;

    @NotNull
    private String paymentMethod;

    @NotNull
    private String status;

    @NotNull
    private String transactionId;

    private UserDTO user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", paymentTime=" + getPaymentTime() +
            ", paymentPrice=" + getPaymentPrice() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", status='" + getStatus() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
