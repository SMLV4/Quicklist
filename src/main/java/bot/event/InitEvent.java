package bot.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class InitEvent
{
    public static final String          COMMAND      = "init";
    public static final String          COMMAND_PATH = COMMAND;
    private final       InteractionHook hook;

    public InitEvent(@NotNull SlashCommandInteractionEvent event)
    {
        this.hook = event.getHook();
    }

    public static CommandData buildCommand()
    {
        return Commands.slash(COMMAND, "Initialize a channel for Quicklist. Requires an empty channel.");
    }

    public InteractionHook getHook()
    {
        return hook;
    }
}
