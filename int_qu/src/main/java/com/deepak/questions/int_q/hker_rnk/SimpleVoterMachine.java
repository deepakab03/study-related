package com.deepak.questions.int_q.hker_rnk;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleVoterMachine implements VoterMachine {
    private static final Logger logger = LoggerFactory.getLogger(SimpleVoterMachine.class);
    
    public String findWinner(String[] votesArray) {
        Map<String, Integer> voteMap = new TreeMap<>();
        int maxVote = 0;
        for (String vote: votesArray) {
            if (voteMap.containsKey(vote)) {
                int existingCount = voteMap.get(vote);
                existingCount++;
                voteMap.put(vote, existingCount);
                if (existingCount > maxVote) {
                    maxVote = existingCount;
                }
            } else {
                voteMap.put(vote, 0);
            }
        }
        
        logger.info("Max vote was {}", maxVote);
        String winner = "";
        for(Map.Entry<String, Integer> entry: voteMap.entrySet()) {
            if (entry.getValue() == maxVote) {
                winner = entry.getKey();
            }
        }
        
        return winner;
    }
}
