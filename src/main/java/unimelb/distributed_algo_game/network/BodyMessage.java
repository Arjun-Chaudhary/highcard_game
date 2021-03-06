package unimelb.distributed_algo_game.network;

import java.io.Serializable;

/**
 * 
 * @author Lupiya
 *This class is generic type to carry JSON data in messages
 */
public class BodyMessage implements Serializable{
	private int nodeID;
	private MessageType messageType;
	private Object message;
	
	public enum ACKCode {
		NODE_ID_RECEIVED(0),
		CARD_RECEIVED(1);
		/** The code. */
		private int code;

		
		private ACKCode(int c) {
			code = c;
		}

		/**
		 * Gets the code.
		 *
		 * @return the code
		 */
		public int getCode() {
			return code;
		}
	}
	
	public enum MessageType {
		CON(0),
		ACK(1),
		BCT(2),
		CRD(3),
		DSC(4);
		
		/** The code. */
		private int code;

		
		private MessageType(int c) {
			code = c;
		}

		/**
		 * Gets the code.
		 *
		 * @return the code
		 */
		public int getCode() {
			return code;
		}
	}
	
	/**
	 * Main constructor for this class
	 */
	public BodyMessage(int nodeID, MessageType messageType, Object message){
		this.nodeID = nodeID;
		this.messageType = messageType;
		this.message = message;
	}
	
	/**
	 * Returns the client ID
	 */
	public int getNodeID(){ return nodeID; }
	
	/**
	 * Returns the message type
	 */
	public MessageType getMessageType(){ return messageType; }
	
	/**
	 * Returns the generic message
	 */
	public Object getMessage(){ return message; }
	
	/**
	 * Sets the Node ID
	 */
	public void setNodeID(int nodeID){
		this.nodeID = nodeID;
	}
	
	/**
	 * Sets the message type
	 */
	public void setMessageType(MessageType messageType){
		this.messageType = messageType;
	}
	
	/**
	 * Sets the message to be sent
	 */
	public void setMessage(Object message){
		this.message = message;
	}
}
