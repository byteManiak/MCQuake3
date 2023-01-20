## BUG FIXES / ENHANCEMENTS

#### Weapons
- [ ] Prevent weapons from working in creative
- [ ] Prevent weapons from doing the generic "use" action on interactable blocks
- [ ] Make plasmagun less loud
- [ ] Make dropped item models for weapons smaller.

#### Projectiles
- [ ] Find a way to override the invincibility window on non-player mobs, without mixins
- [ ] Make plasmagun projectile smaller
- [ ] Fix plasmagun projectile collision box
- [ ] Change the position from which the plasmagun balls spawn. Set it lower and more inwards
- [ ] Figure out why plasmagun shoots through players if close enough. Look through bow code as a start.
- [ ] To be able to spawn projectiles from wherever around the player, set the destination of the projectile to e.g. 50 blocks ahead of the player's crosshair, and make the projectile entity head that way.

#### Jumppad
- [ ] Limit max power of jumppads as it's excessive
- [ ] Add 0.1/0.5/1.0 precision buttons for the jumppad power screen
- [ ] Fix only being able to use one jumppad on the whole server
- [ ] Fix jumppad collisions
- [ ] Add NBT tags to jumppad BlockItem so it can be duplicated with its settings by middle click
- [ ] Stop showing the jumppad config screen if that jumppad was destroyed
- [ ] Figure out how to make sliders in the jumppad GUI actually slide, not just click

#### Spikes
- [ ] Fix spikes collisions
- [ ] Reduce the start fall height of spikes so that falling from a spike 1 block above another actually deals damage.
- [ ] Implement upside-down variant of spikes.

#### Misc
- [ ] Show the Quake health to the player
- [ ] Certain sounds should only be heard by a single player
- [ ] Add the ability to sprint while firing a weapon
- [ ] Make physics more Quakey
- [ ] When collisions for the blocks are fixed, establish if falling between 2 blocks counts as 1 or 2 blocks.

## NEW FEATURES
- [ ] Implement in-world portals. Make sure that teleporting to a nearby portal doesn't make the player fly around really fast to other players, as if executing /tp.
- [ ] Implement MCuake Player Settings item
- [ ] Implement all other weapons
- [ ] Implement ammo/pickup entities/spawners
- [ ] Add new blocks

