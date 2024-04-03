package com.nndmove.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Payment getPaymentSample1() {
        return new Payment()
            .id(1L)
            .paymentTime(1L)
            .paymentPrice(1L)
            .paymentMethod("paymentMethod1")
            .status("status1")
            .transactionId("transactionId1");
    }

    public static Payment getPaymentSample2() {
        return new Payment()
            .id(2L)
            .paymentTime(2L)
            .paymentPrice(2L)
            .paymentMethod("paymentMethod2")
            .status("status2")
            .transactionId("transactionId2");
    }

    public static Payment getPaymentRandomSampleGenerator() {
        return new Payment()
            .id(longCount.incrementAndGet())
            .paymentTime(longCount.incrementAndGet())
            .paymentPrice(longCount.incrementAndGet())
            .paymentMethod(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .transactionId(UUID.randomUUID().toString());
    }
}
