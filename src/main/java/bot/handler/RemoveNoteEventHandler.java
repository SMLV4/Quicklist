package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.event.RemoveNoteEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class RemoveNoteEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull RemoveNoteEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId itemId = new ItemId(event.getItemId());
        Item   item   = catalog.getItem(itemId);

        item.removeNote(event.getNoteIndex());

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }
}
