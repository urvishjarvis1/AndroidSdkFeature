package com.example.urvish.voiceapidemo;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isVoiceInteraction() || !isVoiceInteractionRoot()) {
            Toast.makeText(this, "Not a voice interaction!", Toast.LENGTH_SHORT).show();
        } else {
            ((TextView) findViewById(R.id.textview)).setText("voice interaction");
            beginVoiceInterAction();
        }


    }

    private void beginVoiceInterAction() {
        VoiceInteractor.PickOptionRequest.Option option1 = new VoiceInteractor.PickOptionRequest.Option("Red", 1);
        option1.addSynonym("orange");
        option1.addSynonym("yellow");
        option1.addSynonym("ironman");
        VoiceInteractor.PickOptionRequest.Option option2 = new VoiceInteractor.PickOptionRequest.Option("Blue", 1);
        option2.addSynonym("Violate");
        option2.addSynonym("skyblue");
        option2.addSynonym("cap");

        this.getVoiceInteractor().submitRequest(new VoiceInteractor.PickOptionRequest(new VoiceInteractor.Prompt("Which side are you on? Ironman or Cap?"), new VoiceInteractor.PickOptionRequest.Option[]{option1, option2}, null) {
            @Override
            public void onPickOptionResult(boolean finished, Option[] selections, Bundle result) {
                super.onPickOptionResult(finished, selections, result);
                if (finished && selections.length == 1) {
                    ((TextView) findViewById(R.id.textview)).setText(selections[0].getLabel());
                }
            }

            @Override
            public void onCancel() {
                super.onCancel();
                getActivity().finish();
            }
        });

    }
}
