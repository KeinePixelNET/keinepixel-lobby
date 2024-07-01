package net.keinepixel.commands.binder;

import eu.koboo.atcommand.api.binder.PermissionBinderSpigot;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import org.bukkit.command.CommandSender;

public record NoPermissionBinder(LobbyPlugin lobbyPlugin) implements PermissionBinderSpigot {

    @Override
    public void permission(CommandSender commandSender, String s, String s1) {
        //TODO: Get sender language via API
        //TODO: Language Key: generic.no-permission
        commandSender.sendMessage(ChatAction.of(lobbyPlugin.getPrefix()).with("§cYou don't have permission to execute this command!").comp());
    }
}
