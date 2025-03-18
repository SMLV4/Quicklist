package bot.event;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class Event
{
    public static final String OPTION_ITEM = "item";
    public static final String OPTION_LIST = "list";

    protected void addItemOption(SlashCommandData command)
    {
        command.addOption(OptionType.STRING, OPTION_ITEM, "Item", true, true);
    }

    protected void addListOption(SlashCommandData command)
    {
        command.addOption(OptionType.STRING, OPTION_LIST, "List", true, true);
    }
}
