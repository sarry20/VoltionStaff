package sarry20.voltionstaff.Comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import sarry20.voltionstaff.Main;



public class ComandoFreeze implements CommandExecutor {

	private Main plugin;

	public ComandoFreeze(Main plugin) {
		this.plugin = plugin;
	}


	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		FileConfiguration config = this.plugin.getConfig();
		List<String> addsend = config.getStringList("Config.add-freeze-staff");
		List<String> addtar = config.getStringList("Config.add-freeze-target");
		List<String> remsend = config.getStringList("Config.remove-freeze-staff");
		List<String> remtar = config.getStringList("Config.remove-freeze-target");



		if (comando.getName().equalsIgnoreCase("freeze")) {
			if (sender.hasPermission("vstaff.staff")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Utiliza /freeze <jugador>");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "no se pudo encontrar al jugador " + args[0] + "!");
					return true;
				}
				if (plugin.frozen.contains(target.getName())) {
					plugin.frozen.remove(target.getName());
					for (int i = 0; i < remsend.size(); i++) {
						String texto = remsend.get(i);
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", target.getName())));
					}
					for (int i = 0; i < remtar.size(); i++) {
						String texto = remtar.get(i);
						target.sendMessage(
								ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", target.getName())));
					}

					return true;
				

				}
				if (target.hasPermission("vstaff.staff") && target.getName() == sender.getName()) {
					sender.sendMessage(ChatColor.RED+"No te puedes congelar a ti mismo!");


				}
				else if(!(target.hasPermission("vstaff.bypass"))) {
				plugin.frozen.add(target.getName());
				for (int i = 0; i < addsend.size(); i++) {
					String texto = addsend.get(i);
					sender.sendMessage(
							ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", target.getName())));
				}
				for (int i = 0; i < addtar.size(); i++) {
					String texto = addtar.get(i);
					target.sendMessage(
							ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", target.getName())));
				}
				


			}else {
				sender.sendMessage(ChatColor.RED+"No puedes congelar a otros staffs!");
			}


		}
		return true;
	}
		return false;
	}
	
}
