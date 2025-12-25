package game;
import animation.Animation;
import animation.AnimationRunner;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import indicators.ScoreIndicator;
import demos.BallRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;
import listeners.PrintingHitListener;

// from our packages
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import listeners.BlockRemover;
/**
 * game.Game class:
 * Holds the sprites and the collidables, creates the GUI,
 * initializes the game objects, and runs the main game loop.
 */
public class Game implements Animation {

    // ---- Fields ----
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    // NEW: counter of remaining blocks (not including borders)
    private Counter remainingBlocks;
    private Counter remainingBalls;
    // counts how many points the player has
    private Counter score;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private boolean running;


    // ---- Constructor ----

    /**
     * Create a new game.Game with empty sprite/collidable collections
     * and a GUI of size 800x600.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        // Create the window (GUI)
        this.gui = new GUI("Arkanoid - Ass2", 800, 600);

        // Keyboard comes from the GUI
        this.keyboard = this.gui.getKeyboardSensor();

        // Runner is responsible for the animation loop
        this.runner = new AnimationRunner(this.gui, 60);

        // Your existing Sleeper can stay, but will no longer be used in the main loop
        this.sleeper = new Sleeper();

        // Counters
        this.remainingBlocks = new Counter(0);
    }


    // ---- Adding game objects ----

    /** Add a collidable to the game environment. */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /** Add a sprite to the sprites collection. */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /** Helper: add a block as both sprites.Sprite and collision.Collidable. */
    private void addBlock(Block block) {
        this.addSprite(block);
        this.addCollidable(block);
    }

    // ---- Initialize the game objects ----

    /**
     * Initialize a new game: create the borders, blocks,
     * paddle and balls, and add them to the game.
     */
    public void initialize() {
        // 0) Initialize counters
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        this.score = new Counter(0);

        // 1) Create border blocks (so the ball can't leave the screen)
        Block top = new Block(
                new Rectangle(new Point(0, 0), 800, 20),
                Color.GRAY
        );
        Block bottom = new Block(
                new Rectangle(new Point(0, 580), 800, 20),
                Color.GRAY
        );
        Block left = new Block(
                new Rectangle(new Point(0, 0), 20, 600),
                Color.GRAY
        );
        Block right = new Block(
                new Rectangle(new Point(780, 0), 20, 600),
                Color.GRAY
        );

        addBlock(top);
        addBlock(bottom);
        addBlock(left);
        addBlock(right);

        // 2) Create listeners infrastructure
        PrintingHitListener printer = new PrintingHitListener();          // optional: print on hit
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        // bottom block is the "death region" for balls
        bottom.addHitListener(ballRemover);

        // 3) Create block pattern (rows, each row different color)
        int blockWidth = 50;
        int blockHeight = 25;
        int rows = 6;
        int blocksInFirstRow = 12;
        int startY = 100;

        Color[] rowColors = {
                Color.LIGHT_GRAY,
                Color.RED,
                Color.ORANGE,
                Color.YELLOW,
                Color.PINK,
                Color.GREEN
        };

        for (int row = 0; row < rows; row++) {
            int blocksInRow = blocksInFirstRow - row; // fewer blocks in each row
            int y = startY + row * blockHeight;
            int startX = 50 + row * blockWidth;       // shift right on each row

            Color rowColor = rowColors[row % rowColors.length];

            for (int i = 0; i < blocksInRow; i++) {
                int x = startX + i * blockWidth;
                Rectangle r = new Rectangle(new Point(x, y), blockWidth, blockHeight);
                Block b = new Block(r, rowColor);

                // add block to the game
                addBlock(b);

                // register listeners for this block
                b.addHitListener(printer);        // print when hit (optional)
                b.addHitListener(blockRemover);   // remove block + update blocks counter
                b.addHitListener(scoreTracker);   // update score

                // update blocks counter
                this.remainingBlocks.increase(1);
            }
        }

        // 4) Create the paddle at the bottom center
        int paddleWidth = 80;
        int paddleHeight = 15;
        int paddleX = (800 - paddleWidth) / 2;
        int paddleY = 560; // slightly above the bottom

        Rectangle paddleRect = new Rectangle(
                new Point(paddleX, paddleY),
                paddleWidth,
                paddleHeight
        );

        Paddle paddle = new Paddle(
                paddleRect,
                Color.ORANGE,
                this.keyboard, // USE the field, not a new KeyboardSensor
                7,      // paddle speed
                20,     // left limit (do not enter left wall)
                780     // right limit (800 - 20 for the right wall)
        );

        paddle.addToGame(this);


        // 5) Create THREE balls in the middle of the screen
        Ball ball1 = new Ball(400, 300, 7, Color.WHITE);
        Ball ball2 = new Ball(420, 320, 7, Color.CYAN);
        Ball ball3 = new Ball(380, 320, 7, Color.PINK);


        // velocities (same as before)
        ball1.setVelocity(3, -3);
        ball2.setVelocity(-3, -4);
        ball3.setVelocity(2, -5);

        // Tell balls about the GameEnvironment so they can check collisions
        ball1.setGameEnvironment(this.environment);
        ball2.setGameEnvironment(this.environment);
        ball3.setGameEnvironment(this.environment);

        // Add balls as sprites
        this.addSprite(ball1);
        this.addSprite(ball2);
        this.addSprite(ball3);

        // Update balls counter (3 balls)
        this.remainingBalls.increase(3);

        // 6) Add score indicator LAST so it is drawn on top of everything
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        this.addSprite(scoreIndicator);
    }




    // ---- Main game loop ----

    /** Run the game -- start the animation loop. */
    public void run() {
        // 1) Run countdown before the turn starts (3..2..1 over 2 seconds)
        this.runner.run(new animation.CountdownAnimation(2.0, 3, this.sprites));

        // 2) Now run the actual game animation
        this.running = true;
        this.runner.run(this);
    }



    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // 1) אם לחצו P -> להיכנס למסך Pause (עד שלוחצים Space)
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("פ")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }



        // 2) Background (blue)
        d.setColor(new Color(0, 0, 150));   // dark blue
        d.fillRectangle(0, 0, 800, 600);


        // 3) לצייר את כל הספרייטים
        this.sprites.drawAllOn(d);

        // 4) לעדכן את כל הספרייטים (תנועה, התנגשות וכו')
        this.sprites.notifyAllTimePassed();

        // 5) תנאי עצירה (חלק 1+2 אצלך כבר קיים דרך counters)
        // אם אין בלוקים או אין כדורים - לעצור ולסגור חלון
        if (this.remainingBlocks.getValue() == 0 || this.remainingBalls.getValue() == 0) {
            this.running = false;
            this.gui.close();
        }
    }



}