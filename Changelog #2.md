# Change #2: Increased Map Difficulty

## Problem
The original map had wide open corridors and direct paths, making it too easy for Pac-Man to escape from ghosts.

## Solution
Redesigned the map layout to create a more challenging maze with narrower corridors, dead-ends, and complex navigation paths.

## Changes Made to `PacMan.java`

### Updated `tileMap` array (lines 91-113)
Replaced the original map with a more difficult design featuring:

- **Narrower corridors**: Single-tile-wide paths throughout most of the map, reducing escape routes
- **More internal walls**: Added vertical walls breaking up open spaces, creating maze-like patterns
- **Dead-end areas**: Several trap zones where ghosts can corner Pac-Man
- **Complex navigation**: Longer, winding paths requiring more strategic movement
- **Bottlenecks**: Fewer escape options near ghost spawn areas

### Key Map Changes
- Top section: Narrower corridors with more obstacles
- Middle section: More complex wall patterns creating maze-like navigation
- Bottom section: Additional dead-ends and trap areas
- Overall: Reduced open spaces and increased wall density

## Result
The map is significantly more challenging, requiring better planning and strategy to navigate while avoiding ghosts. Players must think ahead and use the narrow corridors more carefully.


