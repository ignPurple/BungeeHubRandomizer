package net.ignpurple.bungee.loadbalancer.strategy;

import net.ignpurple.api.loadbalancer.strategy.AbstractServerPickingStrategy;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComparatorPickingStrategy extends AbstractServerPickingStrategy {
    private final Comparator<ServerInfo> serverInfoComparator;

    public ComparatorPickingStrategy(Comparator<ServerInfo> serverInfoComparator) {
        this.serverInfoComparator = serverInfoComparator;
    }

    @Override
    public CompletableFuture<ServerInfo> getTargetServer(ProxiedPlayer player) {
        return this.createFuture(() -> {
            final List<ServerInfo> availableServers = this.getAvailableServers(player);
            return availableServers.stream()
                .min(this.serverInfoComparator)
                .orElse(null);
        });
    }
}
