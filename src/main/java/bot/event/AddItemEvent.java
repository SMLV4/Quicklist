package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class AddItemEvent implements AcceptsListEvent
{
    public static final  String    COMMAND       = "add-item";
    private static final String    DEFAULT_TITLE = "Quicklist";
    private static final String    OPTION_NAME   = "name";
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

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Add a new item to a list.")
            .addOption(OptionType.STRING, OPTION_NAME, "Item name.", true)
            .addOption(
                OptionType.STRING,
                OPTION_LIST,
                "List to add item to. Default: " + DEFAULT_TITLE + ".",
                false,
                true
            )
            .queue();
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
