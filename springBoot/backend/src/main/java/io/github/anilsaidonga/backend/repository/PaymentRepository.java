package io.github.anilsaidonga.backend.repository;

import org.springframework.stereotype.Repository;

import io.github.anilsaidonga.backend.dto.PaymentRequestDto;
import io.github.anilsaidonga.backend.entity.PaymentEntity;

@Repository
public class PaymentRepository {

	public PaymentEntity getPaymentDetailsById(PaymentRequestDto paymentRequestDto) {
		
		PaymentEntity paymentEntity = executeQuery(paymentRequestDto);
		return paymentEntity;
	}

	private PaymentEntity executeQuery(PaymentRequestDto paymentRequestDto) {
		
		// connect with db and fetch the response
		
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setPaymentId(paymentRequestDto.getPaymentId());
		paymentEntity.setPaymentAmount(100.00);
		paymentEntity.setPaymentCurrency("INR");
		
		return paymentEntity;
	}

}
