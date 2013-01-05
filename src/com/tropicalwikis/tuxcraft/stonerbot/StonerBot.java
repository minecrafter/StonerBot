package com.tropicalwikis.tuxcraft.stonerbot;

import java.util.ArrayList;
import java.util.Random;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class StonerBot extends ListenerAdapter implements Listener {
	ArrayList<String> soFhuckedUp = new ArrayList<String>();
	Random rnd = new Random();
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		if(event.getMessage().startsWith(">fhuck") && soFhuckedUp.size() > 0) {
			event.respond(scramble(soFhuckedUp.get(rnd.nextInt(soFhuckedUp.size()))));
			return;
		}
		if(rnd.nextBoolean() == true && soFhuckedUp.size() > 0) {
			soFhuckedUp.remove(0);
		}
		soFhuckedUp.add(event.getMessage());
	}

	// stolen from drugmeup
	public String scramble(String word) {
		StringBuilder builder = new StringBuilder(word.length());
		boolean[] used = new boolean[word.length()];

		for (int i = 0; i < word.length(); i++) {
			int rndIndex;
			do
				rndIndex = rnd.nextInt(word.length());
			while (used[rndIndex] != false);
			used[rndIndex] = true;

			builder.append(word.charAt(rndIndex));
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		PircBotX bot = new PircBotX();

		bot.setName("StonerBot");
		bot.setLogin("drugs");
		bot.setAutoNickChange(true);
		bot.getListenerManager().addListener(new StonerBot());

		try {
			bot.connect("irc.caffie.net");
			bot.joinChannel("#bots");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}