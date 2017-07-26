package br.ol.rollerball.game;

import br.ol.g2d.G2DContext;
import static br.ol.rollerball.game.RollerBallScene.State.*;
import br.ol.rolllerball.model.BumperBody;
import br.ol.rolllerball.model.ButtonBody;
import br.ol.rolllerball.model.PaddleLeftBody;
import br.ol.rolllerball.model.PaddleRightBody;
import br.ol.rolllerball.model.RollerBallGameModel;
import br.ol.rolllerball.model.SlingshotBody;
import br.ol.rolllerball.model.SlotWheelBody;
import br.ol.rolllerball.model.TargetBody;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.game.entitity.Ball;
import br.ol.rollerball.game.entitity.BallShadow;
import br.ol.rollerball.game.entitity.BonusScreen;
import br.ol.rollerball.game.entitity.Bumper;
import br.ol.rollerball.game.entitity.Button;
import br.ol.rollerball.game.entitity.Camera;
import br.ol.rollerball.game.entitity.GameOverScreen;
import br.ol.rollerball.game.entitity.HUD;
import br.ol.rollerball.game.entitity.Initializer;
import br.ol.rollerball.game.entitity.OLPresents;
import br.ol.rollerball.game.entitity.OutLaneGate;
import br.ol.rollerball.game.entitity.PaddleLeft;
import br.ol.rollerball.game.entitity.PaddleRight;
import br.ol.rollerball.game.entitity.ShooterEnd;
import br.ol.rollerball.game.entitity.ShooterGate;
import br.ol.rollerball.game.entitity.ShooterSpring;
import br.ol.rollerball.game.entitity.Slingshot;
import br.ol.rollerball.game.entitity.SlotWheel;
import br.ol.rollerball.game.entitity.Spinner;
import br.ol.rollerball.game.entitity.TableBackground;
import br.ol.rollerball.game.entitity.TableTop;
import br.ol.rollerball.game.entitity.TableWalls;
import br.ol.rollerball.game.entitity.Target;
import br.ol.rollerball.game.entitity.TargetWall;
import br.ol.rollerball.game.entitity.TitleScreen;
import br.ol.rollerball.game.infra.Keyboard;
import br.ol.rollerball.game.infra.Scene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.shader.BallShadowShader;
import br.ol.rollerball.renderer3d.shader.CursorShader;
import br.ol.rollerball.renderer3d.shader.FlatShader;
import br.ol.rollerball.renderer3d.shader.ShooterShader;
import br.ol.rolllerball.model.BallBody;
import br.ol.rolllerball.model.SpinnerModel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EightBallScene class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RollerBallScene extends Scene {

    public static BallShadowShader shadowShader = new BallShadowShader();
    public static CursorShader cursorShader = new CursorShader();
    public static FlatShader flatShader = new FlatShader();
    public static ShooterShader shooterShader = new ShooterShader();

    public static enum State { INITIALIZING, OL_PRESENTS, TITLE, GAME_START_PREPARATIONS, PLAYING, BONUS_SCREEN, GAME_OVER_SCREEN }
    public State state = State.INITIALIZING;
    
    private G2DContext g2d;
    public RollerBallGameModel gameModel;
    
    private long illuminationStartTime;
    private Color illuminationColor;
    private final Color defaultBackgroundColor = new Color(0, 0, 64, 255);
    private Color backgroundColor = defaultBackgroundColor;
    
    public RollerBallScene() {
        initG2D();
        initGameModel();
    }
    
    private void initG2D() {
        try {
            g2d = new G2DContext();
            g2d.loadFromResource("/res/rb.g2d");
        } catch (Exception ex) {
            Logger.getLogger(RollerBallScene.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    private void initGameModel() {
        gameModel = new RollerBallGameModel();
    }

    public G2DContext getG2D() {
        return g2d;
    }
    
    public State getState() {
        return state;
    }

    public void changeState(State state) {
        if (state != this.state) {
            this.state = state;
            broadcastMessage("stateChanged");
        }
    }

    public RollerBallGameModel getGameModel() {
        return gameModel;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void init() throws Exception {
        entities.add(new Initializer("initializer", this));
        
        OLPresents olpresents = new OLPresents("ol_presents", this);
        entities.add(olpresents);
        
        camera = new Camera("camera", this, olpresents);
        
        entities.add(new TableTop("table_top", this));
        entities.add(new TableWalls("table_walls", this));
        
        entities.add(new TableBackground("table_background", this));
        
        for (RigidBody rigidBody : gameModel.getBodies().values()) {
            if (rigidBody instanceof PaddleLeftBody) {
                entities.add(new PaddleLeft("paddle_left_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof PaddleRightBody) {
                entities.add(new PaddleRight("paddle_right_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof BumperBody) {
                entities.add(new Bumper("bumper_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof SlingshotBody) {
                entities.add(new Slingshot("slingshot_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof SlotWheelBody) {
                entities.add(new SlotWheel("slotwheel_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof TargetBody) {
                entities.add(new Target("target_1", this, rigidBody.getName()));
            }
            else if (rigidBody instanceof ButtonBody) {
                entities.add(new Button("button_1", this, rigidBody.getName()));
            }
            else if (rigidBody.getName().equals("target_wall_1_0")) {
                entities.add(new TargetWall("target_wall_1_0", this, rigidBody.getName()));
            }
        }
        
        for (SpinnerModel spinner : gameModel.getTable().getSpinners().values()) {
                entities.add(new Spinner("spinner_1", this, spinner));
        }
        
        entities.add(new ShooterGate("shooter_gate", this));
        entities.add(new ShooterSpring("shooter_spring", this));
        entities.add(new ShooterEnd("shooter_end", this));
        
        entities.add(new OutLaneGate("out_lane_gate", this));
        
        entities.add(new Ball("ball", this));
        entities.add(new BallShadow("ball_shadow", this));
        
        entities.add(new TitleScreen("title_screen", this));
        entities.add(new BonusScreen("bonus_screen", this));
        entities.add(new GameOverScreen("game_over_screen", this));
        
        entities.add(new HUD("hud", this));
        
        //entities.add(new FadeEffect("fade_effect", this));
        super.init();
    }

    @Override
    public void updatePhysics() {

// --- kill ball --- just for debugging purposes
//        if (Keyboard.keyDown[KeyEvent.VK_K]) {
//            gameModel.getBodyByName("ball_1").getPosition().setY(4000);
//        }
        
// --- start game --- just for debugging purposes
//        if (Keyboard.keyDown[KeyEvent.VK_S]) {
//            gameModel.start();
//        }

        gameModel.getTable().getShooter().hold(Keyboard.keyDown[KeyEvent.VK_SPACE] || Keyboard.keyDown[KeyEvent.VK_DOWN]);

// --- linux seems not to recognize key released event properly
//        if (Keyboard.keyDown[KeyEvent.VK_Z] && !Keyboard.keyDownConsumed[KeyEvent.VK_Z]) {
//            Keyboard.keyDownConsumed[KeyEvent.VK_Z] = true;
//            gameModel.getTable().leftSensorGroup();
//        }
//        if (Keyboard.keyDown[KeyEvent.VK_X] && !Keyboard.keyDownConsumed[KeyEvent.VK_X]) {
//            Keyboard.keyDownConsumed[KeyEvent.VK_X] = true;
//            gameModel.getTable().rightSensorGroup();
//        }

// --- slot machine --- just for debugging purposes
//        if (Keyboard.keyDown[KeyEvent.VK_1] && !Keyboard.keyDownConsumed[KeyEvent.VK_1]) {
//            Keyboard.keyDownConsumed[KeyEvent.VK_1] = true;
//            SlotWheelBody slot1 = (SlotWheelBody) gameModel.getBodyByName("slot_1");
//            slot1.rotate();
//        }
//        if (Keyboard.keyDown[KeyEvent.VK_2] && !Keyboard.keyDownConsumed[KeyEvent.VK_2]) {
//            Keyboard.keyDownConsumed[KeyEvent.VK_2] = true;
//            SlotWheelBody slot2 = (SlotWheelBody) gameModel.getBodyByName("slot_2");
//            slot2.rotate();
//        }
//        if (Keyboard.keyDown[KeyEvent.VK_3] && !Keyboard.keyDownConsumed[KeyEvent.VK_3]) {
//            Keyboard.keyDownConsumed[KeyEvent.VK_3] = true;
//            SlotWheelBody slot3 = (SlotWheelBody) gameModel.getBodyByName("slot_3");
//            slot3.rotate();
//        }
        
        gameModel.getTable().activateLeftPaddles(Keyboard.keyDown[KeyEvent.VK_Z] || Keyboard.keyDown[KeyEvent.VK_A] || Keyboard.keyDown[KeyEvent.VK_LEFT]);
        gameModel.getTable().activateRightPaddles(Keyboard.keyDown[KeyEvent.VK_M] || Keyboard.keyDown[KeyEvent.VK_L] || Keyboard.keyDown[KeyEvent.VK_RIGHT]);
        gameModel.update();
    }

    @Override
    public void update(Renderer r) {
        if (System.currentTimeMillis() - illuminationStartTime < 50) {
            backgroundColor = illuminationColor;
        }
        else {
            backgroundColor = defaultBackgroundColor;
        }
        super.update(r);
    }
    
    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        renderer.getColorBuffer().setBackgroundColor(backgroundColor);        
        super.draw(renderer, g);
//        AffineTransform at = g.getTransform();
//        g.scale(0.1, 0.1);
//        gameModel.drawDebug(g);
//        g.setTransform(at);
    }
    
    public void illuminate(Color illuminationColor) {
        this.illuminationColor = illuminationColor;
        illuminationStartTime = System.currentTimeMillis();
    }

    // --- game flow ---
    
    public void startGame() {
        gameModel.start();
        changeState(GAME_START_PREPARATIONS);
    }

    public void nextLife() {
        gameModel.nextLife();
        changeState(PLAYING);
    }
    
    public void showBonusScreen() {
        changeState(BONUS_SCREEN);
    }
    
    public void showGameOverScreen() {
        changeState(GAME_OVER_SCREEN);
    }
 
    public void backToTitle() {
        gameModel.updateHiscore();
        gameModel.clearScore();
        gameModel.clearAllBonus();
        
        BallBody ball = (BallBody) gameModel.getBodyByName("ball_1");
        ball.getPosition().set(350, 4000);
        ball.getVelocity().set(0, 0);
        ball.setVisible(false);
        
        ((Camera) getCamera()).reset();
        changeState(TITLE);
    }
   
}
