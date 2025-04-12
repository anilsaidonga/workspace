package io.github.anilsaidonga.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilsaidonga.backend.dto.PaymentRequestDto;
import io.github.anilsaidonga.backend.dto.PaymentResponseDto;
import io.github.anilsaidonga.backend.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@GetMapping("/{id}")
	public ResponseEntity<PaymentResponseDto> getPaymentDetailsById(@PathVariable Long id)
	{
		// map incoming data to internal request dto
		PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
		paymentRequestDto.setPaymentId(id);
		
		// pass this request dto to service layer
		PaymentResponseDto paymentResponse = paymentService.getPaymentDetailsById(paymentRequestDto);
		
		return ResponseEntity.ok(paymentResponse);
	}

}
