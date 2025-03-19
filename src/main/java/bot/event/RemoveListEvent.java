package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class RemoveListEvent implements AcceptsListEvent, RemoveCommand
{
    private static final String    SUBCOMMAND   = "list";
    public static final  String    COMMAND_PATH = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ListTitle listTitle;

    public RemoveListEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping listOption = event.getOption(OPTION_LIST);

        assert listOption != null;

        listTitle = new ListTitle(listOption.getAsString());
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Remove a list. Removes all items in that list."))
            .addOption(OptionType.STRING, OPTION_LIST, "List.", true, true);
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }
}
