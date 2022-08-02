package rama.ubcvilla;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import rama.ubcvilla.UBCoreVilla;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class databaseConstructor implements Listener {

    private UBCoreVilla plugin;

    public databaseConstructor(UBCoreVilla plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void JoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String currentWorldName = p.getWorld().getName();
        FileConfiguration Config = plugin.getConfig();
        FileConfiguration Database = plugin.getDatabase();
        File DatabaseFile = plugin.getDatabaseFile();
        Boolean saveLogoutIsEnabled = Config.getBoolean("config.save_logout_world");

        if(saveLogoutIsEnabled){
            Boolean playerPathExists = Database.isSet(p.getName());
            if(!playerPathExists){
                Database.set(p.getName() + ".spawn", false);
                Database.set(p.getName() + ".mundo_parcelas", false);
                try{
                    Database.save(DatabaseFile);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            Database.set(p.getName()+"."+currentWorldName,true);
            try{
                Database.save(DatabaseFile);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    @EventHandler
    public void logoutSave(PlayerChangedWorldEvent e){ //Update the location of the player when changing worlds without disconnecting.
        FileConfiguration Database = plugin.getDatabase();
        File DatabaseFile = plugin.getDatabaseFile();
        FileConfiguration config = plugin.getConfig();

        Player p = e.getPlayer();
        String previous_world_name = e.getFrom().getName();
        String new_world_name = p.getWorld().getName();
        Boolean saveLogoutIsEnabled = config.getBoolean("config.save_logout_world");

        if(saveLogoutIsEnabled){
                Database.set(p.getName() + "." + previous_world_name, false);
                Database.set(p.getName() + "." + new_world_name, true);
            }
            try{
                Database.save(DatabaseFile);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

