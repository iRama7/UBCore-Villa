package rama.ubcvilla;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import rama.ubcvilla.authhook.authMain;
import rama.ubcvilla.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public final class UBCoreVilla extends JavaPlugin {

    public String rutaConfig;
    private FileConfiguration Database = null;
    private File DatabaseFile = null;
    public static UBCoreVilla plugin;
    public Boolean debugMode = this.getConfig().getBoolean("debug-mode");


    @Override
    public void onEnable() {
        plugin = this;
        registerConfig();
        createDatabase();
        registerEvents();
        registerCommands();
        registerChannels();
        checkHooks();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void sendLog(String m, Player p, int v) {
        String prefix = ChatColor.translateAlternateColorCodes('&', "&6&lUB&cCore ");
        String msg;
        if (p == null) {
            msg = ChatColor.translateAlternateColorCodes('&', m);
        } else {
            msg = ChatColor.translateAlternateColorCodes('&', m).replaceAll("%player%", p.getName());
        }
        switch (v) {
            case 1: //Console message
                Bukkit.getConsoleSender().sendMessage(prefix + msg);
                break;
            case 2: //Debug console message
                prefix = ChatColor.translateAlternateColorCodes('&', "&6&lUB&cCore &e[DEBUG] ");
                Bukkit.getConsoleSender().sendMessage(prefix + msg);
                break;
            case 3: //Error console message
                prefix = ChatColor.translateAlternateColorCodes('&', "&6&lUB&cCore &4[ERROR] ");
                Bukkit.getLogger().severe(prefix + msg);
                break;
            case 4: //Player message
                p.sendMessage(msg);
                break;
            default:
                Bukkit.getConsoleSender().sendMessage(prefix + "Invalid message value");
                break;
        }

    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new authMain(), this);
        pm.registerEvents(new databaseConstructor(this), this);
        sendLog("&eRegistering events...", null, 1);
    }

    public void registerChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "ub:channel", new Reader(this));
        sendLog("&eRegistering incoming and outgoing channels...", null, 1);
    }

    public void registerCommands() {
        this.getCommand("minas").setExecutor(new Minas());
        this.getCommand("nether").setExecutor(new Nether());
        this.getCommand("dragon").setExecutor(new Dragon());
        this.getCommand("parcelas").setExecutor(new Parcelas());
        this.getCommand("villa").setExecutor(new Villa(this));
        this.getCommand("villa").setAliases(Collections.singletonList("spawn"));
        sendLog("&eRegistering commands...", null, 1);
    }

    public void registerConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        rutaConfig = config.getPath();
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        sendLog("&eLoading configuration YAML...", null, 1);
    }

    public FileConfiguration getDatabase() {
        return this.Database;
    }

    public File getDatabaseFile() {
        return this.DatabaseFile;
    }

    private void createDatabase() {
        DatabaseFile = new File(getDataFolder(), "database.yml");
        if (!DatabaseFile.exists()) {
            DatabaseFile.getParentFile().mkdirs();
            saveResource("database.yml", false);
        }
        Database = new YamlConfiguration();
        try {
            Database.load(DatabaseFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        sendLog("&eLoading database YAML...", null, 1);
    }

    public void checkHooks(){
        if(Bukkit.getPluginManager().getPlugin("PlotSquared") == null){
            sendLog("No se encontró PlotSquared! Algunas funciones del plugin no funcionarán.", null, 3);
        }else{
            sendLog("&aEnabling PlotSquared hook.",null, 1);
        }
        if(Bukkit.getPluginManager().getPlugin("AuthMe") == null){
            sendLog("No se encontró AuthMe! Algunas funciones del plugin no funcionarán.",null, 3);
        }else{
            sendLog("&aEnabling AuthMe hook.",null, 1);
        }
    }
}
