package com.poixpixelcustom.util

import com.google.common.base.Charsets
import com.poixpixelcustom.PoixpixelCustom
import net.citizensnpcs.api.CitizensAPI
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.plugin.PluginManager
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import java.util.*
import java.util.stream.Collectors

object BukkitTools {
    private var plugin: PoixpixelCustom? = null
    private var server: Server? = null
    fun initialize(plugin: PoixpixelCustom) {
        BukkitTools.plugin = plugin
        server = plugin.server
    }

    val onlinePlayers: Collection<Player>
        /**
         * Get an array of all online players
         *
         * @return array of online players
         */
        get() = getServer()!!.onlinePlayers

    fun matchPlayer(name: String): List<Player> {
        val matchedPlayers: MutableList<Player> = java.util.ArrayList()
        for (iterPlayer in Bukkit.getOnlinePlayers()) {
            val iterPlayerName = iterPlayer.name
            if (checkCitizens(iterPlayer)) {
                continue
            }
            if (name.equals(iterPlayerName, ignoreCase = true)) {
                // Exact match
                matchedPlayers.clear()
                matchedPlayers.add(iterPlayer)
                break
            }
            if (iterPlayerName.lowercase().contains(name.lowercase())) {
                // Partial match
                matchedPlayers.add(iterPlayer)
            }
        }
        return matchedPlayers
    }

    /**
     * Given a name this method should only return a UUID that is stored in the server cache,
     * without pinging Mojang servers.
     *
     * @param name - Resident/Player name to get a UUID for.
     * @return UUID of player or null if the player is not in the cache.
     */
    fun getUUIDSafely(name: String?): UUID? {
        return if (hasPlayedBefore(name)) getOfflinePlayer(name).uniqueId else null
    }

    fun getPlayerExact(name: String?): Player? {
        return getServer()!!.getPlayerExact(name!!)
    }

    fun getPlayer(playerId: String?): Player? {
        return getServer()!!.getPlayer(playerId!!)
    }

    fun getPlayer(playerUUID: UUID?): Player? {
        return server!!.getPlayer(playerUUID!!)
    }

    fun getVisibleOnlinePlayers(sender: CommandSender): Collection<Player> {
        return if (sender !is Player) Bukkit.getOnlinePlayers() else Bukkit.getOnlinePlayers().stream()
                .filter { player: Player? -> sender.canSee(player!!) }
                .collect(Collectors.toCollection { ArrayList() })
    }

    /**
     * Tests if this player is online.
     *
     * @param name the name of the player.
     * @return a true value if online
     */
    fun isOnline(name: String?): Boolean {
        return Bukkit.getPlayerExact(name!!) != null
    }

    val worlds: List<World>
        get() = getServer()!!.worlds

    fun getWorld(name: String?): World? {
        return getServer()!!.getWorld(name!!)
    }

    fun getWorld(worldUID: UUID?): World? {
        return getServer()!!.getWorld(worldUID!!)
    }

    fun getServer(): Server? {
        synchronized(server!!) { return server }
    }

    val pluginManager: PluginManager
        get() = getServer()!!.pluginManager
    val scheduler: BukkitScheduler
        get() = getServer()!!.scheduler

    /**
     * Accepts a Runnable object and a delay (-1 for no delay)
     *
     * @param task runnable object
     * @param delay ticks to delay starting
     * @return -1 if unable to schedule or an index to the task is successful.
     */
    fun scheduleSyncDelayedTask(task: Runnable?, delay: Long): Int {
        return scheduler.scheduleSyncDelayedTask(plugin!!, task!!, delay)
    }

    /**
     * Accepts a [Runnable] object and a delay (-1 for no delay)
     *
     * @param task - Runnable
     * @param delay - ticks to delay starting ([Long])
     * @return -1 if unable to schedule or an index to the task is successful.
     */
    fun scheduleAsyncDelayedTask(task: Runnable?, delay: Long): Int {
        return scheduler.runTaskLaterAsynchronously(plugin!!, task!!, delay).taskId
    }

