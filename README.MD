# JMS

### Docs
Java 8
  - [JavaDoc Destination.class](http://docs.oracle.com/javaee/5/api/javax/jms/Destination.html)
ActiveMQ 5
  - [JNDI Support: config initial context](https://activemq.apache.org/jndi-support.html)


### How to Run
This code intends to provide a simple study case of a JMS Consumer.

1. download and run ActiveMQ
2. create a queue named `fila.financeiro`
3. enqueue a message
4. import a project in your IDE
5. add `ActiveMQ` jar, downloaded as a external lib in the project (this is very simple we don't use maven or gradle yet).
6. on Eclipse **Run TestConsumer** class
7. *Debug* and *Test* as you wish.