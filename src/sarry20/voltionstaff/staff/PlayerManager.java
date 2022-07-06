package sarry20.voltionstaff.staff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class PlayerManager {
	 private static PlayerManager playerManager;
	  
	  private Map<UUID, SJugador> players = new HashMap<>();
	  
	  public static PlayerManager get() {
	    if (playerManager == null)
	      playerManager = new PlayerManager(); 
	    return playerManager;
	  }
	  
	  public Map<UUID, SJugador> getPlayers() {
	    return this.players;
	  }
	  
	  public SJugador getPlayer(UUID uUID) {
	    return this.players.get(uUID);
	  }
	  
	  public boolean doesPlayerExists(UUID uUID) {
	    return this.players.containsKey(uUID);
	  }
	  
	  public void createPlayer(Player p) {
	    this.players.put(p.getUniqueId(), new SJugador(p.getUniqueId()));
	  }
	  
	  public void createPlayer(OfflinePlayer p) {
	    this.players.put(p.getUniqueId(), new SJugador(p.getUniqueId()));
	  }
	  
	  public Set<SJugador> PlayersSet(Set<UUID> set) {
	    HashSet<SJugador> hashSet = new HashSet<>();
	    for (UUID uUID : set)
	      hashSet.add(getPlayer(uUID)); 
	    return hashSet;
	  }
	  
	  public Set<SJugador> PlayersSet() {
	    HashSet<SJugador> hashSet = new HashSet<>();
	    for (UUID uUID : getPlayers().keySet())
	      hashSet.add(getPlayer(uUID)); 
	    return hashSet;
	  }
}
