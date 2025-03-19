package bot.event;

import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class RandomItemEvent implements AcceptsListEvent
{
    public static final String          COMMAND      = "random";
    public static final String          COMMAND_PATH = COMMAND;
    private final       InteractionHook hook;
    private final       ListTitle       listTitle;

    public RandomItemEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping listOption = event.getOption(OPTION_LIST);

        listTitle = listOption != null ? new ListTitle(listOption.getAsString()) : null;

        this.hook = event.getHook();
    }

    public static CommandData buildCommand()
    {
        return (Commands.slash(COMMAND, "Select a random item."))
            .addOption(OptionType.STRING, OPTION_LIST, "List to select item from.", false, true);
    }

    public InteractionHook getHook()
    {
        return hook;
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }
}
