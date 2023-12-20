package fr.mimifan.kainobodeathmatch.items;

import fr.mimifan.kainobodeathmatch.api.ConfigurationFile;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private final ItemStack item;
    public ItemBuilder(String name) {
        item = new ItemStack(ConfigurationFile.getInstance().getMaterial(name), 1);
        ItemMeta meta = item.getItemMeta();

        String itemName = ConfigurationFile.getInstance().getItemName(name);
        if(itemName != null && !itemName.isEmpty()) meta.setDisplayName(itemName);
        meta.setLore(ConfigurationFile.getInstance().getLore(name));

        meta.spigot().setUnbreakable(ConfigurationFile.getInstance().isUnbreakable(name));
        item.setItemMeta(meta);

        if(ConfigurationFile.getInstance().getEnchantments(name).size() > 0) item.addEnchantments(ConfigurationFile.getInstance().getEnchantments(name));
    }


    public ItemStack getItemStack() {
        return item;
    }
}
