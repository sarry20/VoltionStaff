package sarry20.voltionstaff.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import sarry20.voltionstaff.Main;
import sarry20.voltionstaff.MySQL.SQLPlayerData;
import sarry20.voltionstaff.staff.PlayerManager;
import sarry20.voltionstaff.staff.SJugador;

public class ComandoStaff implements CommandExecutor {

	private Main plugin;
	public ComandoStaff(Main plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			Player jugador = (Player) sender;
			if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
				PlayerManager.get().createPlayer(jugador);
			
		    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());

			ConsoleCommandSender consola = Bukkit.getServer().getConsoleSender();

			if(jugador.getPlayer().hasPermission("vstaff.staff")) {
		if(sp.getStaffMode().booleanValue()  == false) {
			SQLPlayerData.setInventory(plugin.getMySQL(), jugador.getUniqueId(),1,jugador.getInventory().getContents(),jugador.getInventory().getArmorContents());
			SQLPlayerData.setInventory(plugin.getMySQL(), jugador.getUniqueId(),4,jugador.getInventory().getContents(),jugador.getInventory().getArmorContents());

			sp.setStaffMode(Boolean.valueOf(true)); 
			jugador.getPlayer().getInventory().clear();
			jugador.getInventory().setArmorContents(null);
			ItemStack item = new ItemStack(Material.COMPASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lTeletransportador"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(0,item);

			item= new ItemStack(Material.FEATHER);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lFly"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(2,item);

			item = new ItemStack(Material.CLOCK);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lRandomTP"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(8,item);

			item = new ItemStack(Material.CHEST);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lInvsee"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(6,item);

			item = new ItemStack(Material.PACKED_ICE);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lFreeze"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(3,item);

			//short 10 vanish on short 8 vanish off
			item = new ItemStack(Material.GREEN_DYE);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lVanish"));
			item.setItemMeta(meta);
			jugador.getPlayer().getInventory().setItem(5,item);
			sp.setGm(jugador.getGameMode());
			jugador.setGameMode(GameMode.CREATIVE);
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&cV&6Staff&7]&e El modo staff se ha &aactivado"));
			
			Bukkit.dispatchCommand(consola, "animatedscoreboard switch adminscoreboard "+jugador.getPlayer().getName()+"");
			
		}else {
			ItemStack[] items;
			ItemStack[] armor;
			sp.setStaffMode(Boolean.valueOf(false));
			jugador.getPlayer().getInventory().clear();
			Bukkit.dispatchCommand(consola, "animatedscoreboard switch defaultscoreboard "+jugador.getPlayer().getName()+"");
			jugador.setGameMode(sp.getGm());
			items = SQLPlayerData.getInventory(plugin.getMySQL(), jugador.getUniqueId(),1);
			jugador.getInventory().setContents(items);
			armor = SQLPlayerData.getInventory(plugin.getMySQL(), jugador.getUniqueId(),4);
			jugador.getInventory().setArmorContents(armor);
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&cV&6Staff&7]&e El modo staff se ha &adesactivado"));

		}
		return true;
			}else {
				jugador.getPlayer().sendMessage(ChatColor.RED+"No tienes permiso para ejecutar este comando");
			}
		}else {
			sender.sendMessage("No puedes usar este comando desde la consola");
		}
		return false;
	}

}
