package bot;

import bot.event.AddItemEvent;
import bot.event.AddListEvent;
import bot.event.AddNoteEvent;
import bot.event.BlockEvent;
import bot.event.EditItemEvent;
import bot.event.EditListEvent;
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
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

class SlashCommandEventListener extends ListenerAdapter
{
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
}