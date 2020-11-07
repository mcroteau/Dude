package xyz.sheswayhot;

import xyz.sheswayhot.Dude;
import xyz.sheswayhot.access.Accessor;
import xyz.sheswayhot.access.impl.MockAccessor;

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
