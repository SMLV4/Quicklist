package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.event.UnblockEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class UnblockEventHandler
{
    public static void handle(MessageChannel channel, @NotNull UnblockEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId blockedItemId  = event.getBlockedItemId();
        ItemId blockingItemId = event.getBlockingItemId();
        Item   item           = catalog.getItem(blockedItemId);

        item.removeBlock(blockingItemId);

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);

    }
}
