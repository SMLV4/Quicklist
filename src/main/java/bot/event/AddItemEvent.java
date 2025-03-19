package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class AddItemEvent implements AcceptsListEvent, AddCommand
{
    private static final String    DEFAULT_TITLE = "Quicklist";
    private static final String    OPTION_NAME   = "name";
    private static final String    SUBCOMMAND    = "item";
    public static final  String    COMMAND_PATH  = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ListTitle listTitle;
    private final        String    name;

    public AddItemEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping nameOption = event.getOption(OPTION_NAME);
        OptionMapping listOption = event.getOption(OPTION_LIST);

        assert nameOption != null;

        this.name = nameOption.getAsString();
        this.listTitle = new ListTitle(listOption != null ? listOption.getAsString() : DEFAULT_TITLE);
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Add a new item to a list."))
            .addOption(OptionType.STRING, OPTION_NAME, "Item name.", true)
            .addOption(
                OptionType.STRING,
                OPTION_LIST,
                "List to add item to. Default: " + DEFAULT_TITLE + ".",
                false,
                true
            );
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }

    public String getName()
    {
        return name;
    }
}
