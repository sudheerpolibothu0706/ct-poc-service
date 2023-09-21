package com.cpt.testController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


	
//	@GetMapping("/hello")
//	public String hello() {
//		return "Hello Spring Boot";
//		
//	}
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RequestHeader;
	import org.springframework.web.bind.annotation.RestController;

	import javax.crypto.Mac;
	import javax.crypto.spec.SecretKeySpec;
	import java.security.InvalidKeyException;
	import java.security.NoSuchAlgorithmException;
	import java.util.Base64;
	
	@RestController
	public class TestController {
	    private static final String SECRET_KEY = "yourSecretKey";

	    @GetMapping("/hello")
	    public String hello(@RequestHeader("X-HMAC-Signature") String signature,
	                        @RequestHeader("X-Timestamp") long timestamp) {
	        // Construct canonical representation of the request
	        String canonicalRequest = "GET\n/hello\n" + timestamp; // Add more data if needed

	        try {
	            // Calculate the expected HMAC-SHA-256 signature
	            String expectedSignature = calculateHmacSha256(canonicalRequest, SECRET_KEY);

	            // Compare the received signature with the expected signature
	            if (expectedSignature.equals(signature)) {
	                return "Hello Spring Boot - Request is authentic.";
	            } else {
	                return "Unauthorized - Request is not authentic.";
	            }
	        } catch (Exception e) {
	            return "Error processing the request.";
	        }
	    }

	    private String calculateHmacSha256(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
	        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
	        hmacSha256.init(secretKeySpec);
	        byte[] signatureBytes = hmacSha256.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(signatureBytes);
	    }
	

	
	@GetMapping("/bye")
	public String bye() {
		return "bye Spring Boot";
		
	}
}
