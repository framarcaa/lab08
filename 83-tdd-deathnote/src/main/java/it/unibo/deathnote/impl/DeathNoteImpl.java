package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{

    private final String DEFAULT_CAUSE = "heart attack";

    private class Death {
        private String cause = DEFAULT_CAUSE;
        private String details = "";

        private void setCause(String cause){
            this.cause = cause;
        }

        private void setDetails(String details) {
            this.details = details;
        }
    }

    private static final long MS_LIMIT_TO_WRITE_CAUSE = 40;
    private static final long MS_LIMIT_TO_WRITE_DETAILS = 6040;

    private final Map<String, Death> map = new HashMap<>();
    private final TimerImpl timer = new TimerImpl();
    private String lastName;


    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule number " + ruleNumber + " does not exist");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        lastName = name;
        map.put(lastName, new Death());
        timer.reset();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (lastName == null || cause == null) {
            throw new IllegalArgumentException("There's no valid name or cause");
        }
        final long time = timer.getTime();
        if (time > MS_LIMIT_TO_WRITE_CAUSE) {
            return false;
        }
        map.get(lastName).setCause(cause);
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        if (lastName == null || details == null) {
            throw new IllegalArgumentException("There's no valid name or details");
        }
        final long time = timer.stop();
        if (time > MS_LIMIT_TO_WRITE_DETAILS) {
            return false;
        }
        map.get(lastName).setDetails(details);
        return true;
    }

    private Death equalDeath(String name) {
        final Death death = map.get(name);

        if (death == null) {
            throw new IllegalArgumentException("The name" + name + "is not on the DeathNote");
        }
        return death;
    }

    @Override
    public String getDeathCause(String name) {
        return equalDeath(name).cause;
    }

    @Override
    public String getDeathDetails(String name) {
        return equalDeath(name).details;
    }

    @Override
    public boolean isNameWritten(String name) {
        return map.containsKey(name);
    }
    
}
