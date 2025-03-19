package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class RemoveListEvent implements AcceptsListEvent
{
    public static final String COMMAND = "remove-list";

    private final ListTitle listTitle;

    public RemoveListEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping listOption = event.getOption(OPTION_LIST);

        assert listOption != null;

        listTitle = new ListTitle(listOption.getAsString());
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove a list. Removes all items in that list.")
            .addOption(OptionType.STRING, OPTION_LIST, "List.", true, true)
            .queue();
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }
}
