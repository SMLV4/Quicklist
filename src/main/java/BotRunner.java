import bot.event.AddItemEvent;
import bot.event.AddListEvent;
import bot.event.AddNoteEvent;
import bot.event.BlockEvent;
import bot.event.EditItemEvent;
import bot.event.EditListEvent;
import bot.event.InitEvent;
import bot.event.RemoveItemEvent;
import bot.event.RemoveListEvent;
import bot.event.RemoveNoteEvent;
import bot.event.RollEvent;
import bot.event.UnblockEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

public class BotRunner
{
    public static void main(String[] arguments) throws Exception
    {
        ApplicationContext context    = new ClassPathXmlApplicationContext("main.xml");
        JDABuilder         jdaBuilder = (JDABuilder) context.getBean("jda_builder");
        JDA api = jdaBuilder.build()
            .awaitReady();

        Properties properties = new Properties();
        FileReader reader     = new FileReader("src/main/resources/config.properties");
        properties.load(reader);

        ArrayList<Guild> guilds = new ArrayList<>();
        guilds.add(api.getGuildById(properties.getProperty("guildId1")));
        guilds.add(api.getGuildById(properties.getProperty("guildId2")));

//        clearCommands();

        for (Guild guild : guilds) {
            BlockEvent.addCommand(guild);
            AddItemEvent.addCommand(guild);
            AddListEvent.addCommand(guild);
            AddNoteEvent.addCommand(guild);
            EditItemEvent.addCommand(guild);
            EditListEvent.addCommand(guild);
            InitEvent.addCommand(guild);
            UnblockEvent.addCommand(guild);
            RemoveItemEvent.addCommand(guild);
            RemoveListEvent.addCommand(guild);
            RemoveNoteEvent.addCommand(guild);
            RollEvent.addCommand(guild);
        }
    }

    private static void clearCommands(JDA api, ArrayList<Guild> guilds)
    {
        api.updateCommands().queue();
        for (Guild guild : guilds) {
            guild.updateCommands().queue();
        }
    }
}
