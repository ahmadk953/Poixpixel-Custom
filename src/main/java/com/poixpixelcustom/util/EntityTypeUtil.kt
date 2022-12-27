package com.poixpixelcustom.util

import org.bukkit.Material
import org.bukkit.entity.EntityType
import java.util.*

object EntityTypeUtil {
    private val ExplosiveEntityTypes = Arrays.asList(
            EntityType.CREEPER,
            EntityType.DRAGON_FIREBALL,
            EntityType.FIREBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.FIREWORK,
            EntityType.MINECART_TNT,
            EntityType.PRIMED_TNT,
            EntityType.WITHER,
            EntityType.WITHER_SKULL,
            EntityType.ENDER_CRYSTAL)
    private val ExplosivePVMEntityTypes = Arrays.asList(
            EntityType.CREEPER,
            EntityType.DRAGON_FIREBALL,
            EntityType.FIREBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.WITHER,
            EntityType.WITHER_SKULL,
            EntityType.ENDER_CRYSTAL)
    private val ExplosivePVPEntityTypes = Arrays.asList(
            EntityType.FIREWORK,
            EntityType.MINECART_TNT,
            EntityType.PRIMED_TNT,
            EntityType.ENDER_CRYSTAL)

    fun isInstanceOfAny(classes: List<Class<*>>, obj: Any?): Boolean {
        for (c in classes) if (c.isInstance(obj)) return true
        return false
    }

    /**
     * Helper method to get a Material from an Entity.
     * Used with protection tests in plots.
     *
     * @param entityType EntityType to gain a Material value for.
     * @return null or a suitable Material.
     */
    fun parseEntityToMaterial(entityType: EntityType?): Material? {
        return when (entityType) {
            EntityType.AXOLOTL -> Material.AXOLOTL_BUCKET
            EntityType.COD -> Material.COD
            EntityType.SALMON -> Material.SALMON
            EntityType.PUFFERFISH -> Material.PUFFERFISH
            EntityType.TROPICAL_FISH -> Material.TROPICAL_FISH
            EntityType.TADPOLE -> Material.TADPOLE_BUCKET
            EntityType.ITEM_FRAME -> Material.ITEM_FRAME
            EntityType.GLOW_ITEM_FRAME -> Material.GLOW_ITEM_FRAME
            EntityType.PAINTING -> Material.PAINTING
            EntityType.ARMOR_STAND -> Material.ARMOR_STAND
            EntityType.LEASH_HITCH -> Material.LEAD
            EntityType.ENDER_CRYSTAL -> Material.END_CRYSTAL
            EntityType.MINECART, EntityType.MINECART_MOB_SPAWNER -> Material.MINECART
            EntityType.MINECART_CHEST -> Material.CHEST_MINECART
            EntityType.MINECART_FURNACE -> Material.FURNACE_MINECART
            EntityType.MINECART_COMMAND -> Material.COMMAND_BLOCK_MINECART
            EntityType.MINECART_HOPPER -> Material.HOPPER_MINECART
            EntityType.MINECART_TNT -> Material.TNT_MINECART
            EntityType.BOAT -> Material.OAK_BOAT
            EntityType.CHEST_BOAT -> Material.OAK_CHEST_BOAT
            else -> null
        }
    }

    /**
     * Helper method for parsing an entity to a material, or a default material if none is found.
     * @param entityType Entity type to parse
     * @param defaultValue Material to use if none could be found.
     * @return The parsed material, or the fallback value.
     */
    fun parseEntityToMaterial(entityType: EntityType?, defaultValue: Material): Material {
        val material = parseEntityToMaterial(entityType)
        return material ?: defaultValue
    }

    /**
     * A list of explosion-causing entities.
     *
     * @param entityType EntityType to test.
     * @return true if the EntityType will explode.
     */
    fun isExplosive(entityType: EntityType): Boolean {
        return ExplosiveEntityTypes.contains(entityType)
    }

    /**
     * A list of PVP explosion-causing entities.
     *
     * @param entityType EntityType to test.
     * @return true if the EntityType is PVP and will explode.
     */
    fun isPVPExplosive(entityType: EntityType): Boolean {
        return ExplosivePVPEntityTypes.contains(entityType)
    }

    /**
     * A list of PVM explosion-causing entities.
     *
     * @param entityType EntityType to test.
     * @return true if the EntityType is PVM and will explode.
     */
    fun isPVMExplosive(entityType: EntityType): Boolean {
        return ExplosivePVMEntityTypes.contains(entityType)
    }
}