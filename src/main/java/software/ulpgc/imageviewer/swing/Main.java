package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageLoader;
import software.ulpgc.imageviewer.ImagePresenter;
import software.ulpgc.imageviewer.FileImageLoader;

import java.io.File;

public class Main {
    public static final String root = "C:/Users/PedroRS9/Pictures";
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        ImagePresenter presenter = new ImagePresenter(frame.getImageDisplay());
        ImageLoader loader = new FileImageLoader(new File(root));
        presenter.show(loader.load());
        frame.setVisible(true);
    }
}
