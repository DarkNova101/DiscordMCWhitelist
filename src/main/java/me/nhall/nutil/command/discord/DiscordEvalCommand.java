package me.nhall.nutil.command.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Collections;
import java.util.List;

public class DiscordEvalCommand extends Command {

    private final ScriptEngine engine;

    public DiscordEvalCommand() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(" +
                    "java.io," +
                    "java.lang," +
                    "java.util," +
                    "Packages.net.dv8tion.jda.api," +
                    "Packages.net.dv8tion.jda.api.entities," +
                    "Packages.net.dv8tion.jda.api.entities.impl," +
                    "Packages.net.dv8tion.jda.api.managers," +
                    "Packages.net.dv8tion.jda.api.managers.impl," +
                    "Packages.net.dv8tion.jda.api.utils);");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        if (!e.getAuthor().getId().equals("138088361100443649")) return;

        try {
            engine.put("event", e);
            engine.put("message", e.getMessage());
            engine.put("channel", e.getChannel());
            engine.put("args", args);
            engine.put("api", e.getJDA());
            if (e.isFromType(ChannelType.TEXT)) {
                engine.put("guild", e.getGuild());
                engine.put("member", e.getMember());
            }

            Object out = engine.eval(
                    "(function() {" +
                            "with (imports) {" +
                            e.getMessage().getContentDisplay().substring(args[0].length()) +
                            "}" +
                            "})();");
            sendMessage(e, out == null ? "Executed without error." : out.toString());
        } catch (Exception e1) {
            sendMessage(e, e1.getMessage());
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList(".eval");
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return "Eval";
    }

    @Override
    public List<String> getUsageInstructions() {
        return null;
    }
}
