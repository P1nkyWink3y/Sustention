package dev.pinkuth.maintenance.command;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;

import dev.pinkuth.maintenance.Maintenance;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;

import java.util.*;

public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", CommandSettings.builder()
                .setUsageMessage("/maintenance <on|off> [server]")
                .setDescription("Enable or disable maintenance mode")
                .setAliases("mtn")
                .setPermission("maintenance.change").build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (args.length < 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) return false;
        Boolean mode = (args[0].equalsIgnoreCase("on"));
        if (args.length > 1) {
            String server = args[1];
            Collection<String> servers = new ArrayList<>();
            ProxyServer.getInstance().getServers().forEach((ServerInfo n) -> servers.add(n.getServerName()));
            if (!servers.contains(server)) {
                sender.sendMessage("§cThat server wasn't found");
                return true;
            }
            Maintenance.getInstance().getMaintenanceManager().setServerMaintenance(server, mode);
            sender.sendMessage("§eMaintenance mode has been " + (mode ? "§aenabled" : "§cdisabled") + "§e for §b" + server);
        } else {
            Maintenance.getInstance().getMaintenanceManager().setGlobalMaintenance(mode);
            sender.sendMessage("§eMaintenance mode has been " + (mode ? "§aenabled" : "§cdisabled") + "§e globally");
        }
        return true;
    }
}