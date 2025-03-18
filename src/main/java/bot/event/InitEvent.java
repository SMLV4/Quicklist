package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;

public class InitEvent
{
    public static final String          COMMAND = "init";
    private final       InteractionHook hook;

    public InitEvent(@NotNull SlashCommandInteractionEvent event)
    {
        this.hook = event.getHook();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Initialize a channel for Quicklist. Requires an empty channel.")
            .queue();
    }

    public InteractionHook getHook()
    {
        return hook;
    }
}
