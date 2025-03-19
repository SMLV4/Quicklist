package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class RemoveNoteEvent implements AcceptsItemEvent
{
    public static final String COMMAND     = "remove-note";
    public static final String OPTION_NOTE = "note";
    private final       ItemId itemId;
    private final       int    noteIndex;

    public RemoveNoteEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping itemIdOption = event.getOption(OPTION_ITEM);
        OptionMapping noteIdOption = event.getOption(OPTION_NOTE);

        assert itemIdOption != null;
        assert noteIdOption != null;

        this.itemId = new ItemId(itemIdOption);
        this.noteIndex = noteIdOption.getAsInt();

        assert noteIndex > 0;
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove a note from an item.")
            .addOption(OptionType.STRING, OPTION_ITEM, "Item to remove note from.", true, true)
            .addOption(OptionType.STRING, OPTION_NOTE, "Note.", true, true)
            .queue();
    }

    public ItemId getItemId()
    {
        return itemId;
    }

    public int getNoteIndex()
    {
        return noteIndex;
    }
}
