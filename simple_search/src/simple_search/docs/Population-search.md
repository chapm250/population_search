#this is our explination of our Population search algorithm

Before starting crossover, we modified our regular hillclimber to not do random restarts and to not use loop-recur


First we made uniform crossover. By selecting the top scores from a population, then using those parents and repeatingly doing uniform crossover to make new children to make a new population. Then selecting the top parents from that population and continuing.

This made very poop results because it converged very quickly, we decided to ignore that for now and move on.
