package com.poixpixelcustom.listeners;

import com.poixpixelcustom.constants.Keys;
import com.poixpixelcustom.utils.ConfigHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;

public class EntityListener implements Listener {

    // FIXME: Figure out why this does not work
    private final float explosionPower = ConfigHandler.getInstance().getExplosionPower();

    /**
     * Handles the event when a player right-clicks on an entity with the main hand item.
     * If the entity is of the exploding type, has the custom entity tag, and the main hand item has the custom bucket tag,
     * and the player has the permission to use the exploding entity, it creates an explosion at the entity's location.
     * Otherwise, it sends a message to the player.
     *
     * @param event the PlayerInteractEntityEvent representing the right-click event
     */
    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            PersistentDataContainer entityContainer = entity.getPersistentDataContainer();
            PersistentDataContainer handItemContainer = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();

            if (entity.getType() == ConfigHandler.getInstance().getExplodingType()
                    && entityContainer.has(Keys.CUSTOM_ENTITY)
                    && handItemContainer.has(Keys.CUSTOM_BUCKET)) {

                if (!player.hasPermission("poixpixelcustom.explodingentity.use")) {
                    player.sendMessage(Component.text("You don't have permission to left click this entity!").color(NamedTextColor.RED));

                    return;
                }

                player.sendMessage(Component.text(String.valueOf(explosionPower)));
                entity.getWorld().createExplosion(entity.getLocation(), explosionPower);
            }
        }
    }
}
