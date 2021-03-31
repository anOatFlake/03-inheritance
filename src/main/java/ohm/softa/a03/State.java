package ohm.softa.a03;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class State {
    private int t = 0;
    private final int duration;
    protected static final Logger logger = LogManager.getLogger();

    State(int duration){this.duration = duration;}
    abstract State successor(Cat cat);
    public int getTime() { return t; }
    public int getDuration() { return duration; }

    final public State tick(Cat cat){
        logger.info("tick()");
        t = t + 1;
        if (t < duration) {
            return this;
        }
        else {
            return successor(cat);
        }
    }
}

class DigestingState extends State {
    DigestingState(int duration) {
        super(duration);
    }
    State successor(Cat cat) {
        logger.info("Getting in a playful mood!");
        return new PlayfulState(cat.getAwake()- cat.getDigest());
    }
}

class HungryState extends State {
    HungryState(int duration) {
        super(duration);
    }

    State successor(Cat cat){
        logger.info("I've starved for a too long time...good bye...");
        return new DeathState(1);
    }
    State feed(Cat cat){
        logger.info("You feed the cat...");
        return new DigestingState(cat.getDigest());
    }
}

class PlayfulState extends State {
    PlayfulState(int duration) {
        super(duration);
    }
    State successor(Cat cat) {
        logger.info("Yoan... getting tired!");
        return new SleepingState(cat.getSleep());
    }
}
class DeathState extends State {
    DeathState(int duration) {
        super(duration);
    }
    State successor(Cat cat){
        return this;
    }
}

class SleepingState extends State {
    SleepingState(int duration) {
        super(duration);
    }
    State successor(Cat cat){
        logger.info("Yoan... getting hungry!");
        return new HungryState(cat.getAwake());
    }
}