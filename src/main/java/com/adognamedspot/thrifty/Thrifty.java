package com.adognamedspot.thrifty;

import com.willfp.eco.core.drops.DropQueue;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Thrifty extends EcoEnchant {
    public Thrifty() {
        super(
                "thrifty", EnchantmentType.NORMAL
        );
    }

    @EventHandler
    public void onDeath(@NotNull final EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!this.areRequirementsMet(player)) {
            return;
        }

        LivingEntity victim = event.getEntity();

        if (!EnchantChecks.mainhand(player, this)) {
            return;
        }

        int level = EnchantChecks.getMainhandLevel(player, this);

        if (!EnchantmentUtils.passedChance(this, level)) {
            return;
        }

        if (this.getDisabledWorlds().contains(player.getWorld())) {
            return;
        }

        ItemStack item;

        if (victim instanceof Player) {
            return;
        } else {
            item = getEgg(event.getEntityType());
            if (item == null) {
                return;
            }
        }

        new DropQueue(player)
                .addItem(item)
                .addXP(event.getDroppedExp())
                .setLocation(victim.getLocation())
                .push();

        event.setDroppedExp(0);
    }

    private ItemStack getEgg(@NotNull final EntityType type) {
        
        if (type.name().equals("ENDER_DRAGON")) {
            return null;
        }
        if (type.name().equals("IRON_GOLEM")) {
            return null;
        }
        if (type.name().equals("WITHER")) {
            return null;
        }
        if (type.name().equals("SNOWMAN")) {
            return null;
        }
        
        ItemStack egg = new ItemStack(Material.EGG);
        egg.setType(Material.getMaterial(type.name() + "_SPAWN_EGG"));
        return egg;
    }
}
    
