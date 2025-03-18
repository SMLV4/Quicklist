package bot;

import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.List;
import bot.entity.ListTitle;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;

public class EmbedConverter
{
    public static ArrayList<MessageEmbed> convertCatalogToEmbeds(Catalog catalog)
    {
        ArrayList<MessageEmbed> embeds = new ArrayList<>();
        for (List list : catalog.getLists()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            MessageEmbed embed = embedBuilder.setTitle(list.getTitle())
                .setColor(list.getColor())
                .setDescription(convertItemsToDescription(list))
                .build();
            embeds.add(embed);
        }

        return embeds;
    }

    public static Catalog convertMessageToCatalog(Message catalogMessage)
    {
        ArrayList<List> lists = new ArrayList<>();
        for (MessageEmbed embed : catalogMessage.getEmbeds()) {
            String title = embed.getTitle();
            assert title != null;
            ListTitle listTitle = new ListTitle(title);
            lists.add(new List(
                listTitle,
                embed.getColor(),
                convertDescriptionToItems(listTitle, embed.getDescription())
            ));
        }

        return new Catalog(lists);
    }

    private static ArrayList<Item> convertDescriptionToItems(ListTitle listTitle, String description)
    {
        ArrayList<Item> result = new ArrayList<>();
        if (description == null) {
            return result;
        }

        description = description.replace(Item.BLOCKED_BULLET, Item.UNBLOCKED_BULLET);

        ArrayList<String> itemStrings = new ArrayList<>(java.util.List.of(description.split(Item.UNBLOCKED_BULLET)));
        for (String itemString : itemStrings) {
            itemString = itemString.trim();
            if (itemString.length() > 0) {
                result.add(new Item(listTitle, itemString));
            }
        }

        return result;
    }

    private static String convertItemsToDescription(List list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : list.getItems()) {
            stringBuilder.append("\n")
                .append(item);
        }

        return stringBuilder.toString();
    }
}
