package bot.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class BlockEvent extends BlockingEvent
{
    public static final String COMMAND      = "block";
    public static final String COMMAND_PATH = COMMAND;

    public BlockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        super(event);
    }

    public static CommandData buildCommand()
    {
        return (Commands.slash(COMMAND, "Mark first item as blocked by the second item."))
            .addOptions(buildBlockingOptions());
    }
}
