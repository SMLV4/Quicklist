package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class RemoveListEvent extends Event
{
    public static final String COMMAND = "remove-list";

    private final String listIdentifier;

    public RemoveListEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping listOption = event.getOption(OPTION_LIST);

        assert listOption != null;

        listIdentifier = listOption.getAsString();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove a list. Removes all items in that list.")
            .addOption(OptionType.STRING, OPTION_LIST, "List.", true, true)
            .queue();
    }

    public String getListIdentifier()
    {
        return listIdentifier;
    }
}
