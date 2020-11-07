package xyz.yougo;

import xyz.yougo.Dude;
import xyz.yougo.access.Accessor;
import xyz.yougo.access.impl.MockAccessor;

public class DudeFactory {

    Dude parakeet;
    Accessor mockAccessor;

    public DudeFactory(){
        mockAccessor = new MockAccessor();
        parakeet = new Dude(mockAccessor);
    }

    public Dude getDude(){
        return this.parakeet;
    }

}
