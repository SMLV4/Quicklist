package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.event.RandomItemEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class RandomItemEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull RandomItemEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        event.getHook()
            .sendMessage(catalog.random(event.getListTitle()).toString())
            .queue();
    }
}
