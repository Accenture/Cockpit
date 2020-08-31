package com.cockpit.api.model;

import static org.junit.Assert.assertEquals;
import com.cockpit.api.model.dto.jira.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JiraDataModelTest.class})
@WebMvcTest
public class JiraDataModelTest {

    @Test
    public void whenSetNewBoardThenGetRightBoard() {

        Board mockBoard = new Board();

        // given
        mockBoard.setIsLast(true);

        // then
        assertEquals(true, mockBoard.getIsLast());

    }

}
