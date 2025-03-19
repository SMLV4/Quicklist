package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class AddNoteEvent implements AcceptsItemEvent, AddCommand
{
    private static final String OPTION_NOTE  = "note";
    private static final String SUBCOMMAND   = "note";
    public static final  String COMMAND_PATH = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ItemId itemId;
    private final        String note;

    public AddNoteEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping itemIdOption = event.getOption(OPTION_ITEM);
        OptionMapping noteOption   = event.getOption(OPTION_NOTE);

        assert itemIdOption != null;
        assert noteOption != null;

        this.itemId = new ItemId(itemIdOption);
        this.note = noteOption.getAsString();
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Add a note to an item."))
            .addOption(OptionType.STRING, OPTION_ITEM, "Item to attach note to.", true, true)
            .addOption(OptionType.STRING, OPTION_NOTE, "Note.", true);
    }

    public ItemId getItemId()
    {
        return itemId;
    }

    public String getNote()
    {
        return note;
    }
}
