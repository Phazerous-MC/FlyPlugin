package phazerous.flyplugin;

import org.bukkit.plugin.java.JavaPlugin;
import phazerous.flyplugin.commands.FlyCommand;

public final class Main extends JavaPlugin {

    private MessageSender messageSender;

    @Override
    public void onEnable() {
        LoadConfig();
        SetCommands();
    }

    private void LoadConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    private void SetCommands() {
        messageSender = new MessageSender(this);

        getServer().getPluginCommand("fly").setExecutor(new FlyCommand(this, messageSender));
    }

}
