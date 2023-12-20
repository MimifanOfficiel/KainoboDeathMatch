package fr.mimifan.kainobodeathmatch.api;

import fr.mimifan.kainobodeathmatch.Main;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationFile extends YamlConfigurationFile {
    private static ConfigurationFile instance = new ConfigurationFile(Main.getInstance());
    public ConfigurationFile(JavaPlugin plugin) {
        super("config", plugin);
    }

    public static ConfigurationFile getInstance() {
        return instance;
    }
}