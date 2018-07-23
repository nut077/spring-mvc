package com.nutfreedom.mvc.converter;

import com.nutfreedom.mvc.command.NotesCommand;
import com.nutfreedom.mvc.entity.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NotesCommandToNotesTest {
    private static final Long ID_VALUE = 1L;
    private static final String RECIPETION_NOTES = "notes";
    private NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void convert() {
        NotesCommand command = new NotesCommand();
        command.setId(ID_VALUE);
        command.setRecipeNotes(RECIPETION_NOTES);

        Notes notes = converter.convert(command);

        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPETION_NOTES, notes.getRecipeNotes());
    }
}
