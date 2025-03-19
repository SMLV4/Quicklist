package bot;

import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.entity.List;
import bot.entity.ListTitle;
import bot.event.AcceptsItemEvent;
import bot.event.AcceptsListEvent;
import bot.event.BlockingEvent;
import bot.event.RemoveNoteEvent;
import bot.event.UnblockEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;

class AutoCompleteEventListener extends ListenerAdapter
{
    @Override
    public void onCommandAutoCompleteInteraction(@Nonnull CommandAutoCompleteInteractionEvent event)
    {
        MessageChannel channel = event.getMessageChannel();
        String         input   = event.getFocusedOption().getValue();

        ArrayList<Command.Choice> choices = new ArrayList<>();
        switch (event.getFocusedOption().getName()) {
            case BlockingEvent.OPTION_BLOCKED:
                choices = event.getName().equals(UnblockEvent.COMMAND)
                    ? collectBlockedItemChoices(channel, input)
                    : collectItemChoices(channel, input);
                break;
            case BlockingEvent.OPTION_BLOCKING:
                try {
                    choices = event.getName().equals(UnblockEvent.COMMAND)
                        ? collectBlockingItemChoices(channel, event)
                        : collectItemChoices(channel, input);
                } catch (Exception e) {
                    //skip
                }
                break;
            case AcceptsItemEvent.OPTION_ITEM:
                choices = event.getName().equals(RemoveNoteEvent.COMMAND)
                    ? collectItemChoicesWithNotes(channel, input)
                    : collectItemChoices(channel, input);
                break;
            case AcceptsListEvent.OPTION_LIST:
                choices = collectListChoices(channel, input);
                break;
            case RemoveNoteEvent.OPTION_NOTE:
                try {
                    choices = collectNoteChoices(channel, event);
                } catch (Exception e) {
                    // skip
                }
                break;
        }

        if (!choices.isEmpty() && choices.size() <= OptionData.MAX_CHOICES) {
            event.replyChoices(choices).queue();
        }
    }

    private ArrayList<Command.Choice> collectBlockedItemChoices(MessageChannel channel, String input)
    {
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ArrayList<Command.Choice> choices = new ArrayList<>();
        for (List list : catalog.getLists()) {
            for (Item item : list.getItems()) {
                String itemChoiceName = buildItemChoiceName(item);
                if (item.isBlocked() && (input.isEmpty() || itemChoiceName.toLowerCase().contains(input))) {
                    choices.add(new Command.Choice(itemChoiceName, item.getId().getValue()));
                }
            }
        }

        return choices;
    }

    private ArrayList<Command.Choice> collectBlockingItemChoices(
        MessageChannel channel,
        CommandAutoCompleteInteractionEvent event
    ) throws Exception
    {
        ArrayList<Command.Choice> choices = new ArrayList<>();

        OptionMapping blockedItemOption = event.getOption(BlockingEvent.OPTION_BLOCKED);
        if (null == blockedItemOption) {
            return choices;
        }

        String input = event.getFocusedOption().getValue();
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId blockedItemId = new ItemId(blockedItemOption);
        Item   blockedItem   = catalog.getItem(blockedItemId);

        for (ItemId blockingItemId : blockedItem.getBlockingIds()) {
            Item   blockingItem = catalog.getItem(blockingItemId);
            String codeName     = buildItemChoiceName(blockingItem);
            if (input.isEmpty() || codeName.toLowerCase().contains(input)) {
                choices.add(new Command.Choice(codeName, blockingItem.getId().getValue()));
            }
        }

        return choices;
    }

    private ArrayList<Command.Choice> collectItemChoices(MessageChannel channel, String input)
    {
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ArrayList<Command.Choice> choices = new ArrayList<>();
        for (List list : catalog.getLists()) {
            for (Item item : list.getItems()) {
                String codeName = buildItemChoiceName(item);
                if (input.isEmpty() || codeName.toLowerCase().contains(input)) {
                    choices.add(new Command.Choice(codeName, item.getId().getValue()));
                }
            }
        }

        return choices;
    }

    private ArrayList<Command.Choice> collectItemChoicesWithNotes(MessageChannel channel, String input)
    {
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ArrayList<Command.Choice> choices = new ArrayList<>();
        for (List list : catalog.getLists()) {
            for (Item item : list.getItems()) {
                String codeName = buildItemChoiceName(item);
                if (item.hasNotes() && (input.isEmpty() || codeName.toLowerCase().contains(input))) {
                    choices.add(new Command.Choice(codeName, item.getId().getValue()));
                }
            }
        }

        return choices;
    }

    private ArrayList<Command.Choice> collectListChoices(MessageChannel channel, String input)
    {
        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ArrayList<Command.Choice> choices = new ArrayList<>();
        for (List list : catalog.getLists()) {
            String listTitle = list.getListTitle().toString();
            if (input.isEmpty() || listTitle.toLowerCase().contains(input)) {
                choices.add(new Command.Choice(listTitle, listTitle));
            }
        }

        return choices;
    }

    private ArrayList<Command.Choice> collectNoteChoices(
        MessageChannel channel,
        CommandAutoCompleteInteractionEvent event
    ) throws Exception
    {
        ArrayList<Command.Choice> choices = new ArrayList<>();

        OptionMapping itemOption = event.getOption(AcceptsItemEvent.OPTION_ITEM);
        if (null == itemOption) {
            return choices;
        }

        String input = event.getFocusedOption().getValue();
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId itemId = new ItemId(itemOption);
        Item   item   = catalog.getItem(itemId);

        for (String note : item.getNotes()) {
            if (note.toLowerCase().contains(input)) {
                choices.add(new Command.Choice(note, item.getNotes().indexOf(note)));
            }
        }

        return choices;
    }

    @NotNull
    private static String buildItemChoiceName(Item item)
    {
        ListTitle listTitle  = item.getListTitle();
        String    listString = Objects.requireNonNullElse(listTitle.getShortcut(), listTitle.getTitle());

        return listString + ": " + item.getName();
    }
}