package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    private Long paymentTime;

    private Long paymentPrice;

    private String paymentMethod;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Long getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Long paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

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
            ", user=" + getUser() +
            "}";
    }
}
