package com.poixpixelcustom

import com.earth2me.essentials.Essentials
import com.poixpixelcustom.Commands.EnrichCommand
import com.poixpixelcustom.util.BukkitTools
import com.poixpixelcustom.util.Version
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Callable
import java.util.logging.Logger

class PoixpixelCustom : JavaPlugin() {
    val version = description.version
    private val errors: List<PoixpixelCustom> = ArrayList()
    private val essentials: Essentials? = null
    var isCitizens2 = false
    private var pluginsFound = 0

    init {
        plugin = this
    }

    override fun onEnable() {
        BukkitTools.initialize(this)
        // Load the foundation of PoixpixelCustom, containing config, locales, database, and economy.
        loadFoundation(false)

        // Setup bukkit command interfaces
        registerSpecialCommands()
        registerCommands()

        // Add custom metrics charts.
        addMetricsCharts()

        adventure = BukkitAudiences.create(this)
        checkPlugins()
    }

    override fun onDisable() {
        if (adventure != null) {
            adventure!!.close()
            adventure = null
        }
        log.info(String.format("[%s] Disabled Version %s", description.name, description.version))
    }

    private fun registerSpecialCommands() {
        val commands: List<Command> = ArrayList(4)
        val bukkitCommandMap = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        bukkitCommandMap.isAccessible = true
        val commandMap = bukkitCommandMap[Bukkit.getServer()] as CommandMap
        commandMap.registerAll("poixpixelcustom", commands)
    }

    private fun registerCommands() {
        getCommand("enrich")!!.setExecutor(EnrichCommand())
        logger.info("Added the Enrich command.")
        //this.getCommand("test-economy").setExecutor(new TestEconomyCommand());
        //getLogger().info("Added the Test-Economy command.");
        //this.getCommand("test-permission").setExecutor(new TestPermissionCommand());
        //getLogger().info("Added the Test-Permission command.");
    }

    private fun addMetricsCharts() {
        /*
         * Register bStats Metrics
         */
        val metrics = Metrics(this, 2244)
        metrics.addCustomChart(SimplePie("server_type", Callable {
            if (Bukkit.getServer().name.equals("paper", ignoreCase = true)) return@Callable "Paper" else if (Bukkit.getServer().name.equals("craftbukkit", ignoreCase = true)) {
                if (isSpigotOrDerivative) return@Callable "Spigot" else return@Callable "CraftBukkit"
            }
            "Unknown"
        }))
    }

    fun loadFoundation(reload: Boolean) {
        if (!setupEconomy()) {
            log.warning(String.format("[%s] - No Vault dependency found! Plugin might run with limited functionality.", description.name))
            return
        }
        setupPermissions()
        setupChat()
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java)
                ?: return false
        economy = rsp.provider
        return economy != null
    }

    private fun setupChat(): Boolean {
        val rsp = server.servicesManager.getRegistration(Chat::class.java)
        chat = rsp!!.provider
        return chat != null
    }

    private fun setupPermissions(): Boolean {
        val rsp = server.servicesManager.getRegistration(Permission::class.java)
        permissions = rsp!!.provider
        return permissions != null
    }

    private fun checkPlugins() {
        plugin.logger.info("Searching for third-party plugins...")
        val ecowarn = ""
        val addons: MutableList<String> = ArrayList()
        var test: Plugin?

        /*
         * Check add-ons and third-party plugins we use.
         */test = server.pluginManager.getPlugin("Vault")
        if (test != null) {
            addons.add(String.format("%s v%s", "Vault", test.description.version))
            pluginsFound = pluginsFound + 1
        }
        test = server.pluginManager.getPlugin("Essentials")
        if (test == null) {
            //PoixpixelCustomSettings.setUsingEssentials(false);
            pluginsFound = pluginsFound - 0
        }
        if (pluginsFound > 0) {
            logger.info("Add-on plugins found!")
            logger.info("$pluginsFound add-on plugins found")
        } else {
            logger.warning("No add-on plugins were found.")
            logger.warning("The plugin might run with limited functionality.")
        }
    }

    fun setCitizens2(b: Boolean): PoixpixelCustom {
        return this
    }

    companion object {
        private val log = Logger.getLogger("Minecraft")
        private val OLDEST_MC_VER_SUPPORTED: Version = Version.Companion.fromString("1.19")
        private val CUR_BUKKIT_VER: Version = Version.Companion.fromString(Bukkit.getBukkitVersion())
        private lateinit var plugin: PoixpixelCustom
        private var adventure: BukkitAudiences? = null
        var economy: Economy? = null
            private set
        var permissions: Permission? = null
            private set
        var chat: Chat? = null
            private set

        /**
         * @return the PoixpixelCustom instance
         */
        fun getPlugin(): PoixpixelCustom {
            checkNotNull(plugin) { "Attempted to use getPlugin() while the plugin is null, are you shading PoixpixelCustom?" }
            return plugin
        }

        private val isSpigotOrDerivative: Boolean
            private get() = try {
                Class.forName("org.bukkit.entity.Player\$Spigot")
                true
            } catch (tr: ClassNotFoundException) {
                false
            }
    }
}