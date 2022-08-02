package rama.ubcvilla.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static rama.ubcvilla.UBCoreVilla.plugin;
import static rama.ubcvilla.UBCoreVilla.sendLog;

public class Parcelas implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permission = plugin.getConfig().getString("permissions.comando-parcelas");
        Player player = (Player) sender;
        if(player.hasPermission(permission)){
            rama.ubcvilla.CommandExecutor CE = new rama.ubcvilla.CommandExecutor(plugin);
            CE.executeCommand(player, "parcelas", 0);
        }else{
            sendLog("&cNo puedes hacer eso ahora! ¿Has terminado el tutorial?", (Player) sender, 4);
        }
        return false;
    }
}
