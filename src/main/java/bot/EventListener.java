package bot;

import bot.entity.Catalog;
import bot.entity.Item;
import bot.entity.ItemId;
import bot.entity.List;
import bot.entity.ListTitle;
import bot.event.AddItemEvent;
import bot.event.AddListEvent;
import bot.event.AddNoteEvent;
import bot.event.BlockEvent;
import bot.event.BlockingEvent;
import bot.event.EditItemEvent;
import bot.event.EditListEvent;
import bot.event.Event;
import bot.event.InitEvent;
import bot.event.RemoveItemEvent;
import bot.event.RemoveListEvent;
import bot.event.RemoveNoteEvent;
import bot.event.RollEvent;
import bot.event.UnblockEvent;
import bot.handler.AddItemEventHandler;
import bot.handler.AddListEventHandler;
import bot.handler.AddNoteEventHandler;
import bot.handler.BlockEventHandler;
import bot.handler.EditItemEventHandler;
import bot.handler.EditListEventHandler;
import bot.handler.InitEventHandler;
import bot.handler.RemoveItemEventHandler;
import bot.handler.RemoveListEventHandler;
import bot.handler.RemoveNoteEventHandler;
import bot.handler.RollEventHandler;
import bot.handler.UnblockEventHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;

class EventListener extends ListenerAdapter
{
    @Override
    public void onCommandAutoCompleteInteraction(@Nonnull CommandAutoCompleteInteractionEvent event)
    {
        MessageChannel channel = event.getMessageChannel();
        String         input   = event.getFocusedOption().getValue();

        ArrayList<Command.Choice> choices = new ArrayList<>();
        switch (event.getFocusedOption().getName()) {
            // todo: unblock lists only blocked items
            // todo: unblock second argument lists only items blocking first argument
            case BlockingEvent.OPTION_BLOCKED:
            case BlockingEvent.OPTION_BLOCKING:
            case Event.OPTION_ITEM:
                choices = collectItemChoices(channel, input);
                break;
            case Event.OPTION_LIST:
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

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        event.deferReply()
            .queue();

        try {
            String commandName = event.getName();
            switch (commandName) {
                case AddItemEvent.COMMAND:
                    AddItemEventHandler.handle(event.getChannel(), new AddItemEvent(event));
                    break;
                case AddListEvent.COMMAND:
                    AddListEventHandler.handle(event.getChannel(), new AddListEvent(event));
                    break;
                case AddNoteEvent.COMMAND:
                    AddNoteEventHandler.handle(event.getChannel(), new AddNoteEvent(event));
                    break;
                case BlockEvent.COMMAND:
                    BlockEventHandler.handle(event.getChannel(), new BlockEvent(event));
                    break;
                case EditItemEvent.COMMAND:
                    EditItemEventHandler.handle(event.getChannel(), new EditItemEvent(event));
                    break;
                case EditListEvent.COMMAND:
                    EditListEventHandler.handle(event.getChannel(), new EditListEvent(event));
                    break;
                case InitEvent.COMMAND:
                    InitEventHandler.handle(event.getChannel(), new InitEvent(event));
                    break;
                case RemoveItemEvent.COMMAND:
                    RemoveItemEventHandler.handle(event.getChannel(), new RemoveItemEvent(event));
                    break;
                case RemoveListEvent.COMMAND:
                    RemoveListEventHandler.handle(event.getChannel(), new RemoveListEvent(event));
                    break;
                case RemoveNoteEvent.COMMAND:
                    RemoveNoteEventHandler.handle(event.getChannel(), new RemoveNoteEvent(event));
                    break;
                case RollEvent.COMMAND:
                    RollEventHandler.handle(event.getChannel(), new RollEvent(event));
                    break;
                case UnblockEvent.COMMAND:
                    UnblockEventHandler.handle(event.getChannel(), new UnblockEvent(event));
                    break;
            }
        } catch (Throwable exception) {
            event.getChannel()
                .sendMessage(exception.getMessage())
                .queue();
        }
    }

    private ArrayList<Command.Choice> collectItemChoices(MessageChannel channel, String input)
    {
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ArrayList<Command.Choice> choices = new ArrayList<>();
        for (List list : catalog.getLists()) {
            for (Item item : list.getItems()) {
                ListTitle listTitle  = item.getListTitle();
                String    listString = Objects.requireNonNullElse(listTitle.getShortcut(), listTitle.getTitle());
                String    codeName   = listString + ": " + item.getName();
                if (codeName.toLowerCase().contains(input)) {
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
            String title = list.getTitle();
            if (input.isEmpty() || title.startsWith(input)) {
                choices.add(new Command.Choice(title, title));
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

        OptionMapping itemOption = event.getOption(Event.OPTION_ITEM);
        if (null == itemOption) {
            return choices;
        }

        String input = event.getFocusedOption().getValue();
        input = input.toLowerCase().trim();

        Message catalogMessage = MessageManager.findCatalogMessage(channel);
        Catalog catalog        = EmbedConverter.convertMessageToCatalog(catalogMessage);

        ItemId itemId = new ItemId(itemOption.getAsInt());
        Item   item   = catalog.getItem(itemId);

        for (String note : item.getNotes()) {
            if (note.toLowerCase().contains(input)) {
                choices.add(new Command.Choice(note, item.getNotes().indexOf(note)));
            }
        }

        return choices;
    }
}