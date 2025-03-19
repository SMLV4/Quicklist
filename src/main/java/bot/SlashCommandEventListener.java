package bot;

import bot.event.AddItemEvent;
import bot.event.AddListEvent;
import bot.event.AddNoteEvent;
import bot.event.BlockEvent;
import bot.event.EditItemEvent;
import bot.event.EditListEvent;
import bot.event.InitEvent;
import bot.event.RandomItemEvent;
import bot.event.RemoveItemEvent;
import bot.event.RemoveListEvent;
import bot.event.RemoveNoteEvent;
import bot.event.UnblockEvent;
import bot.handler.AddItemEventHandler;
import bot.handler.AddListEventHandler;
import bot.handler.AddNoteEventHandler;
import bot.handler.BlockEventHandler;
import bot.handler.EditItemEventHandler;
import bot.handler.EditListEventHandler;
import bot.handler.InitEventHandler;
import bot.handler.RandomItemEventHandler;
import bot.handler.RemoveItemEventHandler;
import bot.handler.RemoveListEventHandler;
import bot.handler.RemoveNoteEventHandler;
import bot.handler.UnblockEventHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

class SlashCommandEventListener extends ListenerAdapter
{
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        event.deferReply().queue();

        try {
            String commandName = event.getCommandPath();
            switch (commandName) {
                case AddItemEvent.COMMAND_PATH:
                    AddItemEventHandler.handle(event.getChannel(), new AddItemEvent(event));
                    break;
                case AddListEvent.COMMAND_PATH:
                    AddListEventHandler.handle(event.getChannel(), new AddListEvent(event));
                    break;
                case AddNoteEvent.COMMAND_PATH:
                    AddNoteEventHandler.handle(event.getChannel(), new AddNoteEvent(event));
                    break;
                case BlockEvent.COMMAND_PATH:
                    BlockEventHandler.handle(event.getChannel(), new BlockEvent(event));
                    break;
                case EditItemEvent.COMMAND_PATH:
                    EditItemEventHandler.handle(event.getChannel(), new EditItemEvent(event));
                    break;
                case EditListEvent.COMMAND_PATH:
                    EditListEventHandler.handle(event.getChannel(), new EditListEvent(event));
                    break;
                case InitEvent.COMMAND_PATH:
                    InitEventHandler.handle(event.getChannel(), new InitEvent(event));
                    break;
                case RemoveItemEvent.COMMAND_PATH:
                    RemoveItemEventHandler.handle(event.getChannel(), new RemoveItemEvent(event));
                    break;
                case RemoveListEvent.COMMAND_PATH:
                    RemoveListEventHandler.handle(event.getChannel(), new RemoveListEvent(event));
                    break;
                case RemoveNoteEvent.COMMAND_PATH:
                    RemoveNoteEventHandler.handle(event.getChannel(), new RemoveNoteEvent(event));
                    break;
                case RandomItemEvent.COMMAND_PATH:
                    RandomItemEventHandler.handle(event.getChannel(), new RandomItemEvent(event));
                    break;
                case UnblockEvent.COMMAND_PATH:
                    UnblockEventHandler.handle(event.getChannel(), new UnblockEvent(event));
                    break;
                default:
                    event.getHook()
                        .sendMessage(
                            "Sorry, I've forgotten how to respond to the __"
                            + commandName
                            + "__ command. Or maybe I never knew at all..."
                        ).queue();
            }
        } catch (Throwable exception) {
            event.getHook()
                .sendMessage(exception.getMessage())
                .queue();
        }
    }
}