package fr.mimifan.kainobodeathmatch.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YamlConfigurationFile {

    private final String fileName;

    private final JavaPlugin plugin;

    private YamlConfiguration configuration;

    private File file;

    public YamlConfigurationFile(String fileName, JavaPlugin plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.configuration = null;
        this.file = null;

        this.saveDefault();
    }

    public void load() {
        this.file = new File(this.plugin.getDataFolder(), this.fileName + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);

        InputStream defaultStream = this.plugin.getResource(this.fileName + ".yml");

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.configuration.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getConfiguration() {
        if (this.configuration == null) {
            this.load();
        }

        return this.configuration;
    }

    public void save() {
        if (this.configuration == null || this.file == null) {
            return;
        }

        try {
            this.getConfiguration().save(this.file);
        } catch (Exception e) {
            this.plugin.getLogger().severe("Could not save configuration file " + this.file.getName() + "!");
        }
    }

    public void saveDefault() {
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), this.fileName + ".yml");
        }

        if (!this.file.exists()) {
            this.plugin.saveResource(this.fileName + ".yml", false);
        }
    }

    public String getMessage(String path) {
        String message = this.getConfiguration().getString(path);

        if (message == null) return path;


        return message.replace("&", "§");
    }

    public boolean getBoolean(String path){ return this.getConfiguration().getBoolean(path); }

    public List<String> getMessageList(String path) {
        List<String> messages = this.getConfiguration().getStringList(path);

        if (messages == null) return new ArrayList<>();

        messages.replaceAll(s -> s.replace("&", "§"));
        return messages;
    }

    public List<String> getMessageList(String path, String... args) {
        List<String> messages = this.getConfiguration().getStringList(path);
        if (messages == null) return new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < messages.size(); j++) {
                messages.set(j, messages.get(j).replace("{" + i + "}", args[i]));
            }
        }

        messages.replaceAll(s -> s.replace("&", "§"));
        return messages;
    }

    public String getMessage(String path, String... args) {
        String message = this.getConfiguration().getString(path);

        if (message == null) {
            return path;
        }

        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i]);
        }

        return message.replace("&", "§");
    }

    public Location getLocation(String name) {
        Location location = new Location(Bukkit.getWorld(getMessage(name) + ".world"),
                getConfiguration().getInt(name + ".x"), getConfiguration().getInt(name + ".y"),
                getConfiguration().getInt(name + ".z"));
        location.setYaw((float)getConfiguration().getDouble(name + ".yaw"));
        location.setPitch((float)getConfiguration().getDouble(name + ".pitch"));
        return location;
    }

    public Material getMaterial(String tag){ return Material.matchMaterial(this.getConfiguration().getString(tag + ".item")); }
    public String getItemName(String tag) {
        String name = this.getConfiguration().getString(tag + ".name");

        if (name == null) return "";

        return name.replace("&", "§");
    }

    public List<String> getLore(String tag) { return getMessageList(tag + ".lore"); }
    public List<String> getLore(String tag, String... args) { return getMessageList(tag + ".lore", args); }

    public boolean isUnbreakable(String tag) {
        return getConfiguration().contains(tag + ".unbreakable") && getBoolean(tag + ".unbreakable");
    }

    public boolean hideEnchants(String tag) { return getBoolean(tag + ".hide-enchants"); }

    public HashMap<Enchantment, Integer> getEnchantments(String tag) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        if(!getConfiguration().contains(tag + ".enchantments")) return enchants;
        for(String key : getConfiguration().getConfigurationSection(tag + ".enchantments").getKeys(false)) {
            enchants.put(Enchantment.getByName(key), getConfiguration().getConfigurationSection(tag + ".enchantments").getInt(key));
        }
        return enchants;
    }

}