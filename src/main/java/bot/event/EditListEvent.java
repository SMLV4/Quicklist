package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class EditListEvent extends Event
{
    public static final String COMMAND             = "edit-list";
    public static final String OPTION_NEW_COLOR    = "new-color";
    public static final String OPTION_NEW_SHORTCUT = "new-shortcut";
    public static final String OPTION_NEW_TITLE    = "new-title";
    private final       String list;
    private final       String newColor;
    private final       String newShortcut;
    private final       String newTitle;

    public EditListEvent(@NotNull SlashCommandInteractionEvent event) throws Exception
    {
        OptionMapping listOption        = event.getOption(OPTION_LIST);
        OptionMapping newTileOption     = event.getOption(OPTION_NEW_TITLE);
        OptionMapping newShortcutOption = event.getOption(OPTION_NEW_SHORTCUT);
        OptionMapping newColorOption    = event.getOption(OPTION_NEW_COLOR);

        assert listOption != null;

        if (newTileOption == null && newShortcutOption == null && newColorOption == null) {
            throw new Exception("No new attributes given to edit list.");
        }

        list = listOption.getAsString();
        newColor = newColorOption != null ? newColorOption.getAsString() : null;
        newTitle = newTileOption != null ? newTileOption.getAsString() : null;
        newShortcut = newShortcutOption != null ? newShortcutOption.getAsString() : null;
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Edit list attributes.")
            .addOption(OptionType.STRING, OPTION_LIST, "List to edit.", true, true)
            .addOptions(
                new OptionData(OptionType.STRING, OPTION_NEW_TITLE, "New title.", false),
                new OptionData(OptionType.STRING, OPTION_NEW_COLOR, "Hex color for the border. Ex: FF0000.", false),
                new OptionData(OptionType.STRING, OPTION_NEW_SHORTCUT, "Abbreviation used to add new items.", false)
            )
            .queue();
    }

    public String getList()
    {
        return list;
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
