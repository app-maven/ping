package io.appmaven.ping.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

import io.appmaven.ping.sprites.BaseSprite;
import io.appmaven.ping.utils.Vector;

public class Grid extends BaseSprite {
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private ArrayList<Tile> tiles;

    public Grid(Bitmap image) {
        super(image);

        this.tiles = new ArrayList<>();

        populateTiles();
    }

    private void populateTiles() {
        int left = 0, top = 0;

        float width = this.getWidth();
        float height = this.getHeight();

        while (left < screenWidth) {
            while (top < screenHeight) {
                Tile tile = new Tile(this.getImage());
                Vector position = new Vector(left, top);
                tile.setPosition(position);
                tiles.add(tile);
                top += height;
            }

            left += width;
            top = 0;
        }
    }


    public void draw(Canvas canvas) {
        for (Tile tile : this.tiles) {
            tile.draw(canvas);
        }
    }

    public void update() {
        for (Tile tile : this.tiles) {
            tile.update();
        }
    }
}
