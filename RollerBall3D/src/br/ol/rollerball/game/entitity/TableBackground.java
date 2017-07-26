package br.ol.rollerball.game.entitity;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapScreen;
import br.ol.rolllerball.model.BallHolderBody;
import br.ol.rolllerball.model.SensorBody;
import br.ol.rolllerball.model.TargetGroup;
import br.ol.rolllerball.model.WallBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.infra.Time;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * TableBackground class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TableBackground extends RollerBallEntity {
    
    private final G2DContext g2d;
    private BufferedImage texture;
    private Graphics2D tg;
    
    private final SensorBody sensor1;
    private final SensorBody sensor2;
    private final SensorBody sensor3;

    private final SensorBody sensor_4_1;
    private final SensorBody sensor_4_2;
    private final SensorBody sensor_4_3;
    
    private final WallBody target_wall_1;
    private final BallHolderBody ball_holder_2;
    private final BallHolderBody ball_holder_4;

    private final Map<String, Animation> animations = new HashMap<String, Animation>();
    private Animation target_wall_1_animation;
    private Animation ball_holder_2_animation;
    private Animation ball_holder_4_animation;
    
    private final TextBitmapScreen bonus1;
    private int lastBonus1 = -1;
    private final TextBitmapScreen bonus2;
    private int lastBonus2 = -1;
    
    private final TargetGroup targetGroup1;
    private final TargetGroup targetGroup2;
    private final TargetGroup targetGroup3;
    
    public TableBackground(String name, RollerBallScene scene) {
        super(name, scene);
        g2d = scene.getG2D();
        sensor1 = (SensorBody) gameModel.getBodyByName("sensor_1");
        sensor2 = (SensorBody) gameModel.getBodyByName("sensor_2");
        sensor3 = (SensorBody) gameModel.getBodyByName("sensor_3");
        sensor_4_1 = (SensorBody) gameModel.getBodyByName("sensor_4_1");
        sensor_4_2 = (SensorBody) gameModel.getBodyByName("sensor_4_2");
        sensor_4_3 = (SensorBody) gameModel.getBodyByName("sensor_4_3");
        target_wall_1 = (WallBody) gameModel.getBodyByName("target_wall_1_0");
        ball_holder_2 = (BallHolderBody) gameModel.getBodyByName("ball_holder_2");
        ball_holder_4 = (BallHolderBody) gameModel.getBodyByName("ball_holder_4");
        targetGroup1 = gameModel.getTable().getTargetGroups().get("target_group_1");
        targetGroup2 = gameModel.getTable().getTargetGroups().get("target_group_2");
        targetGroup3 = gameModel.getTable().getTargetGroups().get("target_group_3");
        animations.put("sensor_1", g2d.getAnimations().getCopy("sensor_1"));
        animations.put("sensor_2", g2d.getAnimations().getCopy("sensor_2"));
        animations.put("sensor_3", g2d.getAnimations().getCopy("sensor_3"));
        animations.put("sensor_4_1", g2d.getAnimations().getCopy("sensor_4_1"));
        animations.put("sensor_4_2", g2d.getAnimations().getCopy("sensor_4_2"));
        animations.put("sensor_4_3", g2d.getAnimations().getCopy("sensor_4_3"));
        animations.put("target_group_1_1", g2d.getAnimations().get("target_group_1_1"));
        animations.put("target_group_2_1", g2d.getAnimations().get("target_group_2_1"));
        animations.put("target_group_3_1", g2d.getAnimations().get("target_group_3_1"));
        animations.put("target_group_1_2", g2d.getAnimations().get("target_group_1_2"));
        animations.put("target_group_2_2", g2d.getAnimations().get("target_group_2_2"));
        animations.put("target_group_3_2", g2d.getAnimations().get("target_group_3_2"));
        bonus1 = g2d.getTextScreens().get("bonus1");
        bonus2 = g2d.getTextScreens().get("bonus2");
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/table_background.obj", 70);
        texture = mesh.get(0).material.map_kd.getColorBuffer();
        tg = (Graphics2D) texture.getGraphics();
        transform.setIdentity();
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        animations.get("sensor_1").currentFrameIndex = sensor1.isLightOn() ? 1 : 0;
        animations.get("sensor_2").currentFrameIndex = sensor2.isLightOn() ? 1 : 0;
        animations.get("sensor_3").currentFrameIndex = sensor3.isLightOn() ? 1 : 0;
        animations.get("sensor_4_1").currentFrameIndex = sensor_4_1.isLightOn() ? 1 : 0;
        animations.get("sensor_4_2").currentFrameIndex = sensor_4_2.isLightOn() ? 1 : 0;
        animations.get("sensor_4_3").currentFrameIndex = sensor_4_3.isLightOn() ? 1 : 0;
        animations.get("target_group_1_1").currentFrameIndex = targetGroup1.isActivated() ? 1 : 0;
        animations.get("target_group_2_1").currentFrameIndex = targetGroup2.isActivated() ? 1 : 0;
        animations.get("target_group_3_1").currentFrameIndex = targetGroup3.isActivated() ? 1 : 0;
        animations.get("target_group_1_2").currentFrameIndex = targetGroup1.isLightOn() ? 1 : 0;
        animations.get("target_group_2_2").currentFrameIndex = targetGroup2.isLightOn() ? 1 : 0;
        animations.get("target_group_3_2").currentFrameIndex = targetGroup3.isLightOn() ? 1 : 0;
        
        for (Animation animation : animations.values()) {
            animation.draw(tg);
        }

        long timeDelta = Time.delta / 1000000;
        
        target_wall_1_animation = g2d.getAnimations().get("target_wall_1_" + (target_wall_1.isVisible() ? "off" : "on"));
        target_wall_1_animation.update(timeDelta);
        if (!target_wall_1_animation.playing) {
            target_wall_1_animation.play();
        }
        target_wall_1_animation.draw(tg);
        
        ball_holder_2_animation = g2d.getAnimations().get("ball_holder_2_" + (ball_holder_2.getDirection() > 0 ? "down" : "up"));
        ball_holder_2_animation.update(timeDelta);
        if (!ball_holder_2_animation.playing) {
            ball_holder_2_animation.play();
        }
        ball_holder_2_animation.draw(tg);

        ball_holder_4_animation = g2d.getAnimations().get("ball_holder_4");
        if (!ball_holder_4.isVisible()) {
            ball_holder_4_animation.currentFrameIndex = 2;
        }
        else {
            ball_holder_4_animation.currentFrameIndex = ball_holder_4.getDirection() > 0 ? 1 : 0;
        }
        ball_holder_4_animation.draw(tg);
        
        if (gameModel.getBonus(0) != lastBonus1) {
            g2d.getAnimations().get("bonus_1").draw(tg);
            lastBonus1 = gameModel.getBonus(0);
            bonus1.print(0, 0, gameModel.getBonusStr(0));
            bonus1.draw(tg);
        }
        
        if (gameModel.getBonus(2) != lastBonus2) {
            g2d.getAnimations().get("bonus_2").draw(tg);
            lastBonus2 = gameModel.getBonus(2);
            bonus2.print(0, 0, gameModel.getBonusStr(2));
            bonus2.draw(tg);
        }
        
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 1.0;
    }

    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
