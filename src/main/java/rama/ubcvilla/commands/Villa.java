package rama.ubcvilla.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rama.ubcvilla.UBCoreVilla;

public class Villa implements CommandExecutor {

    private UBCoreVilla plugin;
    public Villa(UBCoreVilla plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        rama.ubcvilla.CommandExecutor CE = new rama.ubcvilla.CommandExecutor(plugin);
        CE.executeCommand(p,"villa", 0);
        return false;
    }
}
