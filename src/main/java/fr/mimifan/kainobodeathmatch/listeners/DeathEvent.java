package fr.mimifan.kainobodeathmatch.listeners;

import fr.mimifan.kainobodeathmatch.api.ConfigurationFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setDeathMessage(ConfigurationFile.getInstance().getMessage("death-message",
                event.getEntity().getName(), event.getEntity().getKiller().getName()));
        event.getEntity().spigot().respawn();
    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent event) {
        
    }

}
