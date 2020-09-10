package com.pierre.vendasonline.services;

import org.springframework.mail.SimpleMailMessage;

import com.pierre.vendasonline.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
