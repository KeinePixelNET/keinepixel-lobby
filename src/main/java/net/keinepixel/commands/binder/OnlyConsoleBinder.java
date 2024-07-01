package net.keinepixel.commands.binder;

import eu.koboo.atcommand.api.binder.OnlyConsoleBinderSpigot;
import eu.koboo.atcommand.api.binder.OnlyPlayerBinderSpigot;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class OnlyConsoleBinder implements OnlyConsoleBinderSpigot {

    @Override
    public void onlyConsole(Player player) {
        //TODO: Get sender language via API
        //TODO: Language Key: command.generic.only-console
        player.sendMessage(ChatAction.of(LobbyPlugin.getInstance().getPrefix()).with("§cOnly the console can execute this command!").comp());
    }
}
