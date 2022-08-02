package rama.ubcvilla.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static rama.ubcvilla.UBCoreVilla.plugin;
import static rama.ubcvilla.sendClass.Send;

public class Minas implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String permission = plugin.getConfig().getString("permissions.comando-minas");
        if(player.hasPermission(permission)){
            String data = player.getName()+";minas";
            Send("MainCommandChannel", data, player);
        }
        return false;
    }
}
