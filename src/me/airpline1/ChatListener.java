package me.airpline1;

import java.util.HashMap;
import java.util.Map;
import me.airpline1.GenderLib.GenderType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	Map<GenderType, String> chatPrefixes;
	
	public ChatListener(){
		chatPrefixes = new HashMap<GenderType, String>();
		FileConfiguration config = GenderLib.getPlugin().getConfig();
		
		for(GenderType gender : GenderType.values()){
			chatPrefixes.put(gender, config.getString(gender.name()));
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerAsyncChat(AsyncPlayerChatEvent evt){
		GenderType gender = GenderLib.getGender(evt.getPlayer());
		
		if(gender != GenderType.NONE){
			//String chatPrefix = chatPrefixes.get(gender);
			
			//TODO: Modify chat to prefix name with gender
		}
	}
}
