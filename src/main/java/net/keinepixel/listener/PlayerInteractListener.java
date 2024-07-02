package net.keinepixel.listener;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.player.SimpleCloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudLobbyGroup;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.utility.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record PlayerInteractListener(LobbyPlugin plugin) implements Listener {

    @EventHandler
    public void handlePlayerInteractEvent(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getEquipment().getItemInMainHand();
        if (!event.getAction().isRightClick()) return;
        event.setCancelled(true);
        switch (itemStack.getType()) {
            case HEART_POTTERY_SHERD -> {
                Gui navigationGui = Gui.gui()
                        .title(Component.text("lobby.menu.navigation.title"))
                        .rows(3)
                        .disableAllInteractions()
                        .create();
                GuiItem skyBlockItem = new GuiItem(ItemBuilder.of(Material.PLAYER_HEAD)
                        .skullUrl("http://textures.minecraft.net/texture/89f7a04ac334fcaf618da9e841f03c00d749002dc592f8540ef9534442cecf42")
                        .displayName("lobby.menu.navigation.skyblock")
                        .lore("lobby.menu.navigation.skyblock-lore")
                        .build()
                );
                skyBlockItem.setAction((inventoryClickEvent -> {
                    ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId());
                    if (cloudPlayer == null) return;
                    ICloudService skyBlockGroup = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("SkyBlockSpawn-1");
                    if (skyBlockGroup == null || (!skyBlockGroup.isServiceJoinable() && cloudPlayer.hasPermissionSync("skyblock.maintenance.bypass"))) {
                        player.sendMessage("§cThe SkyBlock group is not available at the moment.");
                        return;
                    }
                    CloudAPI.getInstance().getCloudPlayerManager().connectPlayer(cloudPlayer, skyBlockGroup);
                    cloudPlayer.sendActionBar("lobby.menu.navigation.skyblock-connecting");
                }));
                navigationGui.getFiller().fill(new GuiItem(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).displayName("§r").build()));
                navigationGui.setItem(13, skyBlockItem);
                navigationGui.open(player);
                return;
            }
            case BREWER_POTTERY_SHERD -> {
                Gui lobbySelectorGui = Gui.gui()
                        .title(Component.text("lobby.menu.lobby-selector.title"))
                        .type(GuiType.HOPPER)
                        .disableAllInteractions()
                        .create();

                List<ICloudService> lobbies = CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName("Hub");
                if (lobbies.isEmpty()) {
                    player.sendMessage("§cNo lobbies are available at the moment.");
                    return;
                }
                for (int i = 0; i < lobbies.size(); i++) {
                    ICloudService lobby = lobbies.get(i);
                    GuiItem lobbyItem = new GuiItem(ItemBuilder.of(Material.PLAYER_HEAD)
                            .skullUrl("http://textures.minecraft.net/texture/89f7a04ac334fcaf618da9e841f03c00d749002dc592f8540ef9534442cecf42")
                            .displayName("lobby.menu.lobby-selector.lobby-name")
                            .lore("lobby.menu.lobby-selector.lobby-lore")
                            .build()
                    );
                    lobbyItem.setAction((inventoryClickEvent -> {
                        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueId());
                        if (cloudPlayer == null) return;
                        CloudAPI.getInstance().getCloudPlayerManager().connectPlayer(cloudPlayer, lobby);
                        cloudPlayer.sendActionBar("lobby.menu.lobby-selector.lobby-connecting");
                    }));
                    lobbySelectorGui.setItem(i, lobbyItem);
                }
                lobbySelectorGui.open(player);
                return;
            }
            default -> {
                return;
            }
        }
    }

}
