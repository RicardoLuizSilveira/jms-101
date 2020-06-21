# JMS

### Docs
Java 8
  - [JavaDoc Destination.class](http://docs.oracle.com/javaee/5/api/javax/jms/Destination.html)

ActiveMQ 5
  - [JNDI Support: config initial context](https://activemq.apache.org/jndi-support.html)


### How to Run
This code intends to provide a simple study case of a JMS Consumer.

1. Download and run ActiveMQ
2. Create a queue named `fila.financeiro`
3. Enqueue a message
4. Import a project in your IDE
5. Add `ActiveMQ` jar, downloaded as a external lib in the project *(this is very simple we don't use maven or gradle yet)*.
6. **Run TestConsumer** class
7. *Debug* and *Test* as you wish.