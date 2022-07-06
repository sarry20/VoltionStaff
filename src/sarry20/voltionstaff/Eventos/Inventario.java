package sarry20.voltionstaff.Eventos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;


import sarry20.voltionstaff.Main;

public class Inventario implements Listener {
	private Main plugin;
	int taskID;

	public Inventario(Main plugin) {
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	public void crearInventario(Player jugador,Player target) {
		//inventario main
		Inventory inv = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&2Inventario del jugador"));
			FileConfiguration config = plugin.getConfig();
			//14 rojo 1 naranja
			ItemStack decoracion = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			ItemStack decoracion1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
			ItemStack item = new ItemStack(Material.RED_DYE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cVida"));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getHealth()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(51, item);
			
			item = new ItemStack(Material.COOKED_CHICKEN);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Comida"));
			lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getFoodLevel()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(52,item);
			
			item = new ItemStack(Material.POTION);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aEfectos de &bpocion"));
			lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getActivePotionEffects()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(53,item);
			
			
			inv.setContents(target.getInventory().getContents());
			inv.setItem(36, decoracion );
			inv.setItem(37, decoracion1 );
			inv.setItem(38, decoracion );
			inv.setItem(39, decoracion1 );
			inv.setItem(40, decoracion );
			inv.setItem(41, decoracion1 );
			inv.setItem(42, decoracion );
			inv.setItem(43, decoracion1 );
			inv.setItem(44, decoracion );

			inv.setItem(45, target.getInventory().getHelmet());
			inv.setItem(46, target.getInventory().getChestplate());
			inv.setItem(47, target.getInventory().getLeggings());
			inv.setItem(48, target.getInventory().getBoots());
			inv.setItem(49, decoracion1 );
			inv.setItem(50, target.getItemInHand());
			actualizar(config,jugador,target);
		jugador.openInventory(inv);
		return;
	}
	private void actualizar(final FileConfiguration config,final Player jugador,Player target) {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
		taskID = sh.scheduleSyncRepeatingTask(plugin,new Runnable(){
			public void run(){
				if(!updateInventory(config,jugador,target)){
					Bukkit.getScheduler().cancelTask(taskID);
					return;
				}
 
			}
		},0L,20L);
 
	}

	@SuppressWarnings("deprecation")
	protected boolean updateInventory(FileConfiguration config, Player jugador,Player target) {
		
		if(jugador.getOpenInventory().getTopInventory() != null && jugador.getOpenInventory().getTitle().contains("Inventario del jugador")) {
			ItemStack decoracion = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			ItemStack decoracion1 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
			
			jugador.getOpenInventory().getTopInventory().setContents(target.getInventory().getContents());
			
			jugador.getOpenInventory().getTopInventory().setItem(45, target.getInventory().getHelmet());
			jugador.getOpenInventory().getTopInventory().setItem(46, target.getInventory().getChestplate());
			jugador.getOpenInventory().getTopInventory().setItem(47, target.getInventory().getLeggings());
			jugador.getOpenInventory().getTopInventory().setItem(48, target.getInventory().getBoots());
			jugador.getOpenInventory().getTopInventory().setItem(50, target.getItemInHand());
					
			jugador.getOpenInventory().getTopInventory().setItem(36, decoracion );
			jugador.getOpenInventory().getTopInventory().setItem(37, decoracion1 );
			jugador.getOpenInventory().getTopInventory().setItem(38, decoracion );
			jugador.getOpenInventory().getTopInventory().setItem(39, decoracion1 );
			jugador.getOpenInventory().getTopInventory().setItem(40, decoracion );
			jugador.getOpenInventory().getTopInventory().setItem(41, decoracion1 );
			jugador.getOpenInventory().getTopInventory().setItem(42, decoracion );
			jugador.getOpenInventory().getTopInventory().setItem(43, decoracion1 );
			jugador.getOpenInventory().getTopInventory().setItem(44, decoracion );
			jugador.getOpenInventory().getTopInventory().setItem(49, decoracion1 );
			
			ItemStack item = new ItemStack(Material.RED_DYE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cVida"));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getHealth()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			jugador.getOpenInventory().getTopInventory().setItem(51, item);
			
			item = new ItemStack(Material.COOKED_CHICKEN);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Comida"));
			lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getFoodLevel()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			jugador.getOpenInventory().getTopInventory().setItem(52,item);
			
			item = new ItemStack(Material.POTION);
			meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aEfectos de &bpocion"));
			lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b"+target.getActivePotionEffects()));
			meta.setLore(lore);
			item.setItemMeta(meta);
			jugador.getOpenInventory().getTopInventory().setItem(53,item);

		return true;

	}else {
		return false;

	}
	}
	@EventHandler
	public void ClickInventario(InventoryClickEvent event) {
		// inventario Main
		String nombre = ChatColor.translateAlternateColorCodes('&', "&2Inventario del jugador");
		if (event.getView().getTitle().equals(nombre)) {
			event.setCancelled(true);

		}
	}

}
