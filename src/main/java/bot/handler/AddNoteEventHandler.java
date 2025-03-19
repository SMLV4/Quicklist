package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.event.AddNoteEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class AddNoteEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull AddNoteEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        Item item = catalog.getItem(event.getItemId());
        item.addNote(event.getNote());

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }
}
