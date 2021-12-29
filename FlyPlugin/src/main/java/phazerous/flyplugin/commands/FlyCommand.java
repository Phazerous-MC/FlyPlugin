package phazerous.flyplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phazerous.flyplugin.Main;
import phazerous.flyplugin.MessageSender;
import phazerous.flyplugin.Operations;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private final Main plugin;
    private final MessageSender messageSender;

    private ArrayList<Player> listOfFlyingPlayers;

    public FlyCommand(Main plugin, MessageSender sender) {
        this.plugin = plugin;
        this.messageSender = sender;

        listOfFlyingPlayers = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        switch (args.length) {
            case 0:
                FlyToggle(player);
                return true;

            case 1:
                if (!FlyToggleForOthers(player, args[0])) {
                    messageSender.SendErrorMessage(player, "Couldn't find " + "'" + args[0] + "'.");
                }

                return true;
        }

        if (!args[0].equals("set")) {
            messageSender.SendErrorMessage(player, "/fly <player:set>");
            return true;
        }

        if (!args[1].equals("speed") || args.length == 2) {
            messageSender.SendErrorMessage(player, "/fly set speed <amplifier>");
            return true;
        }

        int amplifier;

        if ((amplifier=Operations.IntTryParse(args[2])) == -1) {
            messageSender.SendErrorMessage(player, "Amplifier has to be an integer.");
            messageSender.SendErrorMessage(player, "/fly set speed <amplifier>");
            return true;
        }

        if (!(0 <= amplifier && amplifier <= 10)) {
            messageSender.SendErrorMessage(player, "Amplifier has to be between 0 and 10.");
            return true;
        }

        String amplifierStr = Integer.toString(amplifier);

        FlySetSpeed(player, (float)(Float.parseFloat(Integer.toString(amplifier)) * 0.1));

        return true;
    }

    private void FlyToggle(Player player) {
        if (listOfFlyingPlayers.contains(player)) {
            player.setAllowFlight(false);
            listOfFlyingPlayers.remove(player);
            messageSender.SendMessage(player, plugin.getConfig().getString("fly_off_message"));
        } else {
            player.setAllowFlight(true);
            listOfFlyingPlayers.add(player);
            messageSender.SendMessage(player, plugin.getConfig().getString("fly_on_message"));
        }
    }

    private boolean FlyToggleForOthers(Player player, String targetName) {
        Player target = Bukkit.getPlayerExact(targetName);

        if (target != null) {
            FlyToggle(target);
            messageSender.SendMessage(player, plugin.getConfig().getString("fly_for_on_message") + targetName + ".");
            return true;
        }

        return false;
    }

    private void FlySetSpeed(Player player, float amplifier) {
        player.setFlySpeed(amplifier);

        amplifier *= 10;
        DecimalFormat df = new DecimalFormat("0.#");

        messageSender.SendMessage(player, plugin.getConfig().getString("speed_set_message") + df.format(amplifier) + ".");
    }

}
