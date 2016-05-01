/*
 * 
 */
package unimelb.distributed_algo_game.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

import unimelb.distributed_algo_game.network.BodyMessage.MessageType;
import unimelb.distributed_algo_game.player.Player;
import unimelb.distributed_algo_game.pokers.Card;

// TODO: Auto-generated Javadoc
/**
 * The Class GameServer.
 *
 * @author Ting-Ying Tsai
 */
public final class GameServer implements Runnable, NetworkInterface {

	/** The instance. */
	private static GameServer instance = null;

	/** The id. */
	private int id = -1;

	/** The m player. */
	private Player mPlayer = null;

	/** The m socket. */
	private Socket mSocket = null;

	/** The m server socket. */
	private ServerSocket mServerSocket = null;

	/** The m lock. */
	private Object mLock;

	/** The connection state. */
	private ServerConnectionState mConnectionState;

	/** The m player client manager. */
	private PlayerClientManager mPlayerClientManager;
	



	/**
	 * Instantiates a new game server.
	 */
	protected GameServer() {
		mLock = new Object();
		mConnectionState = ServerConnectionState.DISCONNECTED;
		mPlayerClientManager = new PlayerClientManager(10);
	}

	/**
	 * Gets the single instance of GameServer.
	 *
	 * @return single instance of GameServer
	 */
	public static GameServer getInstance() {
		if (instance == null) {
			instance = new GameServer();
		}
		return instance;
	}
	
	/**
	 * Sets the player.
	 *
	 * @param mPlayer
	 *            the new player
	 */
	public void setPlayer(Player mPlayer) {
		if (mPlayer != null) {
			this.mPlayer = mPlayer;
			mPlayerClientManager.setPlayer(this.mPlayer);
		} else {
			System.out.println("Player can't be null");
			throw new NullPointerException();
		}

	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		
		if (mPlayer.isDealer()) {
			
			try {
				runLeaderState();
			} catch (IOException ioe) {
				// TODO Adding error handling
				ioe.printStackTrace();

			} finally {

				System.out.println("Connection Closed");
			}

			
			
			
		} else {
			try {
				runSlaveState();
			} catch (IOException ioe) {
				// TODO Adding error handling
				ioe.printStackTrace();

			} finally {

				System.out.println("Connection Closed");
			}
			
		}
		
	}
	
	private void runLeaderState() throws IOException {
		
		if (mServerSocket != null) {
			System.out.println("Server Start, Waiting....");
			synchronized (mLock) {
				while (mConnectionState == ServerConnectionState.CONNECTED) {

					mSocket = mServerSocket.accept();
					//System.out.println("dd");
					PlayerClientThread t = new PlayerClientThread(mSocket, 1, this);
					mPlayerClientManager.addClient(new Integer(1),t);//Communicate to know player id first
					t.start();
				}

				mServerSocket.close();
			}
		}
		
		

	}
	
	private void runSlaveState() throws IOException {
		//TODO slavestate for a cue to work
		/*
		mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
		mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
		isRunning = true;
		
		if (mServerSocket != null) {
			System.out.println("Server Start, Waiting....");
			synchronized (mLock) {
				while (mConnectionState == ServerConnectionState.CONNECTED) {

					mSocket = mServerSocket.accept();
					PlayerClientThread t = new PlayerClientThread(mSocket, 1);
					mPlayerClientManager.addClient(new Integer(1),t);//Communicate to know player id first
					t.start();
				}

				mServerSocket.close();
			}
		}*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see unimelb.distributed_algo_game.network.NetworkInterface#connect()
	 */
	public synchronized boolean connect() {

		try {

			mServerSocket = new ServerSocket(NetworkInterface.PORT);
			mConnectionState = ServerConnectionState.CONNECTED;

		} catch (IOException ioe) {
			ioe.printStackTrace();
			mServerSocket = null;
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see unimelb.distributed_algo_game.network.NetworkInterface#disconnect()
	 */
	public synchronized void disconnect() {
		mConnectionState = ServerConnectionState.DISCONNECTED;
	}
	
	public void broadcastToClients(Object object) {
		mPlayerClientManager.notifyAllClients(object, mConnectionState, MessageType.BCT);
	}
	
	public void sendCard(Card card, int id) {
		mPlayerClientManager.sendMessageToClient(card, id, mConnectionState, MessageType.CRD);
	}
	
	public synchronized Card getCard(int index){
		return mPlayer.getCard(index);
	}
	

}
