package camelinaction;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;

// Consumer: consumes ticker data / publishes to relevant topics
public class StatisticsStream {

	public static void main(String args[]) throws Exception {
		
		// Instantiate Statistics Engine
		final StatisticsEngine statEngine = StatisticsEngine.getInstance();
		
		// Set up camel context
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory =
	            new ActiveMQConnectionFactory("tcp://localhost:61616");
		context.addComponent("jms", 
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		// Consume messages from queues, process statistics, and publish to relevant topics
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				
				// Consume ticker messages from MSFT queue
				from("jms:queue:MSFT_TICKER_QUEUE")
				// DELAY: delay message intake 1 second
				.delay(1000)
				//  Log which file received
				.log("RECEIVED: ${file:name}")
				// Process ticker statistics using Statistics Engine
				.process(new StatisticsProcessor(statEngine))
//				.setHeader(Exchange.HTTP_METHOD, "GET")
//				.enrich("https://www.cnbc.com/id/19854910/device/rss/rss.rss", new RSSAggregationStrategy())
				// PUBLISH-SUBSCRIBE CHANNEL: Publish statistics to relevant topic
				 .to("jms:topic:MSFT_STATS_TOPIC");
				
				// Consume ticker messages from ORCL queue
				from("jms:queue:ORCL_TICKER_QUEUE")
				// DELAY: delay message intake 1 second
				.delay(1000)
				//  Log which file received
				.log("RECEIVED: ${file:name}")
				// Process ticker statistics using Statistics Engine
				.process(new StatisticsProcessor(statEngine))
				// PUBLISH-SUBSCRIBE CHANNEL: Publish statistics to relevant topic
				 .to("jms:topic:ORCL_STATS_TOPIC");
				
				// Consume ticker messages from IBM queue
				from("jms:queue:IBM_TICKER_QUEUE")
				// DELAY: delay message intake 1 second
				.delay(1000)
				//  Log which file received
				.log("RECEIVED: ${file:name}")
				// Process ticker statistics using Statistics Engine
				.process(new StatisticsProcessor(statEngine))
				// PUBLISH-SUBSCRIBE CHANNEL: Publish statistics to relevant topic
				 .to("jms:topic:IBM_STATS_TOPIC");						
			}
		});

		context.start();
		Thread.sleep(200000);
		context.stop();
	}

}
