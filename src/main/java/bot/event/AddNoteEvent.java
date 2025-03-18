package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class AddNoteEvent extends Event
{
    public static final  String COMMAND     = "add-note";
    private static final String OPTION_NOTE = "note";
    private final        int    itemId;
    private final        String note;

    public AddNoteEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping itemIdOption = event.getOption(OPTION_ITEM);
        OptionMapping noteOption   = event.getOption(OPTION_NOTE);

        assert itemIdOption != null;
        assert noteOption != null;

        this.itemId = itemIdOption.getAsInt();
        this.note = noteOption.getAsString();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Add a note to an item.")
            .addOption(OptionType.STRING, OPTION_ITEM, "Item to attach note to.", true, true)
            .addOption(OptionType.STRING, OPTION_NOTE, "Note.", true)
            .queue();
    }

    public int getItemId()
    {
        return itemId;
    }

    public String getNote()
    {
        return note;
    }
}
