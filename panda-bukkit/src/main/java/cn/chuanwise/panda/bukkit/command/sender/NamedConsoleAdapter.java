package cn.chuanwise.panda.bukkit.command.sender;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;

public class NamedConsoleAdapter
    extends CommandSenderAdapter<ConsoleCommandSender>
    implements ConsoleCommandSender {
    
    final String name;

    public NamedConsoleAdapter(ConsoleCommandSender commandSender, String name) {
        super(commandSender);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isConversing() {
        return commandSender.isConversing();
    }

    @Override
    public void acceptConversationInput(String s) {
        commandSender.acceptConversationInput(s);
    }

    @Override
    public boolean beginConversation(Conversation conversation) {
        return commandSender.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(Conversation conversation) {
        commandSender.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent conversationAbandonedEvent) {
        commandSender.abandonConversation(conversation, conversationAbandonedEvent);
    }

    @Override
    public void sendRawMessage(String s) {
        messages.add(s);
        commandSender.sendRawMessage(s);
    }
}
