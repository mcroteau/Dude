package xyz.sheswayhot;

import xyz.sheswayhot.Parakeet;
import xyz.sheswayhot.access.Accessor;
import xyz.sheswayhot.access.impl.MockAccessor;

public class ParakeetFactory {

    Parakeet parakeet;
    Accessor mockAccessor;

    public ParakeetFactory(){
        mockAccessor = new MockAccessor();
        parakeet = new Parakeet(mockAccessor);
    }

    public Parakeet getParakeet(){
        return this.parakeet;
    }

}
