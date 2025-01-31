package cheaters.get.banned.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

// When I first compiled your code, I saw all those warnings and cried a little
// Now, now I feel much better

public class PatchedGIFReader extends ImageReader {

    private final ImageReader gifReader;

    public PatchedGIFReader(ImageReaderSpi originatingProvider) {
        super(originatingProvider);

        // Find a GIF-compatible reader
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
        if (readers.hasNext()) {
            gifReader = readers.next();
        } else {
            throw new UnsupportedOperationException("No GIF reader available!");
        }
    }

    @Override
    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        gifReader.setInput(input, seekForwardOnly, ignoreMetadata);
    }

    @Override
    public int getNumImages(boolean allowSearch) throws IOException {
        return gifReader.getNumImages(allowSearch);
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        return gifReader.getWidth(imageIndex);
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        return gifReader.getHeight(imageIndex);
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        return gifReader.getImageTypes(imageIndex);
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        return gifReader.read(imageIndex, param);
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        return gifReader.getStreamMetadata();
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        return gifReader.getImageMetadata(imageIndex);
    }

    @Override
    public void reset() {
        super.reset();
        gifReader.reset();
    }

    @Override
    public ImageReadParam getDefaultReadParam() {
        return gifReader.getDefaultReadParam();
    }
}
