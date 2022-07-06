package sarry20.voltionstaff.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ComandoSboard implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if(p.hasPermission("vstaff.staff")) {
					ConsoleCommandSender consola = Bukkit.getServer().getConsoleSender();
					Bukkit.dispatchCommand(consola, "animatedscoreboard switch adminscoreboard "+p.getName()+"");
		}
		 
		return true;
	}

}
