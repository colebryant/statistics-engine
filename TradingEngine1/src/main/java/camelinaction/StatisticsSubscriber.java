package camelinaction;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;


public class StatisticsSubscriber {
	
	
	public static void main(String args[]) throws Exception {
			
		// Create portfolio to add to trading engine (composite of linked list of stocks)
        Stock MSFT = new Stock("MSFT", 15);
        Stock ORCL = new Stock("ORCL", 20);
        MSFT.append(ORCL);
        // Add linked list of stocks to portfolio
        Portfolio p1 = new Portfolio("Portfolio 1", MSFT);
		// Instantiate trading engine
		final TradingEngine tradeEngine = new TradingEngine(p1);
		
		// Set up camel context
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory =
	            new ActiveMQConnectionFactory("tcp://localhost:61616");
		context.addComponent("jms", 
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		// Consume statistics from subscribed topics
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				// Read from MSFT stats topic
				from("jms:topic:MSFT_STATS_TOPIC")
				.log("RECEIVED: ${file:name}")
				// Update portfolio with new statistics and report
				.process(new TradingProcessor(tradeEngine))
				.to("jms:queue:ENGINE_1_FINISH_QUEUE");
				
				// Read from ORCL stats topic
				from("jms:topic:ORCL_STATS_TOPIC")
				.log("RECEIVED: ${file:name}")
				// Update portfolio with new statistics and report
				.process(new TradingProcessor(tradeEngine))
				.to("jms:queue:ENGINE_1_FINISH_QUEUE");
			}
		});
		
		context.start();
		Thread.sleep(250000);
		context.stop();
	}

}
