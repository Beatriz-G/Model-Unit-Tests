package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {
    private Board board;
    private User user1;
    private User user2;
    private User user3;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        // Initialize board, users, question, answer
        board = new Board("Java IDE");
        user1 = new User(board, "SugarCookie");
        user2 = new User(board, "HoneyBun");
        user3 = new User(board, "SweetiePie");
        question = user1.askQuestion("What IDE are we using for Java?");
        answer = user2.answerQuestion(question, "IntelliJ IDEA CE");
    }

    // Questioner's reputation increases by 5 points when upvoted
    @Test
    public void questionersReputationIncreasesBy5PointsWhenUpvoted() throws Exception {
        user2.upVote(question);

        assertEquals(5, user1.getReputation());
    }

    // Answerer's reputation increases by 10 when upvoted
    @Test
    public void answerersReputationIncreasesBy10WhenUpvoted() throws Exception {
        user1.upVote(answer);

        assertEquals(10, user2.getReputation());
    }

    // Answer accepted gives the answerer 15 point boost
    @Test
    public void answerAcceptedGivesAnswerer15PointBoost() throws Exception {
        user1.acceptAnswer(answer);

        assertEquals(15, user2.getReputation());
    }

    // User cannot upvote their own question
    @Test
    public void userCannotUpvoteTheirOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user1.upVote(question);

        assertFalse(true);
    }

    // User cannot downvote their own question
    @Test
    public void userCannotDownvoteTheirOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot downvote for yourself!");

        user1.downVote(question);

        assertFalse(true);
    }

    // User cannot upvote their own answer
    @Test
    public void userCannotUpvoteTheirOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user2.upVote(answer);

        assertFalse(true);
    }

    // User cannot downvote their own answer
    @Test
    public void userCannotDownvoteTheirOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot downvote for yourself!");

        user2.downVote(answer);

        assertFalse(true);
    }

    // Non-Author user cannot accept an answer to the question
    @Test
    public void nonAuthorUserCannotAcceptAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only SugarCookie can accept this answer as it is their question");

        user3.acceptAnswer(answer);
    }

    // Original questioner accepts an answer
    @Test
    public void originalQuestionerAcceptsAnswer () throws Exception {
        user1.acceptAnswer(answer);

        assertEquals(true, answer.isAccepted());
    }

    // Extra Credit Reputation Test
    @Test
    public void userReputationDecreasesBy1IfDownvoted() throws Exception {
        user1.downVote(answer);

        assertEquals(-1, user2.getReputation());
    }
}