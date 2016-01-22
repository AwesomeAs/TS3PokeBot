package main;

import java.util.List;
import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class Main {
	
	// Variable declaration start
	
	private final static String HOST		= "46.32.39.140";		// The server IP we connect to
	private final static int PORT			= 9002;					// The server port we connect to
	private final static String BOTNAME		= "Poker";				// The username for our bot. If it is in use, we start adding number 1, 2, ... at the end
	private final static String POKEMSG		= "You got poked.";		// The message to be poked
	private static int TARGETID				= 0;					// Our target client's ID. Does this even change? Use TARGETNAME if you do not know the ID.
	private final static String TARGETNAME	= "Poor guy";			// Setting this variable to anything other than null will update TARGETID to the desired ID if found.
	private static int POKEAMOUNT			= 5;					// Extra: Let the bot poke MULTIPLE times! Wow!
	private static int POKEINTERVAL			= 2000;					// Extra: ^ Poking multiple times comes in intervals!
	
	private final static int CHANNELID		= 2;					// Make the bot join a channel, if the value is not 0.
	
	// Variable declaration end
	
	public static void main(String[] args) {
		
		// Connect to the server.
		final TS3Config config = new TS3Config();
		config.setHost(HOST);
		config.setDebugLevel(Level.OFF);

		final TS3Query query = new TS3Query(config);
		query.connect();

		final TS3Api api = query.getApi();
		api.selectVirtualServerByPort(PORT);
		
		/*List<Channel> channels = api.getChannels();
		for (Channel c : channels) {
			System.out.println("Channel found: Name = " + c.getName() + " ; ID = " + c.getId() + " ; TotalClients = " + c.getTotalClients());
		}*/
		
		boolean successBotname = api.setNickname(BOTNAME);
		if (!successBotname) {
			attemptSetNickname(api, 1);
		}
		
		// Look for the target by name, not ID.
		if (TARGETNAME != null) {
			Client target = api.getClientByNameExact(TARGETNAME, true);
			if (target != null) {
				TARGETID = target.getId();
			}
		}
		
		// Join the channel, if possible
		if (CHANNELID != 0) {
			api.selectVirtualServerById(CHANNELID);
		}
		
		// POKE POKE POKE! If the amount of pokes is >1, we loop. Otherwise, we just poke.
		if (POKEAMOUNT > 1) {
			for (int i = 0; i < POKEAMOUNT; i++) {
				api.pokeClient(TARGETID, POKEMSG);
				if (i < POKEAMOUNT - 1) { // Do not wait if this was the last poke.
					try {
						Thread.sleep(POKEINTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			api.pokeClient(TARGETID, POKEMSG);
		}
	}
	
	// Internal method for finding an available nickname.
	private static void attemptSetNickname(TS3Api api, int num) {
		boolean success = api.setNickname(BOTNAME + num);
		if (!success) {
			attemptSetNickname(api, num + 1);
		}
	}
	
}
