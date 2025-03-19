package bot.event;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public interface RemoveCommand
{
    String MAIN_COMMAND = "remove";

    static CommandData buildCommand()
    {
        return Commands.slash(RemoveCommand.MAIN_COMMAND, "REMOVE")
            .addSubcommands(
                RemoveItemEvent.buildSubcommand(),
                RemoveListEvent.buildSubcommand(),
                RemoveNoteEvent.buildSubcommand()
            );
    }
}
