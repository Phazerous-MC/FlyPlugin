package phazerous.flyplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageSender {

    private final Main plugin;

    public MessageSender(Main plugin) {
        this.plugin = plugin;
    }

    public void SendMessage(Player player, String message) {
        String prefix = plugin.getConfig().getString("plugin_prefix") + " ";
        String out = prefix + message;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', out));
    }

    public void SendErrorMessage(Player player, String message) {
        String out = "";

        String prefix = plugin.getConfig().getString("plugin_error_color");
        out += prefix + message;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', out));
    }
}
