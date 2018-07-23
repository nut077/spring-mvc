package com.nutfreedom.mvc.converter;

import com.nutfreedom.mvc.command.NotesCommand;
import com.nutfreedom.mvc.entity.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NotesToNotesCommandTest {
    private static final Long ID_VALUE = 1L;
    private static final String RECIPETION_NOTES = "notes";
    private NotesToNotesCommand converter;

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void convert() {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPETION_NOTES);

        NotesCommand command = converter.convert(notes);

        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertNotNull(RECIPETION_NOTES, command.getRecipeNotes());

    }
}
