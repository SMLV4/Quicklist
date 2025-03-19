package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.event.BlockEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class BlockEventHandler
{
    public static void handle(MessageChannel channel, @NotNull BlockEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId blockedItemId  = event.getBlockedItemId();
        ItemId blockingItemId = event.getBlockingItemId();

        if (blockedItemId.equals(blockingItemId)) {
            throw new Exception("An item cannot be blocked by itself.");
        }

        if (!catalog.hasItem(blockingItemId)) {
            throw new Exception("Item " + blockingItemId + " not found.");
        }

        Item item = catalog.getItem(blockedItemId);
        item.addBlock(blockingItemId);

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }
}
