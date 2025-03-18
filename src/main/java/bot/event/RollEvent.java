package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;

public class RollEvent extends Event
{
    public static final String          COMMAND = "roll";
    private final       InteractionHook hook;

    public RollEvent(@NotNull SlashCommandInteractionEvent event)
    {
        this.hook = event.getHook();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Roll a random item.")
            .queue();
    }

    public InteractionHook getHook()
    {
        return hook;
    }
}
