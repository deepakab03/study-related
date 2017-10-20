package com.deepak.questions.int_q.hker_rnk;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Problem Statement: Given a string of votes - candidate names - voting machine has to pick the winner who has maximum
 * number of votes. In cases there is a tie, candidate with alphabetcially last name should win.
 * 
 * @author Deepak Abraham
 */
@RunWith(JUnitParamsRunner.class)
public class VoteMachineTest {
    private VoterMachine voterMachine = new AdvancedVoterMachine(); //new SimpleVoterMachine();
    
    @Parameters("Siddha Yeddy Kumar Anirudha Siddha Siddha Kumar Anirudha Siddha")
    @Test public void
    whenFindWinner_givenClearSingleWinner_shouldReturnCorrectWinner(String voteString) {
        String[] votesArray = voteString.split(" ");
        
        String winner = voterMachine.findWinner(votesArray);
        
        assertThat(winner, is(equalTo("Siddha")));
    }
    
    @Parameters("Siddha Yeddy Kumar Anirudha Yeddy Siddha Kumar Yeddy Siddha")
    @Test public void
    whenFindWinner_givenMultpleWinnerWithTie_shouldReturnAlphabeticallyLastWinner(String voteString) {
        String[] votesArray = voteString.split(" ");
        
        String winner = voterMachine.findWinner(votesArray);
        
        assertThat(winner, is(equalTo("Yeddy")));
    }
    
    @Parameters("Anirudha")
    @Test public void
    whenFindWinner_givenSingleVote_shouldThatAsWinner(String voteString) {
        String[] votesArray = voteString.split(" ");
        
        String winner = voterMachine.findWinner(votesArray);
        
        assertThat(winner, is(equalTo("Anirudha")));
    }
    
    @Parameters("Anirudha Anirban Anirudha Basu Vasu Anirudha Anirban Deepak Anirban Gonchu")
    @Test public void
    whenFindWinner_givenSMultipleCandidaeWithSameStartingName_shouldStillFindByAlphabeticalLastCandidateAsWinner(String voteString) {
        String[] votesArray = voteString.split(" ");
        
        String winner = voterMachine.findWinner(votesArray);
        
        assertThat(winner, is(equalTo("Anirudha")));
    }
}
