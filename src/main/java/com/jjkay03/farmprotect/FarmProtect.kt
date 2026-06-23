package com.jjkay03.farmprotect

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class FarmProtect : JavaPlugin(), Listener, CommandExecutor {

    private companion object {
        const val BYPASS_PERMISSION = "farmprotect.bypass"
    }

    private var protectFromPlayers = true
    private var protectFromMobs = true
    private var disabledWorlds: Set<String> = emptySet()

    override fun onEnable() {
        saveDefaultConfig()
        loadSettings()
        server.pluginManager.registerEvents(this, this)
        getCommand("farmprotect")?.setExecutor(this)
        logger.info("FarmProtect enabled.")
    }

    private fun loadSettings() {
        protectFromPlayers = config.getBoolean("protect-from-players", true)
        protectFromMobs = config.getBoolean("protect-from-mobs", true)
        disabledWorlds = config.getStringList("disabled-worlds")
            .mapTo(HashSet()) { it.lowercase() }
    }

    private fun isProtectionDisabledIn(worldName: String): Boolean =
        worldName.lowercase() in disabledWorlds

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityInteract(event: EntityInteractEvent) {
        if (!protectFromMobs) return
        if (event.block.type != Material.FARMLAND) return
        if (isProtectionDisabledIn(event.block.world.name)) return
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!protectFromPlayers) return
        if (event.action != Action.PHYSICAL) return
        val block = event.clickedBlock ?: return
        if (block.type != Material.FARMLAND) return
        if (isProtectionDisabledIn(block.world.name)) return
        if (event.player.hasPermission(BYPASS_PERMISSION)) return
        event.isCancelled = true
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        if (args.size != 1 || !args[0].equals("reload", ignoreCase = true)) {
            sender.sendMessage("Usage: /$label reload")
            return true
        }
        reloadConfig()
        loadSettings()
        sender.sendMessage("FarmProtect configuration reloaded.")
        return true
    }
}
