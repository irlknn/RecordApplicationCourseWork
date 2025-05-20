package commands;

import deprecated.UserActionHandler;

public class FindItem implements Command{
    private UserActionHandler collection;

    public FindItem(UserActionHandler collection){
        this.collection = collection;
    }

    @Override
    public void execute() {
        collection.find();
    }
}
