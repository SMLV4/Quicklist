import bot.event.AddCommand;
import bot.event.BlockEvent;
import bot.event.EditCommand;
import bot.event.InitEvent;
import bot.event.RandomItemEvent;
import bot.event.RemoveCommand;
import bot.event.UnblockEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BotRunner
{
    public static void main(String[] arguments) throws Exception
    {
        ApplicationContext context    = new ClassPathXmlApplicationContext("main.xml");
        JDABuilder         jdaBuilder = (JDABuilder) context.getBean("jda_builder");
        JDA                api        = jdaBuilder.build().awaitReady();

        api.updateCommands()
            .addCommands(
                AddCommand.buildCommand(),
                BlockEvent.buildCommand(),
                EditCommand.buildCommand(),
                InitEvent.buildCommand(),
                RemoveCommand.buildCommand(),
                RandomItemEvent.buildCommand(),
                UnblockEvent.buildCommand()
            ).queue();
    }
}
