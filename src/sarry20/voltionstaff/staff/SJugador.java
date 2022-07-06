package sarry20.voltionstaff.staff;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SJugador {
	  private UUID uuid;
	  
	  private Player player;
	  
	  private Boolean staffMode = Boolean.valueOf(false);
	  
	  private GameMode gm;
	  
	  public SJugador(UUID UUID) {
		    setUuid(UUID);
		    setPlayer(Bukkit.getPlayer(UUID));
		    setStaffMode(false);
		  }
	  
	public UUID getUuid() {
		return uuid;
	}

	public Player getPlayer() {
		return player;
	}

	public Boolean getStaffMode() {
		return staffMode;
	}
	public GameMode getGm() {
		return gm;
	}

	public void setStaffMode(Boolean staffMode) {
		this.staffMode = staffMode;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setGm(GameMode gm) {
		this.gm = gm;
	}

	  
}
