package commands;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExitItem implements Command{
    private Stage stage;
    private AnchorPane scenePane;

    public ExitItem( AnchorPane scenePane){
        this.scenePane = scenePane;
    }

    @Override
    public void execute()
    {
        stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("You successfully exit");
        stage.close();
    }
}
