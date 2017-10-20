package com.deepak.questions.int_q.hker_rnk;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class AdvancedVoterMachine implements VoterMachine {

    public String findWinner(String[] votesArray) {
        Arrays.sort(votesArray);
        int maxVote = 0;
        Map<String, Integer> voteMap = new TreeMap<>();
        String winner = votesArray[0];
        for (String vote: votesArray) {
            if (voteMap.containsKey(vote)) {
                int existingCount = voteMap.get(vote);
                existingCount++;
                voteMap.put(vote, existingCount);
                if (existingCount >= maxVote) {
                    maxVote = existingCount;
                    winner = vote;
                }
            } else {
                voteMap.put(vote, 0);
            }
        }
        
        return winner;
    }
}
