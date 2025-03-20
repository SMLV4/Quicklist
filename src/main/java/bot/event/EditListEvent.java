package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EditListEvent implements AcceptsListEvent, EditCommand
{
    private static final String    OPTION_COLOR    = "color";
    private static final String    OPTION_PLACE    = "place";
    private static final String    OPTION_SHORTCUT = "shortcut";
    private static final String    OPTION_TITLE    = "title";
    private static final String    SUBCOMMAND      = "list";
    public static final  String    COMMAND_PATH    = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ListTitle listTitle;

    private final @Nullable String newColor;
    private final           int    newPlace;
    private final @Nullable String newShortcut;
    private final @Nullable String newTitle;

    public EditListEvent(@NotNull SlashCommandInteractionEvent event) throws Exception
    {
        OptionMapping listOption     = event.getOption(OPTION_LIST);
        OptionMapping titleOption    = event.getOption(OPTION_TITLE);
        OptionMapping shortcutOption = event.getOption(OPTION_SHORTCUT);
        OptionMapping colorOption    = event.getOption(OPTION_COLOR);
        OptionMapping placeOption    = event.getOption(OPTION_PLACE);

        assert listOption != null;

        if (null == titleOption && null == shortcutOption && null == colorOption && null == placeOption) {
            throw new Exception("No new attributes given to edit list.");
        }

        listTitle = new ListTitle(listOption.getAsString());
        newColor = colorOption != null ? colorOption.getAsString() : null;
        newTitle = titleOption != null ? titleOption.getAsString() : null;
        newShortcut = shortcutOption != null ? shortcutOption.getAsString() : null;
        newPlace = placeOption != null ? placeOption.getAsInt() : -1;
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Edit list attributes."))
            .addOption(OptionType.STRING, OPTION_LIST, "List to edit.", true, true)
            .addOption(OptionType.STRING, OPTION_TITLE, "New title.", false)
            .addOption(OptionType.STRING, OPTION_COLOR, "Hex color for the border. Ex: FF0000.", false)
            .addOption(OptionType.STRING, OPTION_SHORTCUT, "Abbreviation used to add new items.", false)
            .addOption(OptionType.STRING, OPTION_PLACE, "Move list to x place in the line of lists.", false);
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }

    public @Nullable String getNewColor()
    {
        return newColor;
    }

    public int getNewPlace()
    {
        return newPlace;
    }

    public @Nullable String getNewShortcut()
    {
        return newShortcut;
    }

    public @Nullable String getNewTitle()
    {
        return newTitle;
    }
}
