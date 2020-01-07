package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;

public class SoundButton extends android.support.v7.widget.AppCompatButton {
    private SoundPool soundPool;
    private int SoundId;
    private int SoundStreamId = 0;

    public SoundButton(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setSoundEffectsEnabled(false);
        setAllCaps(false);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        SoundId = soundPool.load(context, R.raw.abc, 1);
    }

    public SoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                playSound();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void playSound() {
        if (PreferenceUtils.getInstance().isSound) {
            if (SoundStreamId != 0) {
                soundPool.pause(SoundStreamId);
            }
            SoundStreamId = soundPool.play(SoundId, 1.0f, 1.0f, 0, 0, 1);
        }
    }
}