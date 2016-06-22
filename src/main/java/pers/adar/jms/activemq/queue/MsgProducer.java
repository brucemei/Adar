/**
 * Copyright (c) 2015, adar.w (adar-w@outlook.com) 
 * 
 * http://www.adar-w.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pers.adar.jms.activemq.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import pers.adar.jms.activemq.ConnectionCenter;

public class MsgProducer {

	private static final String QUEUE = "queue-adar";
	
	public static void main(String[] args) throws JMSException {
		Connection connection = ConnectionCenter.getConnection();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue(QUEUE);
		
		MessageProducer producer = session.createProducer(queue);
		
		TextMessage message = session.createTextMessage();
		message.setText("queue-adar-message");
		
		producer.send(message);
		
		connection.close();
	}
}
