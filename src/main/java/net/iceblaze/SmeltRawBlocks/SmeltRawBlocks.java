package net.iceblaze.SmeltRawBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class SmeltRawBlocks extends JavaPlugin {
    public void onEnable(){
        saveDefaultConfig();
        for(Map<?, ?> section : getConfig().getMapList("recipes")) {
            String toS = (String) section.get("to");
            ItemStack result = new ItemStack(Objects.requireNonNull(Material.getMaterial(toS)));
            String fromS = (String) section.get("from");
            Material from = Material.getMaterial(fromS);
            assert from != null;
            float xp = (float) (double) section.get("XP");
            int time = (int) section.get("time");
            NamespacedKey key = new NamespacedKey(this, (toS + "_from_" + section.get("type") + "_" + fromS).toLowerCase());
            Recipe r;
            if(section.get("type").equals("BLASTING")) {
                r = new BlastingRecipe(key, result, from, xp, time);
            } else {
                r = new FurnaceRecipe(key, result, from, xp, time);
            }
            Bukkit.addRecipe(r);
            getLogger().log(Level.INFO, "Registered Recipe: " + key);
        }
    }
}
