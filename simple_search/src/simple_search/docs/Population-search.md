#this is our explination of our Population search algorithm

Before starting crossover, we modified our regular hillclimber to not do random restarts and to not use loop-recur


First we made uniform crossover. By selecting the top scores from a population, then using those parents and repeatingly doing uniform crossover to make new children to make an entirely new population. Then selecting the top parents from that population and continuing.

This made very poop results because it converged very quickly, we decided to ignore that for now and move on.

Then we wrote two-point crossover, using the same method of selecting parents and making a new population. 
Our two pooint crossover only made one child, not two.
The results from this were even worse, the population converged almost instantly.

Then we decided we needed to change the method of selecting parents, instead of just selecting the top two. 
First we implemented tournament select, where we randomly selected an amount from the population, like say 10 of the 100.  Then we pick the top 2 of those to be the parents.    After doing this, the population still converged very quickly. 

Then we tried another method of selecting parents,
We sorted the population by best scores, and then took the top half of the population, then randomly selected 2 from the top half to be the parents. This still didn't work still converged very quickly.


Then we decicded that when we make a new population, we need to use more than just the same 2 parents, otherwise we get too much convergence.   So then we redesigned our method of making a new population, we would use one of the above methods of finding random parents, but then we would just make 1 child, then use the random parent selecter again to make another chil. Then we continued this until we had an enteriely new population of the same size.
After all this, our population still converged very quickly.

Since our population still converged very quickly, we decided to modify our parent selcting method, to just be completely random,  (we knew this one not climb well, but we just wanted to see if we could stop the population from converging)  after implementing completely random parent selcting, we realized our population would go much farther before converging but it would still converge.



Finally, after Friday in class, we realized where we were really going wrong was because we had no mutation implemented, we just had pure crossover.
