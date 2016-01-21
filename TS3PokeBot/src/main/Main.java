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

public class Main {
	
	public static void main(String[] args) {
		
		final TS3Config config = new TS3Config();
		config.setHost("ts.wow-ggm.dk:9002");
		config.setDebugLevel(Level.ALL);

		final TS3Query query = new TS3Query(config);
		query.connect();

		final TS3Api api = query.getApi();
		api.login("", "-");
		List<Channel> channels = api.getChannels();
		for (Channel c : channels) {
			System.out.println("Channel found: " + c.getName() + " ; " + c.getId());
		}
		api.selectVirtualServerById(1);
		api.setNickname("PutPut");
		api.sendChannelMessage("PutPut is online!");

		// Get our own client ID by running the "whoami" command
		final int clientId = api.whoAmI().getId();

		// Listen to chat in the channel the query is currently in
		// As we never changed the channel, this will be the default channel of the server
		api.registerEvent(TS3EventType.TEXT_CHANNEL, 0);

		// Register the event listener
		api.addTS3Listeners(new TS3EventAdapter() {

			@Override
			public void onTextMessage(TextMessageEvent e) {
				// Only react to channel messages not sent by the query itself
				if (e.getTargetMode() == TextMessageTargetMode.CHANNEL && e.getInvokerId() != clientId) {
					String message = e.getMessage().toLowerCase();

					if (message.equals("!ping")) {
						// Answer "!ping" with "pong"
						api.sendChannelMessage("pong");
					} else if (message.startsWith("hello")) {
						// Greet whoever said hello
						// Message: "Hello <client name>!"
						api.sendChannelMessage("Hello " + e.getInvokerName() + "!");
					}
				}
			}
		});
	}
	
}
