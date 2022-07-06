package sarry20.voltionstaff;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import sarry20.voltionstaff.Comandos.ComandoFreeze;
import sarry20.voltionstaff.Comandos.ComandoRollback;
import sarry20.voltionstaff.Comandos.ComandoSboard;
import sarry20.voltionstaff.Comandos.ComandoStaff;
import sarry20.voltionstaff.Eventos.Inventario;
import sarry20.voltionstaff.Eventos.PlayerListener;
import sarry20.voltionstaff.MySQL.Connect;
import sarry20.voltionstaff.MySQL.DatabaseCreation;
import sarry20.voltionstaff.MySQL.SQLPlayerData;
import sarry20.voltionstaff.PlaceHolder.PlaceHolderCreate;
import sarry20.voltionstaff.staff.PlayerManager;
import sarry20.voltionstaff.staff.SJugador;


public class Main extends JavaPlugin {
	   private File customConfigFile;
	    private FileConfiguration customConfig;
		PluginDescriptionFile pdffile = getDescription();
		public String version = pdffile.getVersion();
		public String nombre = ChatColor.RED+"["+ChatColor.RED+pdffile.getName()+ChatColor.RED+"]";
		public FileConfiguration config = this.getCustomConfig();
		private Connect connection;
		public int staffsi;
		ArrayList<String> staffs;
		public int staffson;

		public ArrayList<String> frozen = new ArrayList<String>();

		public void AddToFreeze(Player jugador) {
			
			frozen.add(jugador.getName());
		}
		
		
		public void onEnable() {
			Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"El plugin ha sido iniciado ");
			Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"Version: "+ChatColor.RED+version);

			customConfig = this.getConfig();
			 String host = this.getCustomConfig().getString("MySQL.host");
			 int port = customConfig.getInt("MySQL.port");
			 String database = customConfig.getString("MySQL.database");
			 String username = customConfig.getString("MySQL.username");
			 String password = customConfig.getString("MySQL.password");
			this.connection = new Connect(host,port,database,username,password);
			DatabaseCreation.createTables(getMySQL());

			if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"PlaceHolderAPI Installed ");
				new PlaceHolderCreate(this).register();
			}
		    registerEvents();
		    registrerComandos();	
		    createCustomConfig();
		}
		public void onDisable() {
			Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"El plugin se desactivo ");
			Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"Version: "+ChatColor.RED+version);
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission("vstaff.staff")) {
			if(PlayerManager.get().doesPlayerExists(p.getUniqueId()) == false)
				PlayerManager.get().createPlayer(p);
		    SJugador sp = PlayerManager.get().getPlayer(p.getUniqueId());
			if(sp.getStaffMode() == true) {
				SQLPlayerData.setStopValue(getMySQL(), p.getUniqueId(), true);
				
			}
				}
			}
		}
		public void registrerComandos() {
			this.getCommand("staff").setExecutor(new ComandoStaff(this ));
			this.getCommand("freeze").setExecutor(new ComandoFreeze(this));
			this.getCommand("sboard").setExecutor(new ComandoSboard());
			this.getCommand("rollback").setExecutor(new ComandoRollback(this));


		}
		
		public void registerEvents() {
			PluginManager pm = getServer().getPluginManager();
//			pm.registerEvents(new fifthInventory(this),this);

			pm.registerEvents(new PlayerListener(this),this);
			pm.registerEvents(new Inventario(this),this);


		}
		public Connection getMySQL() {
			return this.connection.getConnection();
		}
		
	    public FileConfiguration getCustomConfig() {
	        return this.customConfig;
	    }

	    private void createCustomConfig() {
	        customConfigFile = new File(getDataFolder(), "config.yml");
	        if (!customConfigFile.exists()) {
	            customConfigFile.getParentFile().mkdirs();
	            saveResource("config.yml", false);
	         }

	        customConfig= new YamlConfiguration();
	        try {
	            customConfig.load(customConfigFile);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
	    }

		public int getStaffson() {
			return staffson;
		}
		 
	    
}
