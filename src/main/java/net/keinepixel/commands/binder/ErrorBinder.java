package net.keinepixel.commands.binder;

import eu.koboo.atcommand.api.binder.ErrorBinderSpigot;
import eu.koboo.atcommand.api.binder.OnlyPlayerBinderSpigot;
import io.netty.util.internal.ThrowableUtil;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public record ErrorBinder(LobbyPlugin lobbyPlugin) implements ErrorBinderSpigot {

    @Override
    public void error(CommandSender commandSender, Throwable throwable, String s) {
        if (throwable instanceof IllegalArgumentException) {
            commandSender.sendMessage(ChatAction.of(lobbyPlugin.getPrefix()).with("§c" + throwable.getMessage()).comp());
        } else {
            commandSender.sendMessage(ChatAction.of(lobbyPlugin.getPrefix()).with("§cAn error occurred while executing the command!").comp());
            //TODO: Log error to file and upload to a paste server
            //TODO: Then, the user can send the link to the support.
            ThrowableUtil.stackTraceToString(throwable).lines().forEach(commandSender::sendMessage);
        }
    }
}
