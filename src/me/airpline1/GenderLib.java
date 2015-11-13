package me.airpline1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GenderLib extends JavaPlugin implements Listener{
	public enum GenderType{
		MALE("boy", "man"), FEMALE("girl", "woman"), NONE("none");
		
		private String[] aliases;
		
		GenderType(String... a){aliases = a;}
		
		public static GenderType getGenderType(String alias){
			alias = alias.toUpperCase();
			if(valueOf(alias) != null) return valueOf(alias);
			
			alias = alias.toLowerCase();
			for(GenderType gender : values()){
				for(String a : gender.aliases){
					if(a.equals(alias)) return gender;
				}
			}
			return GenderType.NONE;
		}
	};
	
	static Map<UUID, GenderType> onlinePlayers = null;
	static Map<UUID, GenderType> offlinePlayers = null;
	private static GenderLib plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
		onlinePlayers = new HashMap<UUID, GenderType>();
		offlinePlayers = new HashMap<UUID, GenderType>();
		
		//load saved data
		String map = FileData.loadFile("genderlist", "{}");
		for(String pair : map.substring(1, map.length()).split(", ")){
			offlinePlayers.put(UUID.fromString(pair.split("=")[0]), GenderType.getGenderType(pair.split("=")[1]));
		}
		//register events
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override public void onDisable(){
		//save data
		offlinePlayers.putAll(onlinePlayers);
		FileData.saveFile("genderlist", offlinePlayers.toString());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt){
		//move the Player to the online players list
		if(offlinePlayers.containsKey(evt.getPlayer().getUniqueId())){
			onlinePlayers.put(evt.getPlayer().getUniqueId(), offlinePlayers.remove(evt.getPlayer().getUniqueId()));
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt){
		//move the Player to the offline players list
		if(onlinePlayers.containsKey(evt.getPlayer().getUniqueId())){
			offlinePlayers.put(evt.getPlayer().getUniqueId(), onlinePlayers.remove(evt.getPlayer().getUniqueId()));
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
		if(cmd.getName().equals("male")){
			//add the argument "male" to the end of the args[] array
			String[] oldArgs = args;
			args = new String[args.length+1];
			for(int i=0; i<oldArgs.length; ++i) args[i] = oldArgs[i];
			args[oldArgs.length] = "male";
			
			return onCommand(sender, getCommand("gender"), label, args);
		}
		else if(cmd.getName().equals("female")){
			//add the argument "male" to the end of the args[] array
			String[] oldArgs = args;
			args = new String[args.length+1];
			for(int i=0; i<oldArgs.length; ++i) args[i] = oldArgs[i];
			args[oldArgs.length] = "female";
			
			return onCommand(sender, getCommand("gender"), label, args);
		}
		
		else if(cmd.getName().equals("gender")){
			if(args.length == 0){
				sender.sendMessage("§cToo few arguments");
				return false;
			}
			if(args.length == 1){
				if(sender instanceof Player == false){
					sender.sendMessage("§cToo few arguments");
					return false;
				}
				GenderType gender = GenderType.getGenderType(args[0]);
				if(gender == null){
					sender.sendMessage("§cGender "+args[0]+" is undefined");
					return false;
				}
				onlinePlayers.put(((Player)sender).getUniqueId(), gender);
			}
			else{
				if(sender.hasPermission("gender.set.others")){
					sender.sendMessage("§4You do not have access to this command.");
					return false;
				}
				
				@SuppressWarnings("deprecation")//temporarily depricated, will never be deleted by bukkit however
				OfflinePlayer targetPlayer = getServer().getOfflinePlayer(args[0]);
				
				if(targetPlayer == null){
					sender.sendMessage("§cPlayer "+args[0]+" not found");
					return false;
				}
				GenderType gender = GenderType.getGenderType(args[1]);
				if(gender == null){
					sender.sendMessage("§cGender "+args[1]+" is undefined");
					return false;
				}
				
				if(targetPlayer.isOnline()) onlinePlayers.put(targetPlayer.getUniqueId(), gender);
				else offlinePlayers.put(targetPlayer.getUniqueId(), gender);
			}
			return true;
		}
		return false;
	}
	
	public static GenderType getGender(String username){
		@SuppressWarnings("deprecation")
		OfflinePlayer p = plugin.getServer().getOfflinePlayer(username);
		return p == null ? null : getGender(p.getUniqueId());
	}
	public static GenderType getGender(OfflinePlayer p){
		return getGender(p.getUniqueId());
	}
	public static GenderType getGender(Player p){
		return onlinePlayers.containsKey(p.getUniqueId()) ? onlinePlayers.get(p.getUniqueId()) : null;
	}
	public static GenderType getGender(UUID uuid){
		if(onlinePlayers != null){
			if(onlinePlayers.containsKey(uuid)) return onlinePlayers.get(uuid);
			if(offlinePlayers.containsKey(uuid)) return offlinePlayers.get(uuid);
		}
		return searchGender(uuid);
	}
	public static GenderType searchGender(UUID uuid){
		//TODO:
		/**
		 * Fill in here, have the plugin connect to a database and see if
		 * the Player has a saved gender there, otherwise return null.
		 * This will allow players to have a "global" gender accessible by
		 * any server using this plugin
		 * (Need to set up a database for this since I don't just want to
		 * have it in my dropbox)
		 */
		return null;
	}
}