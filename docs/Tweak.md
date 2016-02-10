our answers were all just the first random answer, unless it started at 0 in which case it did get better. 
After printing the tweaked answer we discovered that they had lower scores then the first answer. This proves that our tweak function is actually working,
however it just gets really unlucky and just finds scores that are lower then the original random answer. The amount of times that this happens is somewhat
suspicious.


Our idea for implementing a random restart would be to slowly increase the number of things being flipped until the result flips/randomizes the spots in the
choices array. Another thing that we did not test for is finding the sweet spot of how many tweaks we need to do per iteration.
