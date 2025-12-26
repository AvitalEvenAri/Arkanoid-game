package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import listeners.BlockRemover;
import listeners.BallRemover;
import listeners.PrintingHitListener;
import listeners.ScoreTrackingListener;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.Sprite;
import sprites.SpriteCollection;
import java.awt.Color;
import java.util.List;

public class GameLevel implements Animation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final LevelInformation levelInfo;
    private final KeyboardSensor keyboard;
    private final AnimationRunner runner;
    private final Counter score;

    private SpriteCollection sprites;
    private GameEnvironment environment;

    private Counter remainingBlocks;
    private Counter remainingBalls;

    private boolean running;

    public GameLevel(LevelInformation levelInfo,
                     KeyboardSensor keyboard,
                     AnimationRunner runner,
                     Counter score) {
        this.levelInfo = levelInfo;
        this.keyboard = keyboard;
        this.runner = runner;
        this.score = score;

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);

        this.running = false;
    }

    // --- API used by GameFlow ---
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    public int getRemainingBalls() {
        return this.remainingBalls.getValue();
    }

    // --- game object wiring ---
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    private void addBlock(Block block) {
        this.addSprite(block);
        this.addCollidable(block);
    }

    // --- initialize according to LevelInformation ---
    public void initialize() {
        // reset per-level state
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);

        // 1) Background
        this.addSprite(this.levelInfo.getBackground());

        // 2) Borders + death region
        Block top = new Block(new Rectangle(new Point(0, 0), WIDTH, 20), Color.GRAY);
        Block left = new Block(new Rectangle(new Point(0, 0), 20, HEIGHT), Color.GRAY);
        Block right = new Block(new Rectangle(new Point(WIDTH - 20, 0), 20, HEIGHT), Color.GRAY);

        Block deathRegion = new Block(new Rectangle(new Point(0, HEIGHT - 20), WIDTH, 20), Color.GRAY);

        addBlock(top);
        addBlock(left);
        addBlock(right);
        addBlock(deathRegion);

        // 3) Listeners
        PrintingHitListener printer = new PrintingHitListener(); // optional
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        deathRegion.addHitListener(ballRemover);

        // 4) Level blocks
        List<Block> blocks = this.levelInfo.blocks();
        for (Block b : blocks) {
            addBlock(b);

            b.addHitListener(printer);       // optional
            b.addHitListener(blockRemover);  // remove blocks
            b.addHitListener(scoreTracker);  // +5 points per hit

            this.remainingBlocks.increase(1);
        }

        // 5) Paddle from levelInfo
        int paddleWidth = this.levelInfo.paddleWidth();
        int paddleSpeed = this.levelInfo.paddleSpeed();
        int paddleHeight = 15;

        int paddleX = (WIDTH - paddleWidth) / 2;
        int paddleY = HEIGHT - 40;

        Paddle paddle = new Paddle(
                new Rectangle(new Point(paddleX, paddleY), paddleWidth, paddleHeight),
                Color.ORANGE,
                this.keyboard,
                paddleSpeed,
                20,
                WIDTH - 20
        );
        paddle.addToGame(this);

        // 6) Balls from levelInfo
        List<Velocity> velocities = this.levelInfo.initialBallVelocities();
        int n = this.levelInfo.numberOfBalls();

        for (int i = 0; i < n; i++) {
            Ball ball = new Ball(WIDTH / 2, paddleY - 20, 7, Color.WHITE);
            ball.setVelocity(velocities.get(i));
            ball.setGameEnvironment(this.environment);
            this.addSprite(ball);
            this.remainingBalls.increase(1);
        }

        // 7) Indicators (always visible)
        this.addSprite(new ScoreIndicator(this.score));
        this.addSprite(new indicators.LevelNameIndicator(this.levelInfo.levelName()));

    }

    // --- Animation interface ---
    @Override
    public void doOneFrame(DrawSurface d) {
        // Pause (support English/Hebrew key)
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("פ")) {
            this.runner.run(new animation.KeyPressStoppableAnimation(
                    this.keyboard,
                    biuoop.KeyboardSensor.SPACE_KEY,
                    new animation.PauseScreen()
            ));
        }

        // Draw and update
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        // Stop conditions
        if (this.remainingBlocks.getValue() == 0) {
            // +100 bonus for clearing the level
            this.score.increase(100);
            this.running = false;
        } else if (this.remainingBalls.getValue() == 0) {
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    public void run() {
        // Countdown before the turn starts
        this.runner.run(new CountdownAnimation(2.0, 3, this.sprites));

        this.running = true;
        this.runner.run(this);
    }


}
