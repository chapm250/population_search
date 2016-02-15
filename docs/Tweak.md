our answers were all just the first random answer, unless it started at 0 in which case it did get better. 
After printing the tweaked answer we discovered that they had lower scores then the first answer. This proves that our tweak function is actually working,
however it just gets really unlucky and just finds scores that are lower then the original random answer. The amount of times that this happens is somewhat
suspicious.

It turns out that our tweak function wasn't working because we were choosing a random answer between 0 and 0 which led to nothing getting higher unless it was overweight then it would work. Because of this glitch we solved this problem in many ways. For our working solution we have a loop-recur that loops as many times as you want it to run, then changes 6 spots randomly (choose 6 because our we wanted to flip 3 points and our tweak function randomly selects 0 or 1 so on average it flips 3 bits). If the modified answer is better than the original then we use that in the next iteration. We also tried vanilla tail recursion but we ran into stack overflow error if we ran it > 2000 times.  

Our idea for implementing a random restart would be to slowly increase the number of things being flipped until the result flips/randomizes the spots in the
choices array. Another thing that we did not test for is finding the sweet spot of how many tweaks we need to do per iteration.
