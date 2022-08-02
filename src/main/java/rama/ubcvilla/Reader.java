package rama.ubcvilla;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Reader implements PluginMessageListener {
    private UBCoreVilla plugin;

    public Reader(UBCoreVilla plugin){
        this.plugin = plugin;
    }
    @Override
    public void onPluginMessageReceived(String channel, Player unused, byte[] bytes) {
        if(channel.equals("ub:channel")){
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if(subChannel.equals("BungeeMainCommandChannel")) {
                String player_name = in.readUTF();
                String send = in.readUTF();
                Player p = Bukkit.getPlayer(player_name);
                CommandExecutor CE = new CommandExecutor(plugin);
                CE.executeCommand(p, send, 0);
                Bukkit.getLogger().info("DEBUG 01");
            }else if(subChannel.equals("PlayerJoinEvent")){
                    String player_name = in.readUTF();
                    Player player = Bukkit.getPlayer(player_name);
                    String msg = "";
                    if(player.hasPlayedBefore()){
                        msg = plugin.getConfig().getString("messages.join");
                    }else{
                        msg = plugin.getConfig().getString("messages.new-player");
                    }
                    if(!Bukkit.getOnlinePlayers().isEmpty()){
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player_name%", player_name)));
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,100,2);
                        }
                    }
                }else{
                Bukkit.getLogger().info("DEBUG 02" + subChannel);
            }
        }else{
            Bukkit.getLogger().info("DEBUG 03" + channel);
        }
    }
}

