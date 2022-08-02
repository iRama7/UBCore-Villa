package rama.ubcvilla.plothook;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.entity.Player;

public class plotMain {

    public Boolean playerHasPlots(Player player) {
        PlotAPI plotAPI = new PlotAPI();
        PlotPlayer plotPlayer = plotAPI.wrapPlayer(player.getUniqueId());
        int PlayerPlotCount = plotPlayer.getPlotCount();
        if(PlayerPlotCount > 0){
            return true;
        }else{
            return false;
        }
    }
}
