package net.keinepixel.listener;

import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
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
    }

}
