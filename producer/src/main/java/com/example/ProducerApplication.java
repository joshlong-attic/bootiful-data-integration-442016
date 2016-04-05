package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableBinding(Source.class)
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	/*@Bean
	MessageChannel requests() {
		return MessageChannels.direct().get();
	}

	private String lukeRequests = "luke-requests";

	@Bean
	AmqpTemplate amqpTemplate(ConnectionFactory cf) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);
		rabbitTemplate.setQueue(lukeRequests);
		rabbitTemplate.setExchange(lukeRequests);
		rabbitTemplate.setRoutingKey(lukeRequests);
		return rabbitTemplate;
	}

	@Bean
	IntegrationFlow rabbitOutboundFlow(MessageChannel requests,
	                                   AmqpTemplate amqpTemplate) {
		return IntegrationFlows.from(requests)
				.handle(Amqp.outboundAdapter(amqpTemplate))
				.get();
	}*/

	/*@Autowired
	private ServiceChannels serviceChannels ;

	MessageChannel requests(){
		return this.serviceChannels.requests();
	}*/

	@Autowired
	private Source serviceChannels;

	@Bean
	IntegrationFlow files(
			@Value("${lukes-favorite-directory:${HOME}/Desktop/ingest}") File file) {
		return IntegrationFlows.from(Files.inboundAdapter(file).autoCreateDirectory(true),
				spec -> spec.poller(pollerFactory -> pollerFactory.fixedRate(10, TimeUnit.SECONDS)))
				.transform(new FileToStringTransformer())
				.channel(this.serviceChannels.output())
				.get();
	}
}


@RestController
class RequestRestController {

	@Autowired
	private MessageChannel requests;

	@RequestMapping("/requests/{request}")
	public void acceptRequests(@PathVariable String request) {
		this.requests.send(MessageBuilder.withPayload(request).build());
	}


}
