## BUG FIXES / ENHANCEMENTS

#### Weapons
- [ ] Prevent weapons from working in creative
- [ ] Prevent weapons from doing the generic "use" action on interactable blocks
- [x] Make plasmagun less loud
- [x] Make dropped item models for weapons smaller.

#### Projectiles
- [ ] Find a way to override the invincibility window on non-player mobs, without mixins
- [x] Make plasmagun projectile smaller
- [x] Change the position from which the plasmagun balls spawn. Set it lower and more inwards

#### Jumppad
- [x] Limit max power of jumppads as it's excessive
- [x] Add 0.1/0.5/1.0 precision buttons for the jumppad power screen
- [x] Fix only being able to use one jumppad on the whole server
- [ ] Fix jumppad collisions
- [ ] Add NBT tags to jumppad BlockItem so it can be duplicated with its settings by middle click
- [ ] Stop showing the jumppad config screen if that jumppad was destroyed
- [x] Figure out how to make sliders in the jumppad GUI actually slide, not just click

#### Spikes
- [ ] Fix spikes collisions
- [x] Reduce the start fall height of spikes so that falling from a spike 1 block above another actually deals damage.
- [ ] Implement upside-down variant of spikes.

#### Misc
- [ ] Show the Quake health to the player
- [x] Certain sounds should only be heard by a single player
- [x] Add the ability to sprint while firing a weapon
- [ ] Make physics more Quakey
- [ ] When collisions for the blocks are fixed, establish if falling between 2 blocks counts as 1 or 2 blocks.

## NEW FEATURES
- [ ] Implement in-world portals. Make sure that teleporting to a nearby portal doesn't make the player fly around really fast to other players, as if executing /tp.
- [ ] Implement MCuake Player Settings item
- [ ] Implement all other weapons
- [ ] Implement ammo/pickup entities/spawners
- [ ] Add new blocks

