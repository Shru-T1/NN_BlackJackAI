# NN_BlackJackAI
NN_BlackJackAI is a game model for the well known BlackJack game made using Artificial Intelligence. This is a project by Shruti Sharma and Ben Ruelas. 
The following are the basic rules:

The goal of blackjack is to beat the dealer’s hand without going over 21.
Face cards are worth 10. Aces are worth 11.
Player starts with two cards, one of the dealer's cards is hidden until the end.
To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.
If you go over 21 you bust, and the system wins regardless of the dealer's hand.
If you are dealt 21 from the start (Ace & 10), you got a blackjack.
Dealer will hit until his/her cards total 17 or higher.

We have used Neural Network to train our game model. After playing some 10,000 games we get considerable results.

We have used the following structure for our neural network:
Input Layers = 18
(All possible Initial Player Sums)
Hidden Layers = 4
(Decision Factor: No. of neurons = About double of the output layer)
Output Layers = 2
( Always only two options: either Hit or Stand)
Our alpha is 0.6.

We have made the following inferences.
First Card: Having a small high-number, i.e. from 8-11 calls for a hit irrespective of the opponent’s card. However, a big high-number from 12-16 calls for a hit only when the dealer has his show card lower than or equal to 7.
Second Card: Having a numbers smaller than 7 with an ace usually call for a hit, depending on the card shown by the dealer. If the dealer has a high card, the probabilities of calling a hit increase.
There are rare and special cases, even within the observed matrix, and so are some deviations. 
