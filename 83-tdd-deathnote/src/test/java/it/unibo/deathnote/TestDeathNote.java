package it.unibo.deathnote;

import java.beans.Transient;
import java.util.List;

import javax.swing.plaf.synth.SynthEditorPaneUI;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDeathNote {
    private final DeathNote deathNote = new DeathNoteImpl();

    @Test
    void testRuleZeroAndNegative() {
        for (final int i : List.of(0, -1)) {
            try {
                deathNote.getRule(i);
            } catch (Exception e) {
                assertFalse(e.getMessage().isBlank());
            }
        }
    }

    @Test
    void testNoRuleEmptyOrNull() {
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testHumanDeath() {
        final String name = "Papa Francesco";
        assertFalse(deathNote.isNameWritten(name));
        deathNote.writeName(name);
        assertTrue(deathNote.isNameWritten(name));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause() throws InterruptedException {
        // check that writing a cause of death before writing a name throws the correct
        // exception
        try {
            deathNote.writeDeathCause("pneumonia");
        } catch (Exception e) {
            assertFalse(e.getMessage().isBlank());
        }
        // write the name of a human in the notebook
        final String name = "Silvio Berlusconi";
        deathNote.writeName(name);
        // verify that the cause of death is a heart attack
        assertEquals("heart attack", deathNote.getDeathCause(name));
        // write the name of another human in the notebook
        final String name2 = "Albero Biggioggero";
        deathNote.writeName(name2);
        // set the cause of death to "karting accident"
        assertTrue(deathNote.writeDeathCause("karting accident"));
        // verify that the cause of death has been set correctly (returned true, and the
        // cause is indeed "karting accident")
        assertEquals("karting accident", deathNote.getDeathCause(name2));
        // sleep for 100ms
        Thread.sleep(100);
        //try to change the cause of death
        deathNote.writeDeathCause("motorbike accident");
        //verify that the cause of death has not been changed
        assertEquals("motorbike accident", deathNote.getDeathCause(name2));
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        //check that writing the death details before writing a name throws the correct exception
        try {
            deathNote.writeDetails("get sick");
        } catch (Exception e) {
            assertFalse(e.getMessage().isBlank());
        }
        //write the name of a human in the notebook
        final String name = "Silvio Berlusconi";
        deathNote.writeName(name);
        //verify that the details of the death are currently empty
        assertTrue(deathNote.getDeathDetails(name).isBlank());
        //set the details of the death to "ran for too long"
        assertTrue(deathNote.writeDetails("ran for too long"));
        //verify that death details have been set correctly (returned true, and the details are indeed "ran for too long")
        assertEquals("ran for too long", deathNote.getDeathDetails(name));
        //write the name of another human in the notebook
        final String name2 = "Albero Biggioggero";
        deathNote.writeName(name2);
        // sleep for 6100ms
        Thread.sleep(6100);   
        //try to change the details
        deathNote.writeDetails("get sick");
        //verify that the details have not been changed
        assertEquals("get sick", deathNote.getDeathDetails(name2));
    }
}