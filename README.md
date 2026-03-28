# BlackJack AI — Neural Network (NN)

An AI agent that learns to play Blackjack using a Neural Network built from scratch in Java, trained over 10,000 simulated games.

## Overview

This project implements a Blackjack game where the AI player learns optimal strategy through a custom-built neural network. The network is trained entirely through simulated play, with no pre-trained weights or external ML libraries.

Built by Shruti Sharma and Ben Ruelas.

## How It Works

The neural network is trained over 10,000 simulated games, learning to map game states to optimal actions (hit or stand). The network architecture was designed specifically for the Blackjack decision space.

### Network Architecture

| Layer | Details |
|-------|---------|
| Input | 18 neurons (all possible initial player sums) |
| Hidden | 4 neurons (approximately double the output layer) |
| Output | 2 neurons (Hit or Stand) |
| Alpha (learning rate) | 0.6 |

## Key Observations

After training, the agent converges on the following strategy:

- **Small high numbers (8–11):** Always hit, regardless of the dealer's card
- **Large high numbers (12–16):** Hit only when the dealer's shown card is 7 or lower
- **Ace + low card:** Hit in most cases, with the dealer's card as a deciding factor

## Tech Stack

- **Language:** Java
- **Architecture:** MVC (Model, View, Controller)
- **AI Model:** Custom Neural Network with backpropagation

## Project Structure

```
NN_BlackJackAI/
├── Card.java            # Card representation
├── Deck.java            # Deck logic
├── Model.java           # Game model
├── View.java            # Game display
├── Controller.java      # Game flow controller
├── HiddenNeuron.java    # Hidden layer neuron logic
├── OutputNeuron.java    # Output layer neuron logic
├── NNRD.java            # Neural network training logic
└── BlackjackNN.pptx     # Project presentation
```

## Running the Project

1. Clone the repository
2. Compile all `.java` files
3. Run `Controller.java` to start the game

```bash
javac *.java
java Controller
```

## Context

This was one of two parallel implementations built to compare how different AI learning paradigms converge on optimal Blackjack strategy. See the companion repo [CLS_BlackJackAI](https://github.com/Shru-T1/CLS_BlackJackAI) for the Collective Learning Systems implementation.
