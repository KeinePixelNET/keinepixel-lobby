package net.keinepixel.listener;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudProxyGroup;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import net.keinepixel.utility.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinListener(LobbyPlugin lobbyPlugin) implements Listener {

    @EventHandler
    public void handlePlayerJoinEvent(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.joinMessage(null);

        //TODO: Get player language via API
        //TODO: Language Key: lobby.join-message
        player.sendMessage(ChatAction.of(lobbyPlugin.getPrefix()).with("lobby.join-message").comp());

        //TODO: Get player language via API
        //TODO: Language Key: lobby.items.navigation
        player.getInventory().setItem(4, ItemBuilder.of(Material.HEART_POTTERY_SHERD)
                .displayName("lobby.items.navigation")
                .lore("lobby.items.navigation-lore")
                .unbreakable()
                .build()
        );
        player.getInventory().setItem(8, ItemBuilder.of(Material.BREWER_POTTERY_SHERD)
                .displayName("lobby.items.lobby-selector")
                .lore("lobby.items.lobby-selector-lore")
                .unbreakable()
                .build()
        );

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            ICloudService proxyGroup = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Proxy-1");
            if (proxyGroup == null) return;
            int allPlayers = proxyGroup.getOnlineCount();
            onlinePlayer.setLevel(allPlayers);
        });
    }

}
