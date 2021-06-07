Distributed Statistics Generator System

The purpose of the project is to load a number of channels with 
various stock ticker data statistics that can be consumed selectively by 
multiple trading engines. These trading engines will then update the stocks in 
their portfolios as necessary and print out statistics as they are updated.

The system is largely structured into three layers: a data producer, a 
statistics engine (consumer/producer), and a set of training engines 
(consumers).

The source of ticker data for this system is a set of csv files representing
bid prices, bid quantities, ask prices, and ask quantities for three companies:
Microsoft, Oracle, and IBM. These csv files are read into system via the 
DataProducer project (from the data inbox) on a one-second delay.

Usage Notes:

- First, ensure that Apache ActiveMQ is running tcp://localhost:61616
- Begin by executing the producer class DataProducer.java in the DataProducer
project. This will load csv ticker data in to the respective ticker data 
  message queues
- Next, run the StatisticsSubscriber.java classes in each of the TradingEngine
projects. These subscribers will subscribe to the company stats topics they are
  interested in to then update their statistics
- Finally, run the StatisticsStream.java class in the StatisticsEngine project.
This system will consume from the ticker data message queues, run the 
  statistics, and then push the statistics onto the relevant topics to be
  consumed by the trading engines
- Note that each of the trading engines will print out a statistics report
on its portfolio(s) when it receives new information. These reports are printed
  to standard output. It may be necessary to stop the execution in order to
  inspect the output.
  
Enterprise Integration Patterns Used:

1) Message/Message Broker/Message Endpoint/Message Channel: Implemented via
Camel and used to form message queues/topics for the ticker data and 
   statistics
   
2) Message Router: Used to route ticker messages to their appropriate company 
   queues
   
3) Publish-Subscribe Channel: Utilized for trading engines to subscribe to 
   statistics topics for the companies in which they have stocks
   
4) Point-To-Point Channel: Utilized for ticker data consumer to push messages
onto a queue to be directly consumed by the statistics engine
   
5) Message Translator: Utilized for translating stock ticker csv data into
java hash map objects
   
6) Invalid Message Channel: Channel which is fed invalid ticker data (values
   must be numeric and positive)
   
7) Delay EIP: Utilized in liu of polling consumer. Delays action on message
received based on given time. 

Design Patterns Used:

1) Singleton: Utilized to force each trading engine to only have a single
instance of "Reporting Engine". Also implemented for "Statistics Engine"
   
2) Strategy: Used in Statistics Engine to streamline implementation of 
various statistic-calculating algorithms
   
3) Template Method: Used to provide “skeleton” of steps for implementing 
   statistic-calculating algorithms 
   
4) Composite: Structure used to represent a trading engine's portfolio(s) and
stocks
   
5) Iterator: Used to iterate through the portfolio composite structure
