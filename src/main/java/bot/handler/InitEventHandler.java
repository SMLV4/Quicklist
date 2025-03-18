package bot.handler;

import bot.event.InitEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InitEventHandler
{
    private static final String DEFAULT_TITLE = "Quicklist";

    public static void handle(@NotNull MessageChannel channel, @NotNull InitEvent event)
    {
        if (channel.getHistory().size() > 1) {
            event.getHook().sendMessage("Channel must be empty to turn it into a todo list.").queue();
            return;
        }

        java.util.List<MessageEmbed> embeds = new ArrayList<>();
        embeds.add(new EmbedBuilder().setTitle(DEFAULT_TITLE).build());
        event.getHook().sendMessageEmbeds(embeds).queue();
    }
}
