package bot.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class UnblockEvent extends BlockingEvent
{
    public static final String COMMAND      = "unblock";
    public static final String COMMAND_PATH = COMMAND;

    public UnblockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        super(event);
    }

    public static CommandData buildCommand()
    {
        return (Commands.slash(COMMAND, "Remove a blocking item from another item."))
            .addOptions(buildBlockingOptions());
    }
}
