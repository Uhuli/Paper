package org.bukkit.craftbukkit.help;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;

/**
 * Standard implementation of {@link HelpMap} for CraftBukkit servers.
 */
public class SimpleHelpMap implements HelpMap {

    private HelpTopic defaultTopic;
    private final Map<String, HelpTopic> helpTopics;
    private final Map<Class, HelpTopicFactory<Command>> topicFactoryMap;
    private final CraftServer server;

    @SuppressWarnings("unchecked")
    public SimpleHelpMap(CraftServer server) {
        this.helpTopics = new TreeMap<String, HelpTopic>(HelpTopicComparator.topicNameComparatorInstance());
        this.topicFactoryMap = new HashMap<Class, HelpTopicFactory<Command>>();
        this.server = server;

        this.defaultTopic = new IndexHelpTopic("Index", null, null,
            Collections2.filter(this.helpTopics.values(),
                Predicates.not(Predicates.instanceOf(CommandAliasHelpTopic.class))),
            "Use /help [n] to get page n of help.");
    }

    @Override
    public synchronized HelpTopic getHelpTopic(String topicName) {
        if (topicName.equals("")) {
            return this.defaultTopic;
        }

        if (this.helpTopics.containsKey(topicName)) {
            return this.helpTopics.get(topicName);
        }

        return null;
    }

    @Override
    public Collection<HelpTopic> getHelpTopics() {
        return this.helpTopics.values();
    }

    @Override
    public synchronized void addTopic(HelpTopic topic) {
        // Existing topics take priority
        if (!this.helpTopics.containsKey(topic.getName())) {
            this.helpTopics.put(topic.getName(), topic);
        }
    }

    @Override
    public synchronized void clear() {
        this.helpTopics.clear();
    }

    @Override
    public List<String> getIgnoredPlugins() {
        return List.of(); // Return empty list since we're removing help.yml functionality
    }

    @Override
    public void registerHelpTopicFactory(Class commandClass, HelpTopicFactory factory) {
        Preconditions.checkArgument(Command.class.isAssignableFrom(commandClass) ||
                CommandExecutor.class.isAssignableFrom(commandClass),
            "commandClass (%s) must implement either Command or CommandExecutor",
            commandClass.getName());
        this.topicFactoryMap.put(commandClass, factory);
    }
}
