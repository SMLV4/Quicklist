package bot;

import bot.entity.Catalog;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class MessageManager
{
    public static void cleanup(MessageChannel channel, Message listMessage)
    {
        java.util.List<Message> messageList = channel.getHistoryAfter(listMessage.getId(), 10)
            .complete()
            .getRetrievedHistory();
        for (Message message : messageList) {
            channel.deleteMessageById(message.getId())
                .queue();
        }
    }

    public static Message findCatalogMessage(MessageChannel channel)
    {
        return channel.getHistoryFromBeginning(1)
            .complete()
            .getRetrievedHistory()
            .get(0);
    }

    public static void updateCatalogMessage(MessageChannel channel, Message catalogMessage, Catalog catalog)
    {
        channel.editMessageEmbedsById(catalogMessage.getId(), EmbedConverter.convertCatalogToEmbeds(catalog))
            .queue();
    }
}
