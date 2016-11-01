package com.github.fedorov_s_n.pokertimer.client.model;

import com.google.gwt.user.client.Timer;
import com.google.gwt.media.client.Audio;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.model.properties.IntegerProperty;
import org.tessell.model.properties.ListProperty;
import org.tessell.model.properties.StringProperty;
import static org.tessell.model.properties.NewProperty.integerProperty;
import static org.tessell.model.properties.NewProperty.listProperty;
import static org.tessell.model.properties.NewProperty.stringProperty;
import static org.tessell.model.properties.NewProperty.booleanProperty;

public class AppState {

    public static final String RUN_SYMBOL = "\u23f5";
    public static final String PAUSE_SYMBOL = "\u23f8";
    public final ListProperty<Blind> blinds = listProperty("blinds");
    public final IntegerProperty smallCoeff = integerProperty("smallCoeff", 2);
    public final IntegerProperty anteCoeff = integerProperty("anteCoeff", 0);
    public final IntegerProperty defaultTime = integerProperty("defaultTime", 900);
    public final IntegerProperty timeRemained = integerProperty("timeRemained");
    public final IntegerProperty timeTotal = integerProperty("timeTotal");
    public final StringProperty nextAction = stringProperty("nextAction");
    public final StringProperty blindCaption = stringProperty("blindCaption");
    public final BooleanProperty onStart = booleanProperty("onStart");
    public final Audio audio = Audio.createIfSupported();

    private final Timer timer = new Timer() {
        @Override
        public void run() {
            int time = timeRemained.get() - 1;
            if (time > 0) {
                timeRemained.set(time);
            } else {
                if (audio != null && !onStart.get()) {
                    audio.play();
                }
                Blind blind = next();
                if (blind != null) {
                    setBlind(blind);
                } else {
                    restart();
                }
            }
        }
    };

    private Blind next() {
        if (blinds.get().isEmpty()) {
            return null;
        }
        boolean flag = false;
        for (Blind blind : blinds.get()) {
            if (flag) {
                return blind;
            }
            flag = blind.active.get();
        }
        return flag ? null : blinds.get().get(0);
    }

    public void runTimer() {
        timer.scheduleRepeating(1000);
        nextAction.set(PAUSE_SYMBOL);
    }

    public void stopTimer() {
        timer.cancel();
        nextAction.set(RUN_SYMBOL);
    }

    public void toggleTimer() {
        if (timer.isRunning()) {
            stopTimer();
        } else {
            runTimer();
        }
    }

    public void setActiveBlind(Blind activeBlind) {
        for (Blind b : blinds.get()) {
            b.active.set(b == activeBlind);
        }
    }

    public void setBlind(Blind blind) {
        int small = blind.small.get();
        int big = blind.big.get();
        int ante = blind.ante.get();
        if (ante == 0) {
            blindCaption.set(StringHelper.join("/", new int[]{small, big}));
        } else {
            blindCaption.set(StringHelper.join("/", new int[]{small, big, ante}));
        }
        timeRemained.set(blind.time.get());
        timeTotal.set(blind.time.get());
        setActiveBlind(blind);
        onStart.set(false);
    }

    public void restart() {
        timer.cancel();
        timeTotal.set(defaultTime.get());
        timeRemained.set(0);
        nextAction.set(RUN_SYMBOL);
        if (anteCoeff.get() == 0) {
            blindCaption.set("--/--");
        } else {
            blindCaption.set("--/--/--");
        }
        onStart.set(true);
        setActiveBlind(null);
    }

    public AppState() {
        restart();
        if (audio != null) {
            audio.setSrc("https://raw.githubusercontent.com/fedorov-s-n/pokertimer/gh-pages/resources/horn.mp3");
        }
    }
}
