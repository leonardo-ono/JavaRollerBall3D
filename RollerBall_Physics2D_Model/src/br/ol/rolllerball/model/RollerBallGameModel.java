package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.PhysicsTime;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.World;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RollerBallGameModel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RollerBallGameModel {
    
    private final World world;
    private final Map<String, RigidBody> bodies = new HashMap<String, RigidBody>();
    private final Table table;
    
    // HUD
    private int lives;
    private int score;
    private int hiscore = 1000;
    private final int[] bonus = new int[4];
    private boolean gameOver;
    
    public RollerBallGameModel() {
        world = new World();
        table = new Table(this);
        table.create();
        gameOver = true;
    }

    public World getWorld() {
        return world;
    }


    public Map<String, RigidBody> getBodies() {
        return bodies;
    }

    public Table getTable() {
        return table;
    }
    
    // --- HUD ---

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getLivesStr() {
        String livesStr = "00" + lives;
        livesStr = livesStr.substring(livesStr.length() - 2, livesStr.length());
        return livesStr;
    }

    public void incLives() {
        lives++;
    }
        
    public int getScore() {
        return score;
    }

    public String getScoreStr() {
        String scoreStr = "000000" + score;
        scoreStr = scoreStr.substring(scoreStr.length() - 6, scoreStr.length());
        return scoreStr;
    }

    public void addScore(int points) {
        score += points;
    }

    public void clearScore() {
        score = 0;
    }

    public int getHiscore() {
        return hiscore;
    }
    
    public String getHiscoreStr() {
        String hiscoreStr = "000000" + hiscore;
        hiscoreStr = hiscoreStr.substring(hiscoreStr.length() - 6, hiscoreStr.length());
        return hiscoreStr;
    }

    public void updateHiscore() {
        if (score > hiscore) {
            hiscore = score;
        }
    }

    public int getBonus(int index) {
        return bonus[index];
    }

    public String getBonusStr(int index) {
        String bonusStr = "00000" + getBonus(index);
        bonusStr = bonusStr.substring(bonusStr.length() - 5, bonusStr.length());
        return bonusStr;
    }

    public void addBonus(int index, int points) {
        bonus[index] += points;
        if (bonus[index] > 99999) {
            bonus[index] = 99999;
        }
    }
    
    public int getTotalBonus() {
        int totalBonus = 0;
        for (int bonusPoints : bonus) {
            totalBonus += bonusPoints;
        }
        return totalBonus;
    }
    
    public String getTotalBonusStr() {
        String totalBonusStr = "000000" + getTotalBonus();
        totalBonusStr = totalBonusStr.substring(totalBonusStr.length() - 6, totalBonusStr.length());
        return totalBonusStr;
    }
    
    public void transferBonusToScore(int points) {
        while (getTotalBonus() > 0 && points > 0) {
            for (int i = 0; i < bonus.length; i++) {
                if (bonus[i] == 0) {
                    continue;
                }
                else if (bonus[i] >= points) {
                    addScore(points);
                    bonus[i] -= points;
                    points = 0;
                    return;
                }
                else {
                    addScore(bonus[i]);
                    points -= bonus[i];
                    bonus[i] = 0;
                }
            }
        }
    }

    public void clearAllBonus() {
        for (int i = 0; i < bonus.length; i++) {
            bonus[i] = 0;
        }
    }    

    public boolean isGameOver() {
        return gameOver;
    }
    
    public RigidBody getBodyByName(String name) {
        return bodies.get(name);
    }
    
    // --- add bodies
    
    public BallBody addBall(String name, int x, int y) {
        BallBody ballBody = new BallBody(name, this, x, y);
        world.addRigidBody(ballBody);
        bodies.put(name, ballBody);
        return ballBody;
    }

    public WallBody addWall(String name, int x1, int y1, int x2, int y2) {
        WallBody wallBody = new WallBody(name, this, x1, y1, x2, y2);
        world.addRigidBody(wallBody);
        bodies.put(name, wallBody);
        return wallBody;
    }

    public PaddleLeftBody addPaddleLeft(String name, int x, int y) {
        PaddleLeftBody paddleLeftTopBody = new PaddleLeftBody(name + "_top", this, x, y, -1);
        PaddleLeftBody paddleLeftBottomBody = new PaddleLeftBody(name + "_bottom", this, x, y, 1);
        world.addRigidBody(paddleLeftTopBody);
        world.addRigidBody(paddleLeftBottomBody);
        table.getPaddleLeftModels().add(paddleLeftTopBody);
        table.getPaddleLeftModels().add(paddleLeftBottomBody);
        bodies.put(name + "_top", paddleLeftTopBody);
        bodies.put(name + "_bottom", paddleLeftBottomBody);
        return paddleLeftTopBody;
    }

    public PaddleRightBody addPaddleRight(String name, int x, int y) {
        PaddleRightBody paddleRightTopBody = new PaddleRightBody(name + "_top", this, x, y, -1);
        PaddleRightBody paddleRightBottomBody = new PaddleRightBody(name + "_bottom", this, x, y, 1);
        world.addRigidBody(paddleRightTopBody);
        world.addRigidBody(paddleRightBottomBody);
        table.getPaddleRightModels().add(paddleRightTopBody);
        table.getPaddleRightModels().add(paddleRightBottomBody);
        bodies.put(name + "_top", paddleRightTopBody);
        bodies.put(name + "_bottom", paddleRightBottomBody);
        return paddleRightTopBody;
    }

    public BumperBody addBumper(String name, int x, int y) {
        BumperBody bumperBody = new BumperBody(name, this, x, y, 45);
        world.addRigidBody(bumperBody);
        bodies.put(name, bumperBody);
        return bumperBody;
    }

    public SlingshotBody addSlingshot(String name, int x1, int y1, int x2, int y2) {
        SlingshotBody slingshotBody = new SlingshotBody(name, this, x1, y1, x2, y2);
        world.addRigidBody(slingshotBody);
        bodies.put(name, slingshotBody);
        return slingshotBody;
    }

    public BallHolderBody addBallHolder(String name, int x, int y) {
        BallHolderBody ballHolderBody = new BallHolderBody(name, this, x, y);
        world.addRigidBody(ballHolderBody);
        bodies.put(name, ballHolderBody);
        return ballHolderBody;
    }

    public SensorBody addSensor(String name, int x1, int y1, int x2, int y2) {
        SensorBody sensorBody = new SensorBody(name, this, x1, y1, x2, y2);
        world.addRigidBody(sensorBody);
        bodies.put(name, sensorBody);
        return sensorBody;
    }

    public SensorCircleBody addSensorCircle(String name, int x, int y, int radius) {
        SensorCircleBody sensorCircleBody = new SensorCircleBody(name, this, x, y, radius);
        world.addRigidBody(sensorCircleBody);
        bodies.put(name, sensorCircleBody);
        return sensorCircleBody;
    }

    // bonusIndex = -1 -> add points to score
    public void addSensorGroup(String groupName, int bonusIndex, int bonusPoints, String ... bodyNames) {
        for (String bodyName : bodyNames) {
            SensorBody sensor = (SensorBody) getBodyByName(bodyName);
            sensor.setBonusIndex(bonusIndex);
            sensor.setPoints(bonusPoints);
            List<SensorBody> sensors = table.getSensorGroups().get(groupName);
            if (sensors == null) {
                sensors = new ArrayList<SensorBody>();
                table.getSensorGroups().put(groupName, sensors);
            }
            sensors.add(sensor);
        }
    }

    public TargetBody addTarget(String name, int x1, int y1, int x2, int y2) {
        TargetBody targetBody = new TargetBody(name, this, x1, y1, x2, y2);
        world.addRigidBody(targetBody);
        bodies.put(name, targetBody);
        return targetBody;
    }

    // bonusIndex = -1 -> add points to score
    public void addTargetGroup(String groupName, int debugX, int debugY, int bonusIndex, int bonusPoints, String ... bodyNames) {
        TargetGroup tg = new TargetGroup(this, groupName, debugX, debugY, bonusIndex, bonusPoints);
        for (String bodyName : bodyNames) {
            TargetBody target = (TargetBody) getBodyByName(bodyName);
            tg.addTarget(target);
        }
        table.getTargetGroups().put(groupName, tg);
    }

    public ButtonBody addButton(String name, int x1, int y1, int x2, int y2) {
        ButtonBody buttonBody = new ButtonBody(name, this, x1, y1, x2, y2);
        world.addRigidBody(buttonBody);
        bodies.put(name, buttonBody);
        return buttonBody;
    }

    public SlotWheelBody addSlotWheel(String name, int x, int y) {
        SlotWheelBody slotBody = new SlotWheelBody(name, this, x, y);
        world.addRigidBody(slotBody);
        bodies.put(name, slotBody);
        return slotBody;
    }

    public SpinnerModel addSpinner(String name, int x1, int y1, int x2, int y2) {
        SpinnerModel spinner = new SpinnerModel(name, this, x1, y1, x2, y2);
        world.addRigidBody(spinner.getSensorTop());
        world.addRigidBody(spinner.getSensorBottom());
        bodies.put(spinner.getSensorTop().getName(), spinner.getSensorTop());
        bodies.put(spinner.getSensorBottom().getName(), spinner.getSensorBottom());
        table.getSpinners().put(name, spinner);
        return spinner;
    }
    
    // ---
    
    public void update() {
        PhysicsTime.update();
        while (PhysicsTime.getUpdatesCount() > 0) {
            world.update();
            table.update();
            PhysicsTime.decUpdatesCount();
        }
    }
    
    public void drawDebug(Graphics2D g) {
        world.drawDebug(g);
        table.drawDebug(g);
    }
    
    // --- game flow ---
    
    public void start() {
        lives = 3;
        gameOver = false;
        score = 0;
        Arrays.fill(bonus, 0);
        table.start();
    }
    
    public void nextLife() {
        lives--;
        if (lives == 0) {
            gameOver = true;
            return;
        }
        table.reset();
    }

}
