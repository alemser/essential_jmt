package essencialjmt.cap2.ex6;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import essencialjmt.*;

public class ImageManagerV6 {

    private ImageRepo imageRepo = new ImageRepo();
    private ImageViewer imageViewer = new ImageViewer();

    public void loadImages(String... imageNames) {
        CompletableFuture<?>[] cfutures = Arrays.stream(imageNames).map(name -> CompletableFuture.supplyAsync(() -> imageRepo.loadImage(name))
                .thenApply(this::extractDimension).thenApply(this::countColours).thenAccept(imageViewer::display)).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(cfutures).join();
    }

    private ImageData countColours(ImageData data) {
        Set<Color> colors = new HashSet<Color>();
        int w = data.getBufferedImage().getWidth();
        int h = data.getBufferedImage().getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                colors.add(new Color(data.getBufferedImage().getRGB(x, y)));
            }
        }
        data.setColors(colors);
        return data;
    }

    private ImageData extractDimension(ImageData data) {
        data.setResolution(data.getBufferedImage().getWidth() + " x " + data.getBufferedImage().getHeight());
        return data;
    }
    
    public ImageData getImage(String name) {
        return imageRepo.getImage(name);
    }
}