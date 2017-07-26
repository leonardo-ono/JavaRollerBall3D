package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactAdapter;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.PhysicsVec2;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Table class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Table {
    
    private final RollerBallGameModel model;
    private final List<PaddleLeftBody> paddleLeftModels = new ArrayList<PaddleLeftBody>();
    private final List<PaddleRightBody> paddleRightModels = new ArrayList<PaddleRightBody>();
    private final Map<String, List<SensorBody>> sensorGroups = new HashMap<String, List<SensorBody>>();
    private final Map<String, TargetGroup> targetGroups = new HashMap<String, TargetGroup>();
    private final Map<String, SpinnerModel> spinners = new HashMap<String, SpinnerModel>();
    private final Shooter shooter;
    private boolean shootable;

    
    public Table(RollerBallGameModel model) {
        this.model = model;
        shooter = new Shooter(model);
    }

    public RollerBallGameModel getModel() {
        return model;
    }

    public List<PaddleLeftBody> getPaddleLeftModels() {
        return paddleLeftModels;
    }

    public List<PaddleRightBody> getPaddleRightModels() {
        return paddleRightModels;
    }

    public Map<String, List<SensorBody>> getSensorGroups() {
        return sensorGroups;
    }

    public Map<String, TargetGroup> getTargetGroups() {
        return targetGroups;
    }

    public Map<String, SpinnerModel> getSpinners() {
        return spinners;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public boolean isShootable() {
        return shootable;
    }

    void setShootable(boolean shootable) {
        this.shootable = shootable;
    }
    
    private void loadFromResource(String resource) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // remove comments or empty lines
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                
                // parse datas
                String[] datas = line.split("\\,");
                String name = datas[1].trim();
                int[] coordinates = new int[datas.length - 2];
                for (int i = 2; i < datas.length; i++) {
                    coordinates[i - 2] = Integer.parseInt(datas[i].trim());
                }
                
                // ba = ball
                if (line.startsWith("ba")) {
                    model.addBall(name, coordinates[0], coordinates[1]);
                }
                // wo = wall opened
                else if (line.startsWith("wo")) {
                    addLinkedWalls(name, coordinates, false);
                }
                // wc = wall closed
                else if (line.startsWith("wc")) {
                    addLinkedWalls(name, coordinates, true);
                }
                // pl = paddle leftSensorGroup
                else if (line.startsWith("pl")) {
                    model.addPaddleLeft(name, coordinates[0], coordinates[1]);
                }
                // pr = paddle rightSensorGroup
                else if (line.startsWith("pr")) {
                    model.addPaddleRight(name, coordinates[0], coordinates[1]);
                }
                // bu = bumper
                else if (line.startsWith("bu")) {
                    model.addBumper(name, coordinates[0], coordinates[1]);
                }
                // sl = slingshot
                else if (line.startsWith("sl")) {
                    model.addSlingshot(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                }
                // bh = ball holder
                else if (line.startsWith("bh")) {
                    model.addBallHolder(name, coordinates[0], coordinates[1]);
                }
                // ta = target
                else if (line.startsWith("ta")) {
                    model.addTarget(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                }
                // se = sensor (line)
                else if (line.startsWith("se")) {
                    model.addSensor(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                }
                // sc = sensor circle
                else if (line.startsWith("sc")) {
                    model.addSensorCircle(name, coordinates[0], coordinates[1], coordinates[2]);
                }
                // bt = button
                else if (line.startsWith("bt")) {
                    model.addButton(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                }
                // sm = slot (machine) wheel
                else if (line.startsWith("sw")) {
                    model.addSlotWheel(name, coordinates[0], coordinates[1]);
                }
                // sp = spinner
                else if (line.startsWith("sp")) {
                    model.addSpinner(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                }
                // System.out.println(line);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(RollerBallGameModel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    private void addLinkedWalls(String baseName, int[] coordinates, boolean closed) {
        int index = 0;
        for (int i = 0; i < coordinates.length - (closed ? 0 : 2); i += 2) {
            int x1 = coordinates[(i + 0) % coordinates.length];
            int y1 = coordinates[(i + 1) % coordinates.length];
            int x2 = coordinates[(i + 2) % coordinates.length];
            int y2 = coordinates[(i + 3) % coordinates.length];
            model.addWall(baseName + "_" + index, x1, y1, x2, y2);
            index++;
        }
    }
    
    public void create() {
        loadFromResource("/res/table.map");
        
        model.addSensorGroup("sensor_group_1", 0, ScoreTable.BONUS_MAX, "sensor_1", "sensor_2", "sensor_3");
        model.addSensorGroup("sensor_group_4", -1, ScoreTable.BONUS_MAX, "sensor_4_1", "sensor_4_2", "sensor_4_3");

        model.addTargetGroup("target_group_3", 272, 1526, 2, ScoreTable.BONUS_MAX, "target_3_1", "target_3_2", "target_3_3"); // C
        model.addTargetGroup("target_group_2", 422, 1525, 2, ScoreTable.BONUS_MAX, "target_3_4", "target_3_5", "target_3_6"); // B
        model.addTargetGroup("target_group_1", 519, 1587, 2, ScoreTable.BONUS_MAX, "target_3_7", "target_3_8", "target_3_9"); // A

        // ball shooter
        final SensorCircleBody sbs = (SensorCircleBody) model.getBodyByName("sensor_ball_shooter");
        sbs.addListener(new ContactAdapter<SensorCircleBody>(sbs) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                model.addScore(-ScoreTable.SENSOR_CIRCLE);
                shootable = true;
            }

            @Override
            public void onCollisionOut(RigidBody rb, Contact contact) {
                shootable = false;
            }
        });
        
        // ---
        
        final TargetBody t1 = (TargetBody) model.getBodyByName("target_1");
        final TargetBody t2 = (TargetBody) model.getBodyByName("target_2");
        final WallBody tw1 = (WallBody) model.getBodyByName("target_wall_1_0");
        for (int s : new int[] {4, 7}) {
            SensorBody sensorBody = (SensorBody) model.getBodyByName("sensor_" + s);
            sensorBody.addListener(new ContactAdapter<SensorBody>(sensorBody) {
                @Override
                public void onCollisionEnter(RigidBody rb, Contact contact) {
                    owner.setActivated(false);
                    t1.setVisible(true);
                    t2.setVisible(true);
                    tw1.setVisible(true);
                }
            });
        }

        for (int s : new int[] {5, 6}) {
            SensorBody sensorBody = (SensorBody) model.getBodyByName("sensor_" + s);
            sensorBody.addListener(new ContactAdapter<SensorBody>(sensorBody) {
                @Override
                public void onCollisionEnter(RigidBody rb, Contact contact) {
                    owner.setActivated(false);
                }
            });
        }
        
        t1.addListener(new ContactAdapter<TargetBody>(t1) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                if (!t2.isVisible()) {
                    tw1.setVisible(false);
                }
            }
        });

        t2.addListener(new ContactAdapter<TargetBody>(t2) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                if (!t1.isVisible()) {
                    tw1.setVisible(false);
                }
            }
        });
        
        final TargetGroup tg1 = targetGroups.get("target_group_1"); // A
        final TargetGroup tg2 = targetGroups.get("target_group_2"); // B
        final TargetGroup tg3 = targetGroups.get("target_group_3"); // C

        final SensorCircleBody sc31 = (SensorCircleBody) model.getBodyByName("sensor_circle_3_1");
        sc31.addListener(new ContactAdapter<SensorCircleBody>(sc31) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                tg1.setActivated(true);
            }
        });
        
        final SensorCircleBody sc32 = (SensorCircleBody) model.getBodyByName("sensor_circle_3_2");
        sc32.addListener(new ContactAdapter<SensorCircleBody>(sc32) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                tg2.setActivated(true);
            }
        });

        final SensorCircleBody sc33 = (SensorCircleBody) model.getBodyByName("sensor_circle_3_3");
        sc33.addListener(new ContactAdapter<SensorCircleBody>(sc33) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                tg3.setActivated(true);
            }
        });
        
        final SensorCircleBody sc1 = (SensorCircleBody) model.getBodyByName("sensor_circle_4_1");
        sc1.addListener(new ContactAdapter<SensorCircleBody>(sc1) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                openLastSectionGate(false);
            }
        });

        final SensorCircleBody sc2 = (SensorCircleBody) model.getBodyByName("sensor_circle_4_2");
        sc2.addListener(new ContactAdapter<SensorCircleBody>(sc2) {
            @Override
            public void onCollisionOut(RigidBody rb, Contact contact) {
                model.addScore(-ScoreTable.SENSOR_CIRCLE);
                openLastSectionGate(true);
            }
        });
        
        openLastSectionGate(false);

        final BallHolderBody bh1 = (BallHolderBody) model.getBodyByName("ball_holder_1");
        bh1.addListener(new BallHolderListener() {
            @Override
            public void onHold() {
                model.addBonus(0, ScoreTable.BONUS_MAX);
            }

            @Override
            public void onRelease() {
            }
        });
        
        final BallHolderBody bh2 = (BallHolderBody) model.getBodyByName("ball_holder_2");
        bh2.addListener(new ContactAdapter<BallHolderBody>(bh2) {
            @Override
            public void onCollisionOut(RigidBody rb, Contact contact) {
                bh2.setDirection(BallHolderBody.DOWN);
            }
        });
        
        // slot machine 
        
        final SlotWheelBody sw1 = (SlotWheelBody) model.getBodyByName("slot_1");
        final SlotWheelBody sw2 = (SlotWheelBody) model.getBodyByName("slot_2");
        final SlotWheelBody sw3 = (SlotWheelBody) model.getBodyByName("slot_3");
        
        final ButtonBody bb1 = (ButtonBody) model.getBodyByName("button_right:1");
        bb1.addListener(new ContactAdapter<ButtonBody>(bb1) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw3.rotate();
            }
        });

        final ButtonBody bb2 = (ButtonBody) model.getBodyByName("button_right:2");
        bb2.addListener(new ContactAdapter<ButtonBody>(bb2) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw2.rotate();
            }
        });

        final ButtonBody bb3 = (ButtonBody) model.getBodyByName("button_right:3");
        bb3.addListener(new ContactAdapter<ButtonBody>(bb3) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw1.rotate();
            }
        });

        final ButtonBody bb4 = (ButtonBody) model.getBodyByName("button_left:4");
        bb4.addListener(new ContactAdapter<ButtonBody>(bb4) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw1.rotate();
            }
        });

        final ButtonBody bb5 = (ButtonBody) model.getBodyByName("button_left:5");
        bb5.addListener(new ContactAdapter<ButtonBody>(bb5) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw2.rotate();
            }
        });

        final ButtonBody bb6 = (ButtonBody) model.getBodyByName("button_left:6");
        bb6.addListener(new ContactAdapter<ButtonBody>(bb6) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                sw3.rotate();
            }
        });

        final ButtonBody bb31 = (ButtonBody) model.getBodyByName("button_3_1:7");
        bb31.addListener(new ContactAdapter<ButtonBody>(bb31) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                model.addScore(-ScoreTable.BUTTON);
                model.addBonus(2, ScoreTable.BUTTON_BONUS);
            }
        });

        final ButtonBody bb11 = (ButtonBody) model.getBodyByName("button_1_1:8");
        bb11.addListener(new ContactAdapter<ButtonBody>(bb11) {
            @Override
            public void onCollisionEnter(RigidBody rb, Contact contact) {
                model.addScore(-ScoreTable.BUTTON);
                model.addBonus(0, ScoreTable.BUTTON_BONUS);
            }
        });
        
        final BallHolderBody bh4 = (BallHolderBody) model.getBodyByName("ball_holder_4");
        bh4.setVisible(false);
        bh4.addListener(new ContactAdapter<BallHolderBody>(bh4) {
            @Override
            public void onCollisionOut(RigidBody rb, Contact contact) {
                owner.setVisible(false);
            }
        });

        // workaround to speed up physics simulation for this game
        model.getWorld().setBallBody(model.getBodyByName("ball_1"));
    }
    
    public void update() {
        shooter.update();
        updateSensorGroups();
        updateTargetGroups();
        updateTargetsBallHolderUp();
        updateSlotMachineEvaluateResult();
        updateSpinners();
    }

    private void updateSensorGroups() {
        nextSensorGroup:
        for (List<SensorBody> sensors : sensorGroups.values()) {
            for (SensorBody sensor : sensors) {
                if (!sensor.isActivated()) {
                    continue nextSensorGroup;
                }
            }
            for (SensorBody sensor : sensors) {
                sensor.blink();
            }
            if (sensors.get(0).getBonusIndex() < 0) {
                model.addScore(sensors.get(0).getPoints());
            }
            else {
                model.addBonus(sensors.get(0).getBonusIndex(), sensors.get(0).getPoints());
            }
        }
    }

    private void updateTargetGroups() {
        for (TargetGroup tg : targetGroups.values()) {
            tg.update();
        }
    }
    
    private void updateTargetsBallHolderUp() {
        for (int i = 1; i < 5; i++) {
            TargetBody target = (TargetBody) model.getBodyByName("target_4_" + i);
            if (target.isVisible()) {
                return;
            }
        }
        for (int i = 1; i < 5; i++) {
            TargetBody target = (TargetBody) model.getBodyByName("target_4_" + i);
            target.setVisible(true);
        }
        BallHolderBody ballHolder = (BallHolderBody) model.getBodyByName("ball_holder_2");
        ballHolder.setDirection(BallHolderBody.UP);
    }
    
    private void updateSlotMachineEvaluateResult() {
        final SlotWheelBody sw1 = (SlotWheelBody) model.getBodyByName("slot_1");
        final SlotWheelBody sw2 = (SlotWheelBody) model.getBodyByName("slot_2");
        final SlotWheelBody sw3 = (SlotWheelBody) model.getBodyByName("slot_3");
        if (!sw1.isRotating() && !sw2.isRotating() && !sw3.isRotating()
                && !sw1.isAwardObtained() && !sw2.isAwardObtained() && !sw3.isAwardObtained()
                && sw1.isEnabled() && sw2.isEnabled() && sw3.isEnabled()
                    && sw1.getResult() == sw2.getResult() && sw2.getResult() == sw3.getResult()) {
            sw1.setAwardObtained(true);
            sw2.setAwardObtained(true);
            sw3.setAwardObtained(true);
            sw1.blink();
            sw2.blink();
            sw3.blink();
            final BallHolderBody bh4 = (BallHolderBody) model.getBodyByName("ball_holder_4");
            switch (sw1.getResult()) {
                // 0 = cherry
                case 0:
                    bh4.setVisible(true);
                    bh4.setDirection(BallHolderBody.UP);
                    model.addScore(ScoreTable.SLOT_CHERRY);
                    break;
                // 1 = bell
                case 1:
                    model.incLives();
                    model.addScore(ScoreTable.SLOT_BELL);
                    break;
                // 2 = eggplant
                case 2:
                    bh4.setVisible(true);
                    bh4.setDirection(BallHolderBody.DOWN);
                    model.addScore(ScoreTable.SLOT_EGGPLANT);
                    break;
            }
        }
    }    

    private void updateSpinners() {
        for (SpinnerModel spinner : spinners.values()) {
            spinner.update();
        }
    }

    public void drawDebug(Graphics2D g) {
        shooter.drawDebug(g);
        for (TargetGroup targetGroup : targetGroups.values()) {
            targetGroup.drawDebug(g);
        }
        for (SpinnerModel spinner : spinners.values()) {
            spinner.drawDebug(g);
        }
    }

    private void openLastSectionGate(boolean opened) {
        for (int i = 0; i < 4; i++) {
            WallBody wgo = (WallBody) model.getBodyByName("gate_opened_4_" + i);
            WallBody wgc = (WallBody) model.getBodyByName("gate_closed_4_" + i);
            wgo.setVisible(opened);
            wgc.setVisible(!opened);
        }
    }
    
    // controls

    public void activateLeftPaddles(boolean activated) {
        for (PaddleLeftBody paddleLeftModel : paddleLeftModels) {
            paddleLeftModel.setActivated(activated);
        }
    }

    public void activateRightPaddles(boolean activated) {
        for (PaddleRightBody paddleRightModel : paddleRightModels) {
            paddleRightModel.setActivated(activated);
        }
    }
    
    public void leftSensorGroup() {
        for (List<SensorBody> sensors : sensorGroups.values()) {
            boolean firstSensorActivated = sensors.get(0).isActivated();
            for (int i = 0; i < sensors.size() - 1; i++) {
                SensorBody s1 = sensors.get((i + 0) % sensors.size());
                SensorBody s2 = sensors.get((i + 1) % sensors.size());
                s1.setActivated(s2.isActivated());
            }
            sensors.get(sensors.size() - 1).setActivated(firstSensorActivated);
        }
    }

    public void rightSensorGroup() {
        for (List<SensorBody> sensors : sensorGroups.values()) {
            boolean lastSensorActivated = sensors.get(sensors.size() - 1).isActivated();
            for (int i = sensors.size() - 1; i > 0; i--) {
                SensorBody s1 = sensors.get((i - 0) % sensors.size());
                SensorBody s2 = sensors.get((i - 1) % sensors.size());
                s1.setActivated(s2.isActivated());
            }
            sensors.get(0).setActivated(lastSensorActivated);
        }
    }
    
    public void shoot(double p) {
        if (isShootable()) {
            final BallBody bb1 = (BallBody) model.getBodyByName("ball_1");
            double impulse = p * (-1500 - 500 * Math.random());
            bb1.applyImpulse(new PhysicsVec2(0, impulse));
        }
    }

    public void reset() {
        for (RigidBody body : model.getWorld().getAllBodies()) {
            if (body instanceof TargetBody) {
                body.setVisible(true);
            }
            else if (body instanceof SensorBody) {
                SensorBody sb = (SensorBody) body;
                sb.setActivated(false);
            }
        }
        final BallBody bb1 = (BallBody) model.getBodyByName("ball_1");
        bb1.getPosition().set(581, 1979);
        bb1.getVelocity().set(0, 0);

        final WallBody tw1 = (WallBody) model.getBodyByName("target_wall_1_0");
        tw1.setVisible(true);

        final BallHolderBody bh2 = (BallHolderBody) model.getBodyByName("ball_holder_2");
        bh2.setDirection(BallHolderBody.DOWN);

        final BallHolderBody bh4 = (BallHolderBody) model.getBodyByName("ball_holder_4");
        bh4.setVisible(false);

        for (TargetGroup targetGroup : targetGroups.values()) {
            targetGroup.setActivated(false);
        }
        
        openLastSectionGate(false);
    }
       
    public void start() {
        reset();
        final SlotWheelBody sw1 = (SlotWheelBody) model.getBodyByName("slot_1");
        final SlotWheelBody sw2 = (SlotWheelBody) model.getBodyByName("slot_2");
        final SlotWheelBody sw3 = (SlotWheelBody) model.getBodyByName("slot_3");
        sw1.setEnabled(true);
        sw2.setEnabled(true);
        sw3.setEnabled(true);
        sw1.setResult(0);
        sw2.setResult(1);
        sw3.setResult(2);
        sw1.setAwardObtained(false);
        sw2.setAwardObtained(false);
        sw3.setAwardObtained(false);
    }
    
}
