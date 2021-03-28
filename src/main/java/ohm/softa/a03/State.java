package ohm.softa.a03;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class State {
    int t;
    final int duration = 0;
    protected static final Logger logger = LogManager.getLogger();

    abstract State successor(Cat cat);
    public int getTime() { return t; }
    public int getDuration() { return duration; }
    final public State tick(Cat cat){
        logger.info("tick()");
        t = t + 1;

        return successor(cat);
    }
}

class DigestingState extends State {
    private int digestingTime = 0;

    State successor(Cat cat) {
        logger.info("DigestingState");
        digestingTime = digestingTime + 1;
        if (digestingTime == cat.getDigest()) {
            logger.info("Getting in a playful mood!");
            return new PlayfulState();
        }
        else {
            return this;
        }
    }
}

class HungryState extends State {
    State successor(Cat cat){
        logger.info("HungryState");
        if (t == cat.getAwake()){
            logger.info("I've starved for a too long time...good bye...");
            return new DeathState();
        }
        return this;
    }
    State feed(Cat cat){
        logger.info("You feed the cat...");
        return new DigestingState();
    }
}

class PlayfulState extends State {
    State successor(Cat cat){
        logger.info("PlayfulState");
        if (t >= cat.getAwake() - cat.getDigest()) {
            logger.info("Yoan... getting tired!");
            t = 0;
            return new SleepingState();
        }
        return this;
    }
}

class DeathState extends State {
    State successor(Cat cat){
        logger.info("DeathState");
        return this;
    }
}

class SleepingState extends State {
    State successor(Cat cat){
        logger.info("Sleepingstate");
        if (t == cat.getSleep()) {
            logger.info("Yoan... getting hungry!");
            t = 0;
            return new HungryState();
        }
        else {
            return this;
        }
    }
}