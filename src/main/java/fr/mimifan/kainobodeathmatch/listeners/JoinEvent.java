package fr.mimifan.kainobodeathmatch.listeners;

import fr.mimifan.kainobodeathmatch.Main;

import fr.mimifan.kainobodeathmatch.api.ConfigurationFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.teleport(ConfigurationFile.getInstance().getLocation("spawn"));
        player.getInventory().clear();
        player.getInventory().setHeldItemSlot(0);
        Main.getInstance().getInventory().forEach( (item, slot) -> player.getInventory().setItem(slot, item));
    }

}
