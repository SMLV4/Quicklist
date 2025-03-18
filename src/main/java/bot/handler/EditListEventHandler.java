package bot.handler;

import bot.EmbedConverter;
import bot.MessageManager;
import bot.entity.Catalog;
import bot.entity.List;
import bot.event.EditListEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EditListEventHandler
{
    public static void handle(@NotNull MessageChannel channel, @NotNull EditListEvent event) throws Exception
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        String newTitle    = event.getNewTitle();
        String newShortcut = event.getNewShortcut();
        String newColor    = event.getNewColor();

        String listIdentifier = event.getList();
        List   list           = catalog.getList(listIdentifier);
        if (list == null) {
            throw new Exception("List " + listIdentifier + " not found.");
        }

        if (newTitle != null) {
            list.updateTitle(newTitle);
        }

        if (newShortcut != null) {
            list.updateShortcut(newShortcut);
        }

        if (newColor != null) {
            list.updateColor(Color.decode("#" + newColor));
        }

        MessageManager.updateCatalogMessage(channel, catalogMessage, catalog);
        MessageManager.cleanup(channel, catalogMessage);
    }

}
