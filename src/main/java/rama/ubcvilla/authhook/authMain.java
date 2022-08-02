package rama.ubcvilla.authhook;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

import static rama.ubcvilla.sendClass.Send;

public class authMain implements Listener {

    @EventHandler
    public void getLoginEvent(LoginEvent e){
        Player p = e.getPlayer();
        UUID playerUUID = p.getUniqueId();
        String data = playerUUID+";"+p.getName();
        Send("LoginEventChannel", data, p);
    }
}
