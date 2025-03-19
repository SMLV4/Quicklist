package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.entity.List;
import bot.entity.ListTitle;
import bot.event.AddItemEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddItemEventHandler
{
    public static void handle(MessageChannel channel, @NotNull AddItemEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        List list = catalog.getNotNullList(event.getListTitle());

        ListTitle listTitle = list.getListTitle();
        list.addItem(createNewItem(event, catalog, listTitle));

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }

    private static Item createNewItem(@NotNull AddItemEvent event, Catalog catalog, ListTitle listTitle)
    {
        return new Item(
            event.getName(),
            new ItemId(catalog.highestId() + 1),
            new ArrayList<>(),
            new ArrayList<>(),
            listTitle
        );
    }
}
