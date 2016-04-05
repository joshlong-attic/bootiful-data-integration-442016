package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;


@SpringBootApplication
@EnableBinding(Sink.class)
public class ConsumerApplication {

	/*
	private String lukeRequests = "luke-requests";
	@Bean
	MessageChannel requests() {
		return MessageChannels.direct().get();
	}

	@Bean
	Queue queue() {
		return new Queue(lukeRequests);
	}

	@Bean
	Exchange exchange() {
		return new DirectExchange(lukeRequests, true, false);
	}

	@Bean
	Binding binding() {
		return BindingBuilder
				.bind(this.queue())
				.to(this.exchange())
				.with(lukeRequests)
				.noargs();
	}

	@Bean
	IntegrationFlow rabbitFlow(ConnectionFactory connectionFactory,
	                           MessageChannel requests) {
		return IntegrationFlows.from(
				Amqp.inboundAdapter(connectionFactory, lukeRequests))
				.channel(requests)
				.get();
	}


	// step 2
	@MessageEndpoint
	public static class StringMessageProcessor {

		@ServiceActivator(inputChannel = "requests")
		public void processNewMessagesPlease(@Payload String contents,
		                                     @Headers Map<String, Object> headers) {
			System.out.println("--------------------------");
			System.out.println(contents);
			headers.entrySet().forEach(e -> System.out.println(e.getKey() + '=' + e.getValue()));
		}
	}

	// step 1
	@Bean
	IntegrationFlow consumer(MessageChannel requests) {
		return IntegrationFlows.from(requests)
				.handle(String.class, (contents, headers) -> {
					System.out.println("--------------------------");
					System.out.println(contents);
					headers.entrySet().forEach(e -> System.out.println(e.getKey() + '=' + e.getValue()));
					return null;
				})
				.get();
	}
*/


	@MessageEndpoint
	public static class StringMessageProcessor {

		@ServiceActivator(inputChannel = "input")
		public void processNewMessagesPlease(@Payload String contents,
		                                     @Headers Map<String, Object> headers) {
			System.out.println("--------------------------");
			System.out.println(contents);
			headers.entrySet().forEach(e -> System.out.println(e.getKey() + '=' + e.getValue()));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
/*

interface ProcessorChannels {

	@Input
	SubscribableChannel requests();
}*/
