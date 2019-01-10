package com.example.urvish.autofillframeworkdemo;

import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.support.annotation.NonNull;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

public class AutofillEmail extends AutofillService {


    public AutofillEmail() {
    }

    @Override
    public void onFillRequest(@NonNull FillRequest request, @NonNull CancellationSignal cancellationSignal, @NonNull FillCallback callback) {
        List<FillContext> cotext = request.getFillContexts();
        List<AssistStructure.ViewNode> emailFileds=new ArrayList<>();
        AssistStructure assistStructure = cotext.get(cotext.size() - 1).getStructure();
        identifyEmailNodes(assistStructure.getWindowNodeAt(0).getRootViewNode(),emailFileds);
        if(emailFileds.size()==0){
            return;
        }
        RemoteViews primaryEmail=new RemoteViews(getPackageName(),R.layout.email_suggestion);
        RemoteViews secondaryEmail=new RemoteViews(getPackageName(),R.layout.email_suggestion);
        SharedPreferences sharedPreferences =
                getSharedPreferences("EMAIL_STORAGE", MODE_PRIVATE);

        String primaryEmailString =
                sharedPreferences.getString("PRIMARY", "");
        String secondaryEmailString =
                sharedPreferences.getString("SECONDARY", "");


        primaryEmail.setTextViewText(R.id.email_suggestion_item,
                primaryEmailString);
        secondaryEmail.setTextViewText(R.id.email_suggestion_item,
                secondaryEmailString);
        AssistStructure.ViewNode emailField = emailFileds.get(0);
        Dataset primaryEmailDataSet =
                new Dataset.Builder(primaryEmail)
                        .setValue(
                                emailField.getAutofillId(),
                                AutofillValue.forText(primaryEmailString)
                        ).build();

        Dataset secondaryEmailDataSet =
                new Dataset.Builder(secondaryEmail)
                        .setValue(
                                emailField.getAutofillId(),
                                AutofillValue.forText(secondaryEmailString)
                        ).build();

        FillResponse response = new FillResponse.Builder()
                .addDataset(primaryEmailDataSet)
                .addDataset(secondaryEmailDataSet)
                .build();
        callback.onSuccess(response);
    }

    void identifyEmailNodes(AssistStructure.ViewNode node,List<AssistStructure.ViewNode> emailFields){
        if(node.getClassName().contains("EditText")){
            String viewId=node.getIdEntry();
            if(viewId!=null&&(viewId.contains("email")||viewId.contains("username"))){
                emailFields.add(node);
            }

        }
        for(int i=0;i<node.getChildCount();i++){
            identifyEmailNodes(node.getChildAt(i),emailFields);
        }
    }
    @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {

    }


}
