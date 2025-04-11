package io.github.anilsaidonga.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilsaidonga.backend.dto.PaymentRequest;
import io.github.anilsaidonga.backend.dto.PaymentResponse;
import io.github.anilsaidonga.backend.entity.PaymentEntity;
import io.github.anilsaidonga.backend.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository paymentRepository;

	public PaymentResponse getPaymentDetailsById(PaymentRequest paymentRequest) {
		
		PaymentEntity paymentEntity = paymentRepository.getPaymentDetailsById(paymentRequest);
		
		// map it to payment response object
		PaymentResponse paymentResponse = mapEntityToResponseDto(paymentEntity);
		
		return paymentResponse;
		
	}

	private PaymentResponse mapEntityToResponseDto(PaymentEntity paymentEntity) {
		
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setPaymentId(paymentEntity.getPaymentId());
		paymentResponse.setPaymentAmount(paymentEntity.getPaymentAmount());
		paymentResponse.setPaymentCurrency(paymentEntity.getPaymentCurrency());
		
		return paymentResponse;
		
	}

}
