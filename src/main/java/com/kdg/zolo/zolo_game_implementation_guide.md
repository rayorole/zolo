# Zolo Game Implementation Guide

## Project Structure

The project follows the Model-View-Presenter (MVP) pattern with the following key components:

### Models
- `GameModel`: Manages game state, ship placement logic and validation
- `Ship`: Represents different ship types and their properties  
- `Score`: Handles scoring and high score management
- `Move`: Tracks game moves for undo functionality

### Views
- `GameView`: Main game board UI
- `HomeView`: Starting screen with difficulty selection
- `HelpView`: Game instructions
- `AboutView`: Team information
- `HighScoreView`: Displays top scores

### Presenters
- `GamePresenter`: Game logic and UI updates
- `HomePresenter`: Main menu navigation
- `HighScorePresenter`: Score display logic

## Key Features to Implement

1. Ship Placement Logic
- Validate ship positions
- Check for overlapping ships
- Verify edge/corner placement

2. File Handling
- Load puzzles from text files
- Save/load high scores
- Store game solutions

3. Scoring System
- Track moves and time
- Calculate final score
- Maintain high score list

4. UI Improvements
- Drag and drop ship placement
- Real-time game clock
- Undo/redo functionality 
- Animation for solution playback

## Implementation Steps

1. Create basic models and game logic
2. Implement file I/O for puzzles and scores
3. Build UI components and navigation
4. Add game mechanics and validation
5. Implement scoring and high scores
6. Add extra features (drag/drop, animations)

## Usage Instructions

[Instructions for running and playing the game]