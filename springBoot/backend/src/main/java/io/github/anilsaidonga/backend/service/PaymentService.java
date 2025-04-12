package io.github.anilsaidonga.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilsaidonga.backend.dto.PaymentRequestDto;
import io.github.anilsaidonga.backend.dto.PaymentResponseDto;
import io.github.anilsaidonga.backend.entity.PaymentEntity;
import io.github.anilsaidonga.backend.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository paymentRepository;

	public PaymentResponseDto getPaymentDetailsById(PaymentRequestDto paymentRequestDto) {
		
		PaymentEntity paymentEntity = paymentRepository.getPaymentDetailsById(paymentRequestDto);
		
		// map it to payment response object
		PaymentResponseDto paymentResponseDto = mapEntityToResponseDto(paymentEntity);
		
		return paymentResponseDto;
		
	}

	private PaymentResponseDto mapEntityToResponseDto(PaymentEntity paymentEntity) {
		
		PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
		paymentResponseDto.setPaymentId(paymentEntity.getPaymentId());
		paymentResponseDto.setPaymentAmount(paymentEntity.getPaymentAmount());
		paymentResponseDto.setPaymentCurrency(paymentEntity.getPaymentCurrency());
		
		return paymentResponseDto;
		
	}

}
