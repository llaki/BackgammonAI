BackgammonAI
============

The purpose of this project is to use Artificial Intelligence to create a powerful player of the backgammon game. 
The algorithm used is mini-max which is optimized by alpha-beta pruning technique. The maximal depth for the algorithm to run 
fast enough is 3. For each move the player needs around 25-30 seconds. Also to improve the performance (to make it faster), 
we are not considering every possible outcome of a dice each turn. Instead, each turn we consider just random 14 of them. 
It makes a significant difference in terms of performance and also seems to be good enough approximation for calculations.

The raw evaluation of a board consists of the following:

1. The "pipcount" difference. The pipcount for either black of white pieces roughly means the total distance to be covered in order
to put out all the pieces of that color. If one or several pieces is dead, we are counting the expected number of dice rolls to make 
them alive and for each dice roll which didn't make it alive, we add 7 to the total sum, because on average the sum of the dices each 
roll is 7. The pipcount notion is very important in that game and therefore it's having larger weight then other criterias.

2. The score for blocked spots. Blocked spot is a spot which is covered by at least 2 of the pieces. Having this kind of spot is good 
because it can't possibly get killed until it's there and also that spot becomes blocked for the opponent. Also we give different 
coefficients to safe spots depending on the spots: For example the Spots occupied inside the 6 last spots count more. There are
also differences between these 6. 

3. The score for unsafe spots. These are the spots which contain exactly 1 piece of that color. We give it highest (more negative)
coefficient if it can get killed on the next turn by the opponent.

The evaluation of the board state can be further optimized to include more criterias. For example, if it's impossible to happen any 
more killings, no need to count unsafe pieces and coverages. Only pipcount is important in that case. There are also other possible
criterias that can be added in the future.
