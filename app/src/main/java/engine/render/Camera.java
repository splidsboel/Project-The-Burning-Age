package engine.render;

import javafx.scene.canvas.GraphicsContext;

public class Camera {
    private double x, y;           // world position of camera center
    private double viewportWidth;  // visible screen width
    private double viewportHeight; // visible screen height
    private double zoom = 1.0;

    public Camera(double viewportWidth, double viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    /**
     * Centers the camera on a target while staying within map bounds.
     */
    public void update(double targetX, double targetY,
                       double tileSize, int maxWorldCols, int maxWorldRows) {

        double worldWidth = maxWorldCols * tileSize;
        double worldHeight = maxWorldRows * tileSize;

        // center camera on target
        double cameraX = targetX - (viewportWidth / 2.0) / zoom + (tileSize / 2.0);
        double cameraY = targetY - (viewportHeight / 2.0) / zoom + (tileSize / 2.0);

        // clamp camera to world bounds
        cameraX = Math.max(0, Math.min(cameraX, worldWidth - viewportWidth / zoom));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - viewportHeight / zoom));

        this.x = cameraX;
        this.y = cameraY;
    }

    public void apply(GraphicsContext g) {
        g.save();
        g.scale(zoom, zoom);
        g.translate(-x, -y);
    }

    public void reset(GraphicsContext g) {
        g.restore();
    }

    public void setZoom(double zoom) {
        this.zoom = Math.max(0.1, zoom);
    }
     // --- zoom control ---
    public void zoomIn(double step) { setZoom(zoom + step); }
    public void zoomOut(double step) { setZoom(zoom - step); }
    public double getZoom() { return zoom; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getViewportWidth() { return viewportWidth; }
    public double getViewportHeight() { return viewportHeight; }
}
