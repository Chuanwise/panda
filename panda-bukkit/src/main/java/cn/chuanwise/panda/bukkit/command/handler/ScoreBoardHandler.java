package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.common.space.Container;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Set;

@Data
public class ScoreBoardHandler
    extends AbstractClassHandler<Scoreboard> {

    private static final ScoreBoardHandler INSTANCE = new ScoreBoardHandler();

    public static ScoreBoardHandler getInstance() {
        return INSTANCE;
    }

    protected ScoreBoardHandler() {}

    @Override
    protected Set<String> complete0(CompleteContext context) throws Exception {
        return PlayerHandler.getInstance().complete0(context);
    }

    @Override
    protected Container<Scoreboard> parse0(ParseContext context) throws Exception {
        return PlayerHandler.getInstance().parse0(context)
                .map(Player::getScoreboard);
    }
}
