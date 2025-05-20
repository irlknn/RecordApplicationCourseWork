package commands;

import deprecated.UserActionHandler;

public class SortItem implements Command{
    private UserActionHandler collection;

    public SortItem(UserActionHandler collection){
        this.collection = collection;
    }

    @Override
    public void execute() {
        collection.sort();
    }
}
