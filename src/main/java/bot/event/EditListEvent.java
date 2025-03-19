package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class EditListEvent implements AcceptsListEvent, EditCommand
{
    private static final String    OPTION_COLOR    = "color";
    private static final String    OPTION_SHORTCUT = "shortcut";
    private static final String    OPTION_TITLE    = "title";
    private static final String    SUBCOMMAND      = "list";
    public static final  String    COMMAND_PATH    = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ListTitle listTitle;
    private final        String    newColor;
    private final        String    newShortcut;
    private final        String    newTitle;

    public EditListEvent(@NotNull SlashCommandInteractionEvent event) throws Exception
    {
        OptionMapping listOption        = event.getOption(OPTION_LIST);
        OptionMapping newTileOption     = event.getOption(OPTION_TITLE);
        OptionMapping newShortcutOption = event.getOption(OPTION_SHORTCUT);
        OptionMapping newColorOption    = event.getOption(OPTION_COLOR);

        assert listOption != null;

        if (newTileOption == null && newShortcutOption == null && newColorOption == null) {
            throw new Exception("No new attributes given to edit list.");
        }

        listTitle = new ListTitle(listOption.getAsString());
        newColor = newColorOption != null ? newColorOption.getAsString() : null;
        newTitle = newTileOption != null ? newTileOption.getAsString() : null;
        newShortcut = newShortcutOption != null ? newShortcutOption.getAsString() : null;
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Edit list attributes."))
            .addOption(OptionType.STRING, OPTION_LIST, "List to edit.", true, true)
            .addOption(OptionType.STRING, OPTION_TITLE, "New title.", false)
            .addOption(OptionType.STRING, OPTION_COLOR, "Hex color for the border. Ex: FF0000.", false)
            .addOption(OptionType.STRING, OPTION_SHORTCUT, "Abbreviation used to add new items.", false);
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }

    public String getNewColor()
    {
        return newColor;
    }

    public String getNewShortcut()
    {
        return newShortcut;
    }

    public String getNewTitle()
    {
        return newTitle;
    }
}
