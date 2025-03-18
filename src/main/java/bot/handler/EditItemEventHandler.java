package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.entity.List;
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

        ItemId itemId = new ItemId(event.getId());
        Item   item   = catalog.getItem(itemId);

        String newName = event.getNewName();
        if (newName != null) {
            item.updateName(newName);
        }

        String newListIdentifier = event.getNewList();
        if (newListIdentifier != null) {
            List list = catalog.getList(newListIdentifier);
            if (list == null) {
                throw new Exception("List " + newListIdentifier + " not found.");
            }

            catalog.removeItemFromList(itemId);
            list.addItem(item);
        }

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }

}
