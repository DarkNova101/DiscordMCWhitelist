package me.nhall.whitelist.listener;

import me.nhall.whitelist.Whitelist;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeathListener implements Listener {

    private final Set<EntityDamageEvent.DamageCause> entityDamage = Set.of(EntityDamageEvent.DamageCause.CONTACT, EntityDamageEvent.DamageCause.ENTITY_ATTACK, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Logger logger = Whitelist.getPlugin().getLogger();
        Player player = event.getPlayer();
        Player killer = event.getPlayer().getKiller();

        TextChannel channel = Whitelist.getPlugin().getJda().getTextChannelById(Whitelist.getPlugin().getConfig().getString("death-channel"));

        Component message = event.deathMessage();

        String deathMessage = PlainTextComponentSerializer.plainText().serialize(message);
        deathMessage = (String) Whitelist.getPlugin().getDeathMap().get(deathMessage);

        if (killer == null) {
            TranslatableComponent translatableComponent = (TranslatableComponent) event.deathMessage();
            List<Component> componentArgs = translatableComponent.args();
            TranslatableComponent componentKiller;
            if (componentArgs.size() == 1) {
                componentKiller = null;
            } else {
                componentKiller = (TranslatableComponent) translatableComponent.args().get(1);
            }

            if (componentKiller == null) {
                deathMessage = deathMessage
                        .replace("%1$s", player.getName());
            } else {
                String entityKiller = PlainTextComponentSerializer.plainText().serialize(componentKiller);
                entityKiller = (String) Whitelist.getPlugin().getEntityMap().get(entityKiller);

                deathMessage = deathMessage
                        .replace("%1$s", player.getName())
                        .replace("%2$s", entityKiller);
            }
        } else {
            deathMessage = deathMessage
                    .replace("%1$s", player.getName())
                    .replace("%2$s", killer.getName());
        }

        deathMessage = deathMessage + " (#" + player.getStatistic(Statistic.DEATHS) + ")";
        channel.sendMessage(deathMessage).queue();

    }
}
