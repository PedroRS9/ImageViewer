package software.ulpgc.imageviewer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

public class FileImageLoader implements ImageLoader{
    private final File[] files;

    public FileImageLoader(File folder) {
        this.files = folder.listFiles(isImage());
    }

    private static final Set<String> imageExtensions = Set.of(".jpg", ".png");
    private static FilenameFilter isImage() {
        return (dir, name) -> imageExtensions.stream().anyMatch(name::endsWith);
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return files != null ? files[i].getAbsolutePath() : null;
            }


            @Override
            public Image next() {
                if (files != null) {
                    return imageAt((i+1) % files.length);
                }
                return null;
            }

            @Override
            public Image prev() {
                if (files != null) {
                    return imageAt((i-1+ files.length) % files.length);
                }
                return null;
            }
        };
    }
}