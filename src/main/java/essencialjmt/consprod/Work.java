package essencialjmt.consprod;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import essencialjmt.ImageData;

public class Work {
    private BlockingQueue<ImageData> workQueue = new LinkedBlockingQueue<>();
    
    public ImageData request() throws InterruptedException {
        return workQueue.take();
    }
    
    void produce(ImageData imageData) {
        try {
            workQueue.put(imageData);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
