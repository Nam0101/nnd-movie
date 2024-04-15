package com.nndmove.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "payment_time", nullable = false)
    private Long paymentTime;

    @NotNull
    @Column(name = "payment_price", nullable = false)
    private Long paymentPrice;

    @NotNull
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public Payment paymentTime(Long paymentTime) {
        this.setPaymentTime(paymentTime);
        return this;
    }

    public Payment paymentPrice(Long paymentPrice) {
        this.setPaymentPrice(paymentPrice);
        return this;
    }

    public Payment paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public Payment status(String status) {
        this.setStatus(status);
        return this;
    }

    public Payment transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public Payment user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + getId() +
                ", paymentTime=" + getPaymentTime() +
                ", paymentPrice=" + getPaymentPrice() +
                ", paymentMethod='" + getPaymentMethod() + "'" +
                ", status='" + getStatus() + "'" +
                ", transactionId='" + getTransactionId() + "'" +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public @NotNull Long getPaymentTime() {
        return this.paymentTime;
    }

    public @NotNull Long getPaymentPrice() {
        return this.paymentPrice;
    }

    public @NotNull String getPaymentMethod() {
        return this.paymentMethod;
    }

    public @NotNull String getStatus() {
        return this.status;
    }

    public @NotNull String getTransactionId() {
        return this.transactionId;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentTime(@NotNull Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setPaymentPrice(@NotNull Long paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public void setPaymentMethod(@NotNull String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatus(@NotNull String status) {
        this.status = status;
    }

    public void setTransactionId(@NotNull String transactionId) {
        this.transactionId = transactionId;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
