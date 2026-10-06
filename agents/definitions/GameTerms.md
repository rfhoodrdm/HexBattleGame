
# Unit Template
A unit template represents a military game piece, controlled by one player of the game.

## Stats
- Name: Name of the unit template
- Max HP: Maximum health of the unit while on the field.
- Movement: Number of tiles that the unit may move at one time during a normal action
- Movement Type: Denotes whether the Unit may Move AND attack, Move OR attack, Or behaves according to special movement rules.
- Faction: Which faction may field this unit?
- Unit Type: May be mounted, infantry, siege engine, flying
- Special Attack: May reference a spell or special attack the unit is allowed to make.
- Special Attack frequency: How many turns must pass before the unit is allowed to execute its special attack again.
- Attack Range: How far away is the unit allowed to normal attack
- Attack power: How many combat dice is the unit allowed to roll in a normal attack
- Defense power: How many combat dice is the unit allowed to roll when targeted by a normal attack
- Movement Cards: The number of movement cards that are shuffled into the game's movement deck, which reference this unit.
- Base unit points: How many points does this unit cost when calculating the total army points of a force being fielded
- Base number fielded: An army may have up this many copies of the unit, with no penalty.
- Additional cost per unit: How many points does this unit cost to field beyond the base
- Max number fielded: Absolute maximum number of copies of this unit which may be fielded with any given army.

# Unit
An instance of a unit template on the game board.

## States
- Unit Id: A unique UUID idenfitier
- Unit index: Numeric Index displayed on the game interface, to identify the unit to the user.
- HP: current remaining health of the unit while on the field
- Position: Location on the map of the given unit
