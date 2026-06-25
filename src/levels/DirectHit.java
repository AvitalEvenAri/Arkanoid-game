package levels;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DirectHit implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();

        /*
         * המטרה: שהכדור יטוס "ישר" למעלה לכיוון הבלוק היחיד.
         * dx=0 => אין תנועה ימינה/שמאלה
         * dy שלילי => עולה למעלה
         */
        v.add(new Velocity(0, -5));
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        // רקע כמו בתמונה: שחור + עיגולים/מטרה כחולים
        return new DirectHitBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 40;
        int blockH = 40;

        /*
         * ממקמים את הבלוק במרכז המסך מבחינת X.
         * מבחינת Y שמים אותו באזור העליון, בערך כמו בתמונה.
         * (אם תרצי להזיז עוד קצת למעלה/למטה, תשני את y)
         */
        int x = (WIDTH - blockW) / 2;
        int y = 120;

        Block b = new Block(
                new Rectangle(new Point(x, y), blockW, blockH),
                Color.RED
        );

        blocks.add(b);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
