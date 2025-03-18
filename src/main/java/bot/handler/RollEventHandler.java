package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.event.RollEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RollEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull RollEvent event)
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(catalog.random()
            .toString());

        ArrayList<MessageEmbed> embedList = new ArrayList<>();
        embedList.add(embedBuilder.build());
        event.getHook()
            .sendMessageEmbeds(embedList)
            .queue();
    }
}
