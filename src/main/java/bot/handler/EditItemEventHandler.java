package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.entity.List;
import bot.entity.ListTitle;
import bot.event.EditItemEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class EditItemEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull EditItemEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId itemId = event.getItemId();
        Item   item   = catalog.getItem(event.getItemId());

        String newName = event.getNewName();
        if (newName != null) {
            item.updateName(newName);
        }

        ListTitle newListTitle = event.getNewListTitle();
        if (newListTitle != null) {
            List list = catalog.getNotNullList(newListTitle);
            catalog.removeItemFromList(itemId);
            list.addItem(item);
        }

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }

}
