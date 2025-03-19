package bot.event;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public interface AddCommand
{
    String MAIN_COMMAND = "add";

    static CommandData buildCommand()
    {
        return Commands.slash(AddCommand.MAIN_COMMAND, "ADD")
            .addSubcommands(
                AddItemEvent.buildSubcommand(),
                AddListEvent.buildSubcommand(),
                AddNoteEvent.buildSubcommand()
            );
    }
}
