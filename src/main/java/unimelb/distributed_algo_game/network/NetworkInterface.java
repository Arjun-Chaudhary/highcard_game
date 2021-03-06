/*
 * 
 */
package unimelb.distributed_algo_game.network;

// TODO: Auto-generated Javadoc
/**
 * This is the interface for creating network connectivity.
 *
 * @author Ting-Ying Tsai
 */
public interface NetworkInterface {

	/** The Constant PORT. */
	public static final int PORT = 10009;

	/** The Constant GameServerName. */
	public static final String GameServerName = "HighCard Game";

	/**
	 * The Enum ServerConnectionState.
	 */
	public enum ServerConnectionState {
		/** The connecting. */
		INIT,
		/** The connecting. */
		CONNECTING,
		/** The connected. */
		CONNECTED,
		/** The disconnecting. */
		DISCONNECTING,
		/** The disconnect. */
		DISCONNECTED,
		/** Ack */
		ACK
	};
	
	/**
	 * The Enum ClientConnectionState.
	 */
	public enum ClientConnectionState {
		/** The connecting. */
		INIT,
		/** The connecting. */
		CONNECTING,
		/** The connected. */
		CONNECTED,
		/** The disconnecting. */
		DISCONNECTING,
		/** The disconnect. */
		DISCONNECTED,
		/** Ack */
		ACK
	};


	/**
	 * Connect.
	 *
	 * @return true, if successful
	 */
	public boolean connect();

	/**
	 * Disconnect.
	 */
	public void disconnect();

}
