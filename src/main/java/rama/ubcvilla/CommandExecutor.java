package rama.ubcvilla;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import rama.ubcvilla.plothook.plotMain;

import java.util.List;

import static rama.ubcvilla.UBCoreVilla.sendLog;

public class CommandExecutor {

    private UBCoreVilla plugin;

    public CommandExecutor(UBCoreVilla plugin){
        this.plugin = plugin;
    }

    public void executeCommand(Player p, String s, int i){
        String send = s;

        if(i==1) {
            //Cancel the event if the player disconnected in this world
            FileConfiguration Database = plugin.getDatabase();
            Boolean disconnected_in_parcelas = Database.getBoolean(p.getName() + ".mundo_parcelas");
            Boolean disconnected_in_villa = Database.getBoolean(p.getName() + ".spawn");
            if (s.equals("parcelas") && disconnected_in_parcelas) {
                sendLog("Cancelling command executor event because player disconnected in parcelas world", null, 2);
                return;
            }
            if (s.equals("villa") && disconnected_in_villa) {
                sendLog("Cancelling command executor event because player disconnected in spawn world", null, 2);
                return;
            }
        }

        if(send.equals("parcelas")){
            plotMain PlotAPI = new plotMain();
            if(PlotAPI.playerHasPlots(p)){
                send = "parcelaswithplot";
            }
        }
        if(!plugin.getConfig().isSet("commands."+send)) {
            sendLog("No se pudo encontrar la lista de comandos para "+send,null, 3);
            return;
        }
        List<String> commandsList = plugin.getConfig().getStringList("commands." + send);
        for(String cmd : commandsList){
            String[] commandParts = cmd.split(";");
            String commandType = commandParts[0];
            String command = commandParts[1].replaceAll("%player%", p.getName());
            if(commandType.equals("[CONSOLE]")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
                if(plugin.debugMode){
                    sendLog("Executing console command "+command,null,2);
                }
            }else if(commandType.equals("[PLAYER]")){
                Bukkit.dispatchCommand(p,command);
                if(plugin.debugMode){
                    sendLog("Executing player command "+command,null,2);
                }
            }else{
                sendLog("No se pudo identificar el tipo de comando para "+cmd,null, 3);
            }
        }

    }
}
