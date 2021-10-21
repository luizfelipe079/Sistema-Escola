package br.com.luxfacta.sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.luxfacta.sistema.service.EmailService;
import br.com.luxfacta.sistema.service.SmtpEmailService;

@Configuration
public class EmailConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
