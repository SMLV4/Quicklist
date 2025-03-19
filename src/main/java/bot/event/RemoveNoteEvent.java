package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class RemoveNoteEvent implements AcceptsItemEvent, RemoveCommand
{
    public static final  String OPTION_NOTE  = "note";
    private static final String SUBCOMMAND   = "note";
    public static final  String COMMAND_PATH = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ItemId itemId;
    private final        int    noteIndex;

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

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Remove a note from an item."))
            .addOption(OptionType.STRING, OPTION_ITEM, "Item to remove note from.", true, true)
            .addOption(OptionType.STRING, OPTION_NOTE, "Note.", true, true);
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
