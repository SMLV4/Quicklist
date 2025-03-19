package bot.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class AddListEvent implements AddCommand
{
    public static final  String DEFAULT_COLOR   = "000000";
    public static final  String OPTION_COLOR    = "color";
    public static final  String OPTION_SHORTCUT = "shortcut";
    public static final  String OPTION_TITLE    = "title";
    private static final String SUBCOMMAND      = "list";
    public static final  String COMMAND_PATH    = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        String color;
    private final        String shortcut;
    private final        String title;

    public AddListEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping titleOption = event.getOption(OPTION_TITLE);
        assert titleOption != null;

        String        color       = DEFAULT_COLOR;
        OptionMapping colorOption = event.getOption(OPTION_COLOR);
        if (colorOption != null) {
            color = colorOption.getAsString();
        }

        String        shortcut       = null;
        OptionMapping shortcutOption = event.getOption(OPTION_SHORTCUT);
        if (shortcutOption != null) {
            shortcut = shortcutOption.getAsString();
        }

        this.title = titleOption.getAsString();
        this.color = color;
        this.shortcut = shortcut;
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Add a new list."))
            .addOption(OptionType.STRING, OPTION_TITLE, "List title.", true)
            .addOption(
                OptionType.STRING,
                OPTION_COLOR,
                "Hex color for the border. Default: " + DEFAULT_COLOR + ".",
                false
            )
            .addOption(OptionType.STRING, OPTION_SHORTCUT, "Shortcut used to reference list.", false);
    }

    public String getColor()
    {
        return color;
    }

    public String getShortcut()
    {
        return shortcut;
    }

    public String getTitle()
    {
        return title;
    }
}
