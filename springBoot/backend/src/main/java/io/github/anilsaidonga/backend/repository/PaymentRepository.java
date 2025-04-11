package io.github.anilsaidonga.backend.repository;

import io.github.anilsaidonga.backend.dto.PaymentRequest;
import io.github.anilsaidonga.backend.entity.PaymentEntity;

public class PaymentRepository {

	public PaymentEntity getPaymentDetailsById(PaymentRequest paymentRequest) {
		
		PaymentEntity paymentEntity = executeQuery(paymentRequest);
		return paymentEntity;
	}

	private PaymentEntity executeQuery(PaymentRequest paymentRequest) {
		
	}

}
