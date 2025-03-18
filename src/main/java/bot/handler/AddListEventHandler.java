package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.List;
import bot.entity.ListTitle;
import bot.event.AddListEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AddListEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull AddListEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ListTitle listTitle    = new ListTitle(event.getTitle(), event.getShortcut());
        List      existingList = catalog.getList(listTitle);
        if (existingList != null) {
            if (existingList.getListTitle().isTitle(listTitle.getTitle())) {
                throw new Exception("List " + listTitle.getTitle() + " already exists.");
            } else if (existingList.getListTitle().isShortcut(listTitle.getShortcut())) {
                throw new Exception("List with shortcut " + listTitle.getShortcut() + " already exists.");
            } else {
                throw new Exception("Similar list " + existingList.getTitle() + " already exists.");
            }
        }

        catalog.addList(new List(listTitle, Color.decode("#" + event.getColor())));

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }
}
