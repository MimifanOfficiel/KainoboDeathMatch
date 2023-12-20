package fr.mimifan.kainobodeathmatch;

import fr.mimifan.kainobodeathmatch.api.ConfigurationFile;
import fr.mimifan.kainobodeathmatch.items.ItemBuilder;
import fr.mimifan.kainobodeathmatch.listeners.DeathEvent;
import fr.mimifan.kainobodeathmatch.listeners.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin {

    private static Main instance;
    private final HashMap<ItemStack, Integer> inventory = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new JoinEvent(), this);
        manager.registerEvents(new DeathEvent(), this);

        ConfigurationFile.getInstance().load();
        loadMap();
    }

    private void loadMap() {
        inventory.put((new ItemBuilder("iron-helmet")).getItemStack(), 39);
        inventory.put((new ItemBuilder("iron-chestplate")).getItemStack(), 38);
        inventory.put((new ItemBuilder("iron-leggings")).getItemStack(), 37);
        inventory.put((new ItemBuilder("iron-boots")).getItemStack(), 36);

        inventory.put((new ItemBuilder("sword")).getItemStack(), 0);
        inventory.put((new ItemBuilder("bow")).getItemStack(), 1);
        inventory.put((new ItemBuilder("fishing-rod")).getItemStack(), 2);
        inventory.put(new ItemStack(Material.ARROW, 1), 8);
    }

    public HashMap<ItemStack, Integer> getInventory() {
        return inventory;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static Main getInstance() {
        return instance;
    }
}
