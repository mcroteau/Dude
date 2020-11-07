package dev.yougo;

import dev.yougo.Dude;
import dev.yougo.access.Accessor;
import dev.yougo.access.impl.MockAccessor;

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
