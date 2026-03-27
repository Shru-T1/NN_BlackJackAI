# NN_BlackJackAI
NN_BlackJackAI is a game model for the well known BlackJack game made using Artificial Intelligence. This is a project by Shruti Sharma and Ben Ruelas. 

This project demonstrates machine learning principles applied to a classic card game, achieving strong performance through 10,000+ training games.

**Authors:** Shruti Sharma and Ben Ruelas

---

## Table of Contents

- [Overview](#overview)
- [Game Rules](#game-rules)
- [Neural Network Architecture](#neural-network-architecture)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
- [Key Features](#key-features)
- [Learning Outcomes](#learning-outcomes)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Results & Inferences](#results--inferences)
- [Technologies](#technologies)

---

## Game Rules

The implementation follows standard Blackjack rules:

- **Objective:** Beat the dealer's hand without exceeding 21 points
- **Card Values:**
  - Number cards (2-10): Face value
  - Face cards (J, Q, K): 10 points
  - Aces: 11 points (or 1 if busting)
- **Game Flow:**
  - Player starts with 2 cards; dealer shows 1 card (other hidden)
  - Player can choose to **Hit** (take another card) or **Stand** (keep current total)
  - **Bust:** Exceeding 21 results in immediate loss
  - **Blackjack:** Ace + 10-value card dealt from start
  - **Dealer Rules:** Dealer automatically hits until hand totals 17 or higher

---

## Neural Network Architecture

The AI uses a carefully designed feedforward neural network:

| Component | Details |
|-----------|---------|
| **Input Layer** | 18 neurons (representing all possible player hand values) |
| **Hidden Layer(s)** | 4 neurons (decision factors) |
| **Output Layer** | 2 neurons (Hit or Stand) |
| **Learning Rate (α)** | 0.6 |
| **Training Games** | 10,000+ iterations |

### Network Design Rationale

- **Input neurons = 18:** Covers possible player sums from 3 to 20 (4-21 would be more comprehensive)
- **Hidden neurons = 4:** Approximately double the output layer size for optimal learning capacity
- **Output neurons = 2:** Binary decision—the only two choices a player has at any point

---

## Project Structure

```
NN_BlackJackAI/
├── README.md                    # Project documentation
├── BlackjackNN.pptx            # Presentation slides
├── Card.java                   # Card class definition
├── Deck.java                   # Deck management and shuffling
├── HiddenNeuron.java          # Hidden layer neuron implementation
├── OutputNeuron.java          # Output layer neuron implementation
├── Model.java                 # Neural network model (core logic)
├── NNRD.java                  # Neural network random training/testing
├── Controller.java            # Game flow control
└── View.java                  # GUI and user interface
```

### Key Classes

- **Card.java:** Represents individual playing cards with suit and rank
- **Deck.java:** Manages deck initialization, shuffling, and card distribution
- **HiddenNeuron.java:** Implements hidden layer neurons with weight calculations
- **OutputNeuron.java:** Implements output layer neurons for decision-making
- **Model.java:** Core neural network engine handling forward/backward propagation
- **NNRD.java:** Handles training and testing loops with random game generation
- **Controller.java:** Orchestrates game logic and player-dealer interactions
- **View.java:** Displays game state and handles user interactions

---

## How It Works

### Training Phase

1. **Initialization:** Network weights are randomly initialized
2. **Game Simulation:** 10,000+ games are played between the trained AI and a dealer
3. **Decision Making:** At each turn, the neural network evaluates:
   - Current player hand total
   - Dealer's visible card
   - Outputs probability for Hit vs. Stand
4. **Learning:** Network weights are adjusted based on game outcomes
5. **Optimization:** Learning rate (α = 0.6) controls convergence speed

### Game Execution

1. Player and dealer each receive 2 cards
2. Neural network analyzes current state
3. Network outputs decision: Hit or Stand
4. Hand is compared against dealer's hand
5. Winner is determined based on Blackjack rules

---

## Key Features

- ✅ **AI-Driven Decisions:** Neural network learns optimal strategy through reinforcement
- ✅ **GUI Interface:** Interactive game view built with Java Swing
- ✅ **Extensible Architecture:** Modular design allows easy modifications
- ✅ **Training Capability:** Supports multiple training runs with adjustable parameters
- ✅ **Statistical Analysis:** Tracks win/loss/bust rates across games
- ✅ **Configurable Network:** Adjust layers, neurons, and learning rates

---

## Learning Outcomes

After training on 10,000+ games, the neural network discovered:

### Strategy Insights

**Hand Values 8-11:**
- Always hit regardless of dealer's visible card
- High probability of improving without busting

**Hand Values 12-16 (Danger Zone):**
- Hit only when dealer shows a low card (2-7)
- Stand when dealer shows high cards (8-Ace) to avoid bust risk

**Soft Hands (with Ace):**
- Values below 7 with an Ace typically call for a hit
- Dealer's card strength influences the decision probability
- Higher dealer cards increase likelihood of hitting

**Special Cases & Deviations:**
- Rare hand combinations may deviate from standard strategy
- Network learns nuanced probabilities rather than hard rules

### Performance

- Achieves **considerable results** against dealer
- Strategy aligns with basic Blackjack strategy principles
- Demonstrates successful reinforcement learning application

---

## Getting Started

### Prerequisites

- Java 8 or higher
- JavaFX (for GUI components) or Swing
- IDE: Eclipse, IntelliJ IDEA, or NetBeans (optional)

### Compilation

```bash
javac *.java
```

### Running the Game

```bash
java Controller
```

Or if a main class is specified:

```bash
java View
```

---

## Usage

1. **Start Game:** Launch the application
2. **Observe Training:** Watch the neural network play practice games
3. **Play or Observe:** Choose to play manually or watch the AI play
4. **Analyze Results:** View statistics on win rates and learned strategy
5. **Retrain:** Modify network parameters and retrain if desired

---

## Results & Inferences

### Network Learning

The trained neural network successfully learns a strategy that mirrors **Basic Blackjack Strategy**, proving that:

- Neural networks can learn optimal game strategies without explicit rule programming
- 10,000 training iterations are sufficient for convergence to strong strategy
- Learning rate of 0.6 provides good balance between speed and stability

### Key Observations

1. **Pattern Recognition:** Network identifies critical hand ranges (8-11, 12-16)
2. **Conditional Logic:** Learns to condition decisions on dealer's card
3. **Risk Management:** Understands bust probability and adjusts strategy accordingly
4. **Exception Handling:** Captures rare edge cases and special combinations

---

## Technologies

- **Language:** Java
- **Framework:** Swing/JavaFX (GUI)
- **Algorithm:** Feedforward Neural Network with Backpropagation
- **Training Method:** Reinforcement Learning via Game Simulation
- **Card Game Logic:** Standard Blackjack Rules Implementation

---

## Future Enhancements

- [ ] Deep learning implementation with more layers
- [ ] Support for multiple players
- [ ] Betting system and bankroll management
- [ ] Export/import trained models
- [ ] Comparison with Q-learning and other RL algorithms
- [ ] Web-based interface
- [ ] Tournament mode with statistics tracking

---

## Contributing

Contributions are welcome! Feel free to:
- Submit bug reports
- Suggest improvements
- Propose new features
- Enhance documentation

---

## License

This project is open source. Please check the repository for any specific license information.

---

## Contact

For questions or discussions about this project, please reach out to the authors or open an issue in the repository.