    /**
     * Accepts a [Runnable] object with a delay/repeat (-1 for no delay)
     *
     * @param task runnable object
     * @param delay ticks to delay starting ([Long])
     * @param repeat ticks to repeat after ([Long])
     * @return -1 if unable to schedule or an index to the task is successful.
     */
    fun scheduleSyncRepeatingTask(task: Runnable?, delay: Long, repeat: Long): Int {
        return scheduler.scheduleSyncRepeatingTask(plugin!!, task!!, delay, repeat)
    }

    /**
     * Accepts a [Runnable] object with a delay/repeat (-1 for no delay)
     *
     * @param task runnable object
     * @param delay ticks to delay starting ([Long])
     * @param repeat ticks to repeat after ([Long])
     * @return -1 if unable to schedule or an index to the task is successful.
     */
    fun scheduleAsyncRepeatingTask(task: Runnable?, delay: Long, repeat: Long): Int {
        return scheduler.runTaskTimerAsynchronously(plugin!!, task!!, delay, repeat).taskId
    }

    val playersPerWorld: HashMap<String, Int>
        /**
         * Count the number of players online in each world
         *
         * @return Map of world to online players.
         */
        get() {
            val m = HashMap<String, Int>()
            for (world in getServer()!!.worlds) m[world.name] = 0
            for (player in getServer()!!.onlinePlayers) m[player.world.name] = m[player.world.name]!! + 1
            return m
        }

    @Suppress("deprecation")
    fun hasPlayedBefore(name: String?): Boolean {
        return getServer()!!.getOfflinePlayer(name!!).hasPlayedBefore()
    }

    /**
     * Do not use without first using [.hasPlayedBefore]
     *
     * @param name - name of resident
     * @return OfflinePlayer
     */
    @Suppress("deprecation")
    fun getOfflinePlayer(name: String?): OfflinePlayer {
        return Bukkit.getOfflinePlayer(name!!)
    }

    fun getOfflinePlayerForVault(name: String): OfflinePlayer {
        return Bukkit.getOfflinePlayer(UUID.nameUUIDFromBytes("OfflinePlayer:$name".toByteArray(Charsets.UTF_8)))
    }

    fun convertCoordtoXYZ(loc: Location): String {
        return loc.world.name + " " + loc.blockX + "," + loc.blockY + "," + loc.blockZ
    }

    val worldNames: List<String>
        get() = worlds.stream().map { obj: World -> obj.name }.collect(Collectors.toList())

    fun getWorldNames(lowercased: Boolean): List<String> {
        return if (lowercased) worlds.stream().map { world: World -> world.name.lowercase(Locale.getDefault()) }.collect(Collectors.toList()) else worldNames
    }

    /**
     * Check if the entity is a Citizens NPC.
     *
     * Catches the NoClassDefFoundError thrown when Citizens is present
     * but failed to start up correctly.
     *
     * @param entity Entity to check.
     * @return true if the entity is an NPC.
     */
    fun checkCitizens(entity: Entity?): Boolean {
        if (plugin!!.isCitizens2) {
            try {
                return CitizensAPI.getNPCRegistry().isNPC(entity)
            } catch (e: NoClassDefFoundError) {
                plugin!!.setCitizens2(false)
            }
        }
        return false
    }

    @Suppress("deprecation")
    fun objective(board: Scoreboard, name: String, displayName: String): Objective {
        val objective: Objective
        objective = board.registerNewObjective(name, Criteria.DUMMY, displayName)
        return objective
    }

    /**
     * @param event The event to call
     * @return `true` if the event is cancellable and was cancelled, otherwise `false`.
     */
    fun isEventCancelled(event: Event): Boolean {
        fireEvent(event)
        return if (event is Cancellable) event.isCancelled else false
    }

    fun fireEvent(event: Event) {
        Bukkit.getPluginManager().callEvent(event)
    }

    /**
     * Used to parse user-inputted material names into valid material names.
     *
     * @param name String which should be a material.
     * @return String name of the material or null if no match could be made.
     */
    fun matchMaterialName(name: String): String? {
        val mat = Material.matchMaterial(name.trim { it <= ' ' }.uppercase())
        return mat?.name
    }
}