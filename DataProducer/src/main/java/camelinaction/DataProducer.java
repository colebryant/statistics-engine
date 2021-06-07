package camelinaction;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class DataProducer {
	
	public static void main(String args[]) throws Exception {
		// Set up camel context
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory =
	            new ActiveMQConnectionFactory("tcp://localhost:61616");
		context.addComponent("jms", 
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		// Read stock ticker from csv files, translate to Java hash map and send to appropriate queues
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				// Consume stock ticker data from inbox directory
				from("file:data/inbox?noop=true")
				.log("RECEIVED: ${file:name}")
				.unmarshal().csv()
				// MESSAGE TRANSLATOR: convert message into hash map
				.process(new Processor() {
					public void process(Exchange e) throws Exception {
						String[] messageBody = e.getIn().getBody(String.class).split("\t");
						// Parse out body of message
						String stock = messageBody[0].replace("[", "");
						String bidPrice = messageBody[1];
						String bidQuantity = messageBody[2];
						String askPrice = messageBody[3];
						String askQuantity = messageBody[4].replace("]", "");
						
						// Check that price and quantity data are valid. If invalid, add invalid tag so it will be
						// sent to the Invalid Message channel
						if (!isNumericAndPositive(bidPrice) || !isNumericAndPositive(bidQuantity) || 
								!isNumericAndPositive(askPrice) || !isNumericAndPositive(askQuantity)) {
							stock += "_INVALID";
						}
									
						// Construct HashMap
						HashMap<String, String> tickerMap = new HashMap<String, String>();
						tickerMap.put("stock", stock);
						tickerMap.put("bidPrice", bidPrice);
						tickerMap.put("bidQuantity", bidQuantity);
						tickerMap.put("askPrice", askPrice);
						tickerMap.put("askQuantity", askQuantity);
						// Replace body with has hmap representation of message
						e.getIn().setBody(tickerMap);
						// Pause thread for 1 second to simulate periodic ticker updates
						Thread.sleep(1000);
					}
				})
				// MESSAGE ROUTER/ POINT-TO-POINT CHANNEL: route ticker message to appropriate company queue
				// Route invalid messages to INVALID MESSAGE CHANNEL
				.choice()
				.when(body().regex("^(?!.*INVALID).*MSFT.*$"))
					.to("jms:queue:MSFT_TICKER_QUEUE")
				.when(body().regex("^(?!.*INVALID).*ORCL.*$"))
					.to("jms:queue:ORCL_TICKER_QUEUE")
				.when(body().regex("^(?!.*INVALID).*IBM.*$"))
					.to("jms:queue:IBM_TICKER_QUEUE")
				.otherwise()
				// INVALID MESSAGE CHANNEL: send invalid tickers to below queue
					.to("jms:queue:INVALID_MESSAGE_QUEUE");
			}
		});
		
		context.start();
		Thread.sleep(100000);
		context.stop();
	}
	
	public static boolean isNumericAndPositive(String string) {
		// Method to check if string is numeric and positive
	    if(string == null || string.equals("")) {
	        return false;
	    }
	    
	    try {
	    	Double myDouble;
	        myDouble = Double.parseDouble(string);
	        if (myDouble >= 0) {
	        	return true;
	        } else {
	        	return false;
	        }
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
