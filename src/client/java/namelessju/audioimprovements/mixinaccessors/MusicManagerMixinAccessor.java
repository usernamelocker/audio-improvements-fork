package namelessju.audioimprovements.mixinaccessors;

public interface MusicManagerMixinAccessor
{
    void audioImprovements$afterJoinLevel();
    void audioImprovements$beforeDisconnect();
    
    void audioImprovements$handleDelayAfterTrackEnds(int newDelay);
    void audioImprovements$handleTickDelayClamp(int newDelay);
}
