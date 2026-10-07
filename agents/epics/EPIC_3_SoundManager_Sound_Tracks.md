# Description
This epic will cover the background music in the game. 
- This behavior will be implemented in the SoundManagerService, which implements the SoundManager interface.
- Individual songs are associated with the SoundTrack enum.
    - The file name of the song as well as a field to hold the actual clip exists for each value.
- SoundTrack values are organized into lists, called SoundTrackSequence.
- Whenever we start playing a soundtrack that wasn't previously playing, rewind it to the beginning before playing it.

## Soundtrack-playing Behavior
- When playSoundTrackSequence is called and a soundTrackSequence is specified:
    - If the sequence is the same as the one already playing, do nothing.
    - Otherwise:
        - set the reference to the currently playing sound track sequence.
        - Then play the first sound track from the soundtrack sequence, from the start.
        - When the current song from the sound track sequence finishes playing, then play the next.
            - Once the last song finishes playing, then loop around and play the first song again.
- When stopPlayingAllSoundTracks() is called:
    - Stop playing any song that is currently playing.
    - Remove the reference to the currently playing sound track sequence.
    - Whenever we stop playing a clip explicitly, rewind it to the beginning, to be safe.
    - distinguish between a manual stop and a stop event triggered on the natural end of a clip's playthrough, so we don't accidentally start the next song.
    
## Additionally:
- SoundManager owns the clips in the SoundTrack enum.
    - use @Predestroy to close all of the clips on exit, just to be safe.

## On Loading:
- we should pass the folder music/ as well as the track name of the sounds we want to load into clips.
    - keep the filename on display in the enum value unchanged, though.
    
## Acceptance criteria:
  - Re-requesting the active sequence does not restart it.
  - Changing sequences stops the old track and starts the new sequence at track one.
  - Natural completion advances and wraps correctly.
  - Manual stopping does not advance the sequence.
  - Stopping clears playback state.
  - All owned clips close during application shutdown.