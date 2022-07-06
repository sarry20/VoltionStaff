package sarry20.voltionstaff.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import sarry20.voltionstaff.Main;
import sarry20.voltionstaff.MySQL.SQLPlayerData;

public class ComandoRollback implements CommandExecutor {

	private Main plugin;

	public ComandoRollback(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if(sender.hasPermission("vstaff.rollback")) {
		if(args.length >=1) {
			ItemStack[] items;
			ItemStack[] armor;
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			items = SQLPlayerData.getInventory(plugin.getMySQL(), target.getUniqueId(), 3);
			armor = SQLPlayerData.getInventory(plugin.getMySQL(), target.getUniqueId(), 6);
			target.getInventory().setContents(items);
			target.getInventory().setArmorContents(armor);
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eUn staff te ha devuelto el inventario"));
		}else if(args.length == 0) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Utiliza /rollback <jugador>");
		}
		}
		
		return false;
	}

}
