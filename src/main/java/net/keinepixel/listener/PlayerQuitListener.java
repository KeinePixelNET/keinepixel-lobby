package net.keinepixel.listener;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudProxyGroup;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import net.keinepixel.utility.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerQuitListener(LobbyPlugin lobbyPlugin) implements Listener {

    @EventHandler
    public void handlePlayerQuitEvent(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        event.quitMessage(null);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            ICloudService proxyGroup = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Proxy-1");
            if (proxyGroup == null) return;
            int allPlayers = proxyGroup.getOnlineCount();
            onlinePlayer.setLevel(allPlayers);
        });
    }

}
