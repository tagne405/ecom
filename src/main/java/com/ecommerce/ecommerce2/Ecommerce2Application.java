package com.ecommerce.ecommerce2;

import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.ecommerce2.Service.CustomerService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.management.ServiceNotFoundException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.random.RandomGenerator;

@SpringBootApplication
public class Ecommerce2Application {
	private static final String API_URL = "https://api.notchpay.co/payments/initialize";
	private static final String PUBLIC_KEY = "sb.nQIQz1gdy2HAiNij0W9OR9fp2SLBFmO4S7e84dRANvh2i1brmgAfjiyMWxxVSQ2xJ6kYFBT8sGXbFORikFu6tzqQqRCe6c8t9CUn1blKPiMBilr2SeTqMUXE8e82U";
	public static void main(String[] args) throws IOException {

//		try {
//			URL url = new URL(API_URL);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Authorization", PUBLIC_KEY);
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			String email = "customer@email.com";
//			int amount = 1000;
//			String currency = "XAF";
//			String description = "Payment description";
//			String reference = "your_unique_reference";
//
//			String postData = String.format("{ \"email\": \"%s\", \"amount\": %d, \"currency\": \"%s\", \"description\": \"%s\", \"reference\": \"%s\" }",
//					email, amount, currency, description, reference);
//
//			connection.setDoOutput(true);
//			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//			outputStream.writeBytes(postData);
//			outputStream.flush();
//			outputStream.close();
//
//			int responseCode = connection.getResponseCode();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String line;
//			StringBuffer response = new StringBuffer();
//			while ((line = reader.readLine()) != null) {
//				response.append(line);
//			}
//			reader.close();
//
//			System.out.println("Response Code : " + responseCode);
//			System.out.println("Response Body : " + response.toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//			RestTemplate restTemplate = new RestTemplate();
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.set("Authorization", PUBLIC_KEY);
//
//			String paymentDescription = "Payment description";
//			String reference = "your_unique_reference";
//
//			String requestBody = "{" +
//					"\"email\": \"customer@email.com\"," +
//					"\"amount\": 1000," +
//					"\"currency\": \"XAF\"," +
//					"\"description\": \"" + paymentDescription + "\"," +
//					"\"reference\": \"" + reference + "\"" +
//					"}";
//
//			HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//
//			System.out.println(restTemplate.postForEntity(API_URL, request, String.class));


		SpringApplication.run(Ecommerce2Application.class, args);

//		PaymentOperation payment = new PaymentOperation(this.applicationKey, this.accessKey, this.secretKey);
//		try {
//			TransactionResponse response = payment.makeCollect(100, "MTN", "677550203", new Date(), RandomGenerator.nonce());
//		} catch (IOException | NoSuchAlgorithmException | InvalidKeyException | ServerException |
//				 ServiceNotFoundException | PermissionDeniedException | InvalidClientRequestException e) {
//			throw new RuntimeException(e);
//		}

//		RestTemplate restTemplate = new RestTemplate();
//		String url = "https://mesomb.hachther.com/api/v1.0/payment/online/";
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("X-MeSomb-Application", "91e7a224-711d-4fea-9374-f1215088da8e");
//		headers.set("X-MeSomb-OperationMode", "synchronous");
//		String requestJson = "{\"amount\": 10, \"payer\": \"400001019\", \"fees\": true, \"service\": \"MTN\", \"currency\": \"XAF\", \"country\": \"CM\"}";
//		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
//		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//		System.out.println(response.getBody());


//		String url = "https://api.notchpay.co/payments/initialize";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", "sb.kMUqExOBxstY0CC6IAj5b3letgDVLMqqb5IPqnsSFL9qQ9aLd6Iq0EoNL5ReLQz21CWlcFKahIuPoRbmESwXF6RK2sdCTZEGwDLM2WUhvzeovtOhrNPxk2ILISAVg");
//		headers.setCacheControl("no-cache");
//
//		Map<String, String> data = new HashMap<>();
//		data.put("email", "client@email.com");
//		data.put("amount", "4000");
//		data.put("currency", "XAF");
//		data.put("description", "je suis description");
//		data.put("reference", "mon_test_reference");
//		data.put("redirect_url","https://moi.html");
//
//		HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);
//
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//
//		System.out.println(response.getBody());
//
//		RestTemplate restTemplate2 = new RestTemplate();
//		HttpHeaders headers2 = new HttpHeaders();
//		headers.set("Authorization", "sb.kMUqExOBxstY0CC6IAj5b3letgDVLMqqb5IPqnsSFL9qQ9aLd6Iq0EoNL5ReLQz21CWlcFKahIuPoRbmESwXF6RK2sdCTZEGwDLM2WUhvzeovtOhrNPxk2ILISAVg");
//		HttpEntity<String> entity = new HttpEntity<>("body", headers2);
//		ResponseEntity<String> response2 = restTemplate2.exchange("https://api.notchpay.co", HttpMethod.GET, entity, String.class);
//		String responseBody = response2.getBody();
//
//		System.out.println(responseBody);


	}


	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	// @Bean
	// CommandLineRunner createAdmin(CustomerService customerService){
	// 	return args->{
	// 		customerService.addRole("ADMIN");

	// 		customerService.addNewUser("admin", "admin");

	// 		customerService.addRoleToUser("admin","ADMIN");
	// 		customerService.addRoleToUser("admin","USER");
	// 	};
	// }
}
	