package main.java.start;

import main.java.Utils.ResizeHelper;
import main.java.Utils.XMLDataFinder;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.resource.lang.Lang;

public class Main extends Application {

    private static Lang langue;

    private static Stage prStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        com.sun.javafx.util.Logging.getJavaFXLogger().setLevel(sun.util.logging.PlatformLogger.Level.OFF);

        prStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../../resource/gui/NewUI2.fxml"));
        primaryStage.setTitle("IRC");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.getIcons().add(new Image(getClass().getResource("../../resource/image/ediome2.png").toString()));
        primaryStage.setScene(new Scene(root, 1280, 720));
        Main.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("../../resource/gui/css/main-"+ XMLDataFinder.getTheme() +".css").toExternalForm());

        primaryStage.setResizable(true);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")) {
            long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
            Pointer lpVoid = new Pointer(lhwnd);
            WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
            final User32 user32 = User32.INSTANCE;

            int oldStyle = user32.GetWindowLong(hwnd, WinUser.GWL_STYLE);
            int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
            user32.SetWindowLong(hwnd, WinUser.GWL_STYLE, newStyle);
        }

        ResizeHelper.addResizeListener(getPrimaryStage());
    }

    public static Stage getPrimaryStage() {
        return prStage;
    }

    public static Lang getLangue() {
        return langue;
    }

    public static void setLangue(Lang langue) {
        Main.langue = langue;
    }
}