package com.automotiveserviceplatform.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;
    private static final long OTP_VALIDITY_MINUTES = 5; // OTP valid for 5 minutes

    // In-memory storage for OTPs: phoneNumber -> OtpDetails
    private final Map<String, OtpDetails> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String phoneNumber) {
        String otp = String.format("%0" + OTP_LENGTH + "d", new Random().nextInt((int) Math.pow(10, OTP_LENGTH)));
        otpStore.put(phoneNumber, new OtpDetails(otp, LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES)));
        // In a real application, you would send this OTP via SMS/email
        System.out.println("Generated OTP for " + phoneNumber + ": " + otp); // For testing purposes
        return otp;
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        OtpDetails storedOtpDetails = otpStore.get(phoneNumber);

        if (storedOtpDetails == null) {
            return false; // No OTP found for this phone number
        }

        if (storedOtpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStore.remove(phoneNumber); // OTP expired
            return false;
        }

        if (storedOtpDetails.getOtp().equals(otp)) {
            otpStore.remove(phoneNumber); // OTP successfully validated, remove it
            return true;
        }
        return false;
    }

    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OtpDetails(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
