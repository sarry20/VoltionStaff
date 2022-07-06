package sarry20.voltionstaff.Eventos;

import java.util.ArrayList;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import sarry20.voltionstaff.Main;
import sarry20.voltionstaff.MySQL.SQLPlayerData;
import sarry20.voltionstaff.staff.PlayerManager;
import sarry20.voltionstaff.staff.SJugador;

public class PlayerListener implements Listener {
	private Main plugin;
	protected boolean suspend = false;
	
	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void alEntrar(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(p.hasPermission("vstaff.staff")) {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lTeletransportador"));
		item.setItemMeta(meta);
		if(SQLPlayerData.getStopValue(plugin.getMySQL(), p.getUniqueId()) == true ) {
			if(PlayerManager.get().doesPlayerExists(p.getUniqueId()) == false)
				PlayerManager.get().createPlayer(p);
		    SJugador sp = PlayerManager.get().getPlayer(p.getUniqueId());
		    
				sp.setStaffMode(Boolean.valueOf(false));
				p.setGameMode(GameMode.SURVIVAL);
				ItemStack[] items;
				ItemStack[] armor;
				items = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),1);
				armor = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),4);
				p.getInventory().setContents(items);
				p.getInventory().setArmorContents(armor);			
				SQLPlayerData.setStopValue(plugin.getMySQL(), p.getUniqueId(), false);

		}
		if(p.getInventory().contains(item)) {
			ItemStack[] items;
			ItemStack[] armor;
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&cV&6Staff&7]&c No se pudo cargar tu ultimo inventario" ));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7[&cV&6Staff&7]&a Cargando copia mas reciente..." ));
			items = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),2);
			armor = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),5);
			p.getInventory().setContents(items);
			p.getInventory().setArmorContents(armor);


		}
		if(p.hasPermission("vstaff.staff")) {
		if(!SQLPlayerData.staffExist(plugin.getMySQL(), p.getUniqueId())) {
			SQLPlayerData.createStaff(plugin.getMySQL(), p.getUniqueId(), p.getName());
		}
		}else if(!(p.hasPermission("vstaff.staff"))) {
			if(!SQLPlayerData.playerExist(plugin.getMySQL(), p.getUniqueId())) {
				SQLPlayerData.createPlayer(plugin.getMySQL(), p.getUniqueId(), p.getName());
			}
		}
		if(p.hasPermission("vstaff.staff")) {
			plugin.staffson++;
		}
		}
	}
	@EventHandler (priority=EventPriority.HIGH)
	public void alSalir(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(p.getUniqueId()) == false)
			PlayerManager.get().createPlayer(p);
	    SJugador sp = PlayerManager.get().getPlayer(p.getUniqueId());
		if(sp.getStaffMode() == true) {
			sp.setStaffMode(Boolean.valueOf(false));
			p.setGameMode(sp.getGm());
			ItemStack[] items;
			ItemStack[] armor;
			items = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),1);
			armor = SQLPlayerData.getInventory(plugin.getMySQL(), p.getUniqueId(),4);
			p.getInventory().setContents(items);
			p.getInventory().setArmorContents(armor);			
		}else {

			SQLPlayerData.setInventory(plugin.getMySQL(), p.getUniqueId(), 2, p.getInventory().getContents(),p.getInventory().getArmorContents());
			SQLPlayerData.setInventory(plugin.getMySQL(), p.getUniqueId(), 5, p.getInventory().getContents(),p.getInventory().getArmorContents());

		}
		if(p.hasPermission("vstaff.staff")) {
			plugin.staffson--;
		}
		if(plugin.frozen.contains(p.getName())) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(pl.hasPermission("vstaff.staff")){
					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&cV&6Staff&7]&e El usuario &c "+p.getName()+" &ese ha desconectado estando congelado" ));
				}
					
			}
		}

	}
	@EventHandler
	public void alMover(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (plugin.frozen.contains(p.getName())) {
            event.setTo(event.getFrom());
            p.sendMessage(ChatColor.RED + "¡Estas congelado!");
	}
	}
	
	@EventHandler
	public void alClick(PlayerInteractEvent event) {
		ConsoleCommandSender consola = Bukkit.getServer().getConsoleSender();
		Player jugador =  event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
		ArrayList<Player> playerson = new ArrayList<Player>();
		int players = 0;
			if(jugador.hasPermission("vstaff.staff")) {
				ItemStack item= new ItemStack(Material.FEATHER);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lFly"));
				item.setItemMeta(meta);
				try {
				if(event.getItem().equals(item) && sp.getStaffMode().booleanValue() == true) {
					Bukkit.dispatchCommand(consola, "fly "+jugador.getName());
					
					

				}
				item = new ItemStack(Material.CLOCK);
				meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lRandomTP"));
				item.setItemMeta(meta);
				if(event.getItem().equals(item) && sp.getStaffMode().booleanValue() == true) {

					for(Player p : Bukkit.getOnlinePlayers()) {
						if(!p.hasPermission("vstaff.staff")) {
						playerson.add(p);
						players++;
						}
					}

					if(players != 0) {

						 int ran = (int) Math.floor(Math.random()*players);
//					System.out.println("player a elegir = "+playerson.get(ran));
					
					Player p2tp = playerson.get(ran);
					
						jugador.teleport(p2tp);
					
					
					} 
					

				}	
				item = new ItemStack(Material.GREEN_DYE);
				meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lVanish"));
				item.setItemMeta(meta);
				if(event.getItem().equals(item) && sp.getStaffMode().booleanValue() == true){
					Bukkit.dispatchCommand(consola, "vanish "+jugador.getName());

				}
			}catch(NullPointerException ex){
				
			}
			}
		
	}
	@EventHandler
	public void creativeClick(InventoryCreativeEvent event) {
		Player jugador = (Player) event.getWhoClicked();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
		if(sp.getStaffMode().booleanValue() == true) 
			event.setCancelled(true);

	}
	@EventHandler
	public void romper (BlockBreakEvent event) {
		Player jugador = event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
		if(sp.getStaffMode() == true) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void coger(EntityPickupItemEvent event) {
		if(event.getEntityType().equals(EntityType.PLAYER)) {
			Player jugador = (Player) event.getEntity();
			
			if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
				PlayerManager.get().createPlayer(jugador);
		    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
			if(sp.getStaffMode() == true) {
				event.setCancelled(true);
			}
		}

	}
	@EventHandler
	public void soltar(PlayerDropItemEvent event) {
		Player jugador = event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
		if(sp.getStaffMode() == true) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void click(InventoryClickEvent event) {
		Player jugador = (Player) event.getWhoClicked();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
		if(sp.getStaffMode() == true) {
			event.setCancelled(true);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void pinteracte(PlayerInteractEntityEvent event) {
		Player jugador = (Player) event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());
	    if(event.getRightClicked().getType().equals(EntityType.PLAYER) ) {
			Player target = (Player) event.getRightClicked();

	    
	
	    
		
		ItemStack item = new ItemStack(Material.PACKED_ICE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lFreeze"));
		item.setItemMeta(meta);
		if(jugador.getItemInHand().equals(item) && sp.getStaffMode().booleanValue() == true) {

			Bukkit.dispatchCommand(jugador, "freeze "+target.getName());


		}
		item = new ItemStack(Material.CHEST);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lInvsee"));
		item.setItemMeta(meta);
		 if(jugador.getItemInHand().equals(item) && sp.getStaffMode().booleanValue() == true) {

			Inventario inv = new Inventario(plugin);
			inv.crearInventario(jugador, target);
			
		}
	    }
	}
	@EventHandler
	public void pinteracte(BlockPlaceEvent event) {
		Player jugador = (Player) event.getPlayer();
		if(PlayerManager.get().doesPlayerExists(jugador.getUniqueId()) == false)
			PlayerManager.get().createPlayer(jugador);
	    SJugador sp = PlayerManager.get().getPlayer(jugador.getUniqueId());

		if(sp.getStaffMode().booleanValue() == true) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pdeath(PlayerDeathEvent event) {
		Player p = (Player) event.getEntity();
		if(event.getEntityType().equals(EntityType.PLAYER))
		if(!p.hasPermission("vstaff.staff")) {
			SQLPlayerData.setInventory(plugin.getMySQL(), p.getUniqueId(), 3, p.getInventory().getContents(),p.getInventory().getArmorContents());
		}

	}
}
