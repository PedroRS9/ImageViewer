package software.ulpgc.imageviewer;

public interface ImageDisplay {
    void paint(Image image, int offset);
    int getWidth();
    void clear();
    void on(Shift shift);
    void on(Released released);

    interface Shift {
        Shift Null = offset -> {};
        void offset(int offset);
    }
    interface Released {
        Released Null = offset -> {};
        void offset(int offset);
    }
}
